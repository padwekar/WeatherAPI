package bytemarktask.lifesavi.app.weatherx.ui.weather.search

import android.location.Location
import android.location.LocationManager
import bytemarktask.lifesavi.app.weatherx.app.base.IBasePresenter
import bytemarktask.lifesavi.app.weatherx.app.base.IBaseView
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

interface WeatherSearchContract {

    interface View : IBaseView {
        fun changeTextSize(size : Float)
        fun navigateBack()
        fun locationManager() : LocationManager
        fun locationRequestTask() : Task<LocationSettingsResponse>
        fun listenToLocations(listen: Boolean)

    }

    interface Presenter : IBasePresenter<View> {
        fun onPlaceSearch(place: String)
        fun onTextChange(text : String)
        fun onBackPress()
        fun onLocationRequest()
        fun onCurrentLocationReceived(location : Location?)
        fun onException(ex : Throwable)

    }

}