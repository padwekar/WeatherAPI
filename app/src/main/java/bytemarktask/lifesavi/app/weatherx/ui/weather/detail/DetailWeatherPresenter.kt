package bytemarktask.lifesavi.app.weatherx.ui.weather.detail

import android.support.v7.widget.LinearLayoutManager
import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.model.Forecast
import bytemarktask.lifesavi.app.weatherx.data.model.ForecastResponse
import bytemarktask.lifesavi.app.weatherx.data.remote.RemoteDataSource
import bytemarktask.lifesavi.app.weatherx.data.subject.Event
import bytemarktask.lifesavi.app.weatherx.utils.NetworkUtils

class DetailWeatherPresenter(baseView: DetailWeatherContract.View) : DetailWeatherContract.Presenter {

    var view = baseView
    private var remoteDataSource = RemoteDataSource()

    override fun onViewActive(view: DetailWeatherContract.View) {
        val adapter = DetailWeatherAdapter()
        adapter.presenter = this
        view.initRecyclerView(adapter, LinearLayoutManager(view.viewContext(), LinearLayoutManager.HORIZONTAL, false))
        Event.searchEvent.subscribe {
            modifyAndNotifyForecast(it)
        }
    }

    override fun onScreenAppeared(currentCity: Forecast?, futureForecast: Array<Forecast>?) {

        futureForecast?.let {
            if (view.isViewPresent()) {
                view.showFutureForecast(futureForecast)
            }
        }
        currentCity?.let {
            if (view.isViewPresent()) {
                view.showWeatherData(currentCity)
            }
        } ?: fetchLocationData()

    }



    override fun onWeatherDateSelected(forecast: Forecast) {
        view.showWeatherData(forecast)
    }

    override fun onSearchClick() {
        view.showSearchScreen()
    }


    private fun fetchLocationData(place: String = Constant.Defaults.City) {

        view.setProgressBar(true)

        when (NetworkUtils.isNetworkAvailable(view.viewContext())) {
            true ->
                view.hideError()
            false -> {
                view.setProgressBar(false)
                view.showError(Throwable("No Internet Connection"))
                return
            }
        }

        remoteDataSource.fetchForecast(place).subscribe(
                {
                    modifyAndNotifyForecast(it)
                }, {
            notifyError(it)
        }
        )

    }

    private fun modifyAndNotifyForecast(response: ForecastResponse) {
        response.run {
            view.also {

                this.data.forEach { forecast ->
                    forecast.city_name = this@run.city_name
                }

                if (view.isViewPresent()) {
                    view.setProgressBar(false)
                    it.showWeatherData(forecast = this.data.first())
                    it.showFutureForecast(futureForecast = this.data)
                } else {
                    it.setWeatherData(forecast = this.data.first(), futureForecast = this.data)
                }

            }
        }
    }

    private fun notifyError(throwable: Throwable) {
        view.run {
            view.setProgressBar(false)
            view.showError(throwable)
        }
    }

    override fun onRetryClick(place: String) {
        fetchLocationData(place)
    }

}