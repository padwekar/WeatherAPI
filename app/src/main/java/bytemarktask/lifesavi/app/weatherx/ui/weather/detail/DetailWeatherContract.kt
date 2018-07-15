package bytemarktask.lifesavi.app.weatherx.ui.weather.detail

import android.support.v7.widget.RecyclerView
import bytemarktask.lifesavi.app.weatherx.app.base.IBasePresenter
import bytemarktask.lifesavi.app.weatherx.app.base.IBaseView
import bytemarktask.lifesavi.app.weatherx.data.model.Forecast

interface DetailWeatherContract {

    interface View : IBaseView {
        fun setWeatherData(forecast: Forecast,futureForecast: Array<Forecast>)
        fun showWeatherData(forecast: Forecast)
        fun showFutureForecast(futureForecast : Array<Forecast>)
        fun showSearchScreen()
        fun initRecyclerView(adapter: DetailWeatherAdapter,layoutManager: RecyclerView.LayoutManager)
    }

    interface Presenter : IBasePresenter<View>{
        fun onScreenAppeared(currentCity : Forecast?,futureForecast: Array<Forecast>?)
        fun onWeatherDateSelected(forecast: Forecast)
        fun onSearchClick()
        fun onRetryClick(place : String)
    }

}