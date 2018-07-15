package bytemarktask.lifesavi.app.weatherx.ui.weather.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.base.BaseView
import bytemarktask.lifesavi.app.weatherx.data.annotation.InRelationShipWith
import bytemarktask.lifesavi.app.weatherx.data.model.Forecast
import bytemarktask.lifesavi.app.weatherx.ui.weather.activity.MainActivity
import bytemarktask.lifesavi.app.weatherx.ui.weather.search.WeatherSearchFragment
import bytemarktask.lifesavi.app.weatherx.utils.DateUtils
import bytemarktask.lifesavi.app.weatherx.utils.ImageUtils
import kotlinx.android.synthetic.main.fragment_weather_detail.*

@InRelationShipWith(R.layout.fragment_weather_detail)
class DetailWeatherFragment : BaseView(), DetailWeatherContract.View {

    private lateinit var adapter: DetailWeatherAdapter
    private var presenter: DetailWeatherContract.Presenter? = null
    private var currentForecast: Forecast? = null
    private var futureForecast: Array<Forecast>? = null


    companion object {
        fun newInstance() = DetailWeatherFragment()
    }


    override fun isViewPresent(): Boolean {
        return this.isAdded
    }

    override fun begin() {

        presenter = DetailWeatherPresenter(this)
        presenter?.onViewActive(this)
        presenter?.onScreenAppeared(currentForecast, futureForecast)

        imageViewSearch.setOnClickListener {
            presenter?.onSearchClick()
        }

    }

    override fun setWeatherData(forecast: Forecast, futureForecast: Array<Forecast>) {
        this.currentForecast = forecast
        this.futureForecast = futureForecast
    }

    override fun initRecyclerView(adapter: DetailWeatherAdapter, layoutManager: RecyclerView.LayoutManager) {
        this.adapter = adapter
        recyclerViewWeather.adapter = adapter
        recyclerViewWeather.layoutManager = layoutManager
    }

    override fun showSearchScreen() {
        val fragment = WeatherSearchFragment.newInstance()
        fragment.show(childFragmentManager, "")
    }

    override fun showWeatherData(forecast: Forecast) {
        currentForecast = forecast

        group_weather_elements.visibility = View.VISIBLE
        currentForecast = forecast
        forecast.run {
            textViewTemperature.text = temp.toString()
            textView.text = forecast.city_name
            textViewDate.text = DateUtils.getFormattedDate(valid_date, "dd MMMM yyyy")

            textViewCondition.text = weather.description
            textViewWind.text = String.format(getString(R.string.format_wind_speed), wind_spd)
            textViewHumidity.text = String.format(getString(R.string.format_relative_humidity), rh)

            (activity as MainActivity).setImage(ImageUtils.imageForCategory(weatherCode = weather.code, context = viewContext()))
        }
    }

    override fun showFutureForecast(futureForecast: Array<Forecast>) {
        this.futureForecast = futureForecast

        futureForecast.first().isSelected = true
        adapter.forecastList.clear()
        this.futureForecast?.let {
            adapter.forecastList.addAll(it.asList())
            adapter.notifyDataSetChanged()
        }
    }

    override fun hideError() {
        error_view.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        error_view.visibility = View.VISIBLE
        error_view.setTitle("Error")
        error_view.setSubtitle(throwable.message)
        error_view.setRetryListener {
            error_view.visibility = View.GONE
            presenter?.onRetryClick(currentForecast?.city_name ?: "Delhi")
        }

    }

    override fun setProgressBar(show: Boolean) {
        when (show) {
            true -> {
                progressBar.visibility = View.VISIBLE
                loadingStatus.visibility = View.VISIBLE
            }
            false -> {
                progressBar.visibility = View.GONE
                loadingStatus.visibility = View.GONE
            }
        }
    }

    override fun viewContext(): Context {
        return this@DetailWeatherFragment.context!!
    }

}