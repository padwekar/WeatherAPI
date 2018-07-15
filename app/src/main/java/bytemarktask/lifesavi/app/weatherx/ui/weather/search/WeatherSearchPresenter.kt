package bytemarktask.lifesavi.app.weatherx.ui.weather.search

import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.remote.RemoteDataSource
import bytemarktask.lifesavi.app.weatherx.data.subject.Event
import bytemarktask.lifesavi.app.weatherx.utils.NetworkUtils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsStatusCodes

class WeatherSearchPresenter(val view: WeatherSearchContract.View) : WeatherSearchContract.Presenter {
    override fun onException(ex: Throwable) {
        view.setProgressBar(false)
        view.showError(Throwable("Place not found ! Try some different search."))
    }


    override fun onCurrentLocationReceived(location: Location?) {
        location?.let {


            if(!isNetworkAvailable()){
                return
            }

            view.setProgressBar(true)

            remoteDataSource.fetchForecastFromLatLong(location.latitude,location.longitude).subscribe(
                    {
                        view.run {
                            setProgressBar(false)
                            listenToLocations(false)
                            Event.searchEvent.onNext(it)
                            navigateBack()
                        }
                    }, {
                view.run {
                    view.setProgressBar(false)
                    view.showError(Throwable("Place not found ! Try some different search."))
                }
            }
            )

        }
    }

    override fun onLocationRequest() {
        if (ActivityCompat.checkSelfPermission(view.viewContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.activity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    Constant.RequestCode.PermissionLocation)
        } else {
            accessUserLocation()
        }
    }

    private var remoteDataSource = RemoteDataSource()

    override fun onTextChange(text: String) {

        if (text.count() > 0) {
            view.hideError()
        }

        if (text.count() > Constant.App.TextCharacterLimit) {
            view.changeTextSize(view.viewContext().resources.getDimension(R.dimen.text_size_large))
        } else {
            view.changeTextSize(view.viewContext().resources.getDimension(R.dimen.text_size_large_title))
        }
    }

    private fun isNetworkAvailable() : Boolean {

        val networkAvailable = NetworkUtils.isNetworkAvailable(view.viewContext())

        when (networkAvailable) {
            true ->
                view.hideError()

            false -> {
                view.setProgressBar(false)
                view.showError(Throwable("No Internet Connection"))
                return false
            }
        }

        return networkAvailable
    }

    override fun onPlaceSearch(place: String) {
        if (TextUtils.isEmpty(place)) {
            view.showError(Throwable("Please Enter a city name"))
        } else {

            if(!isNetworkAvailable()){
                return
            }

            view.setProgressBar(true)

            remoteDataSource.fetchForecast(place).subscribe(
                    {
                        view.run {
                            Event.searchEvent.onNext(it)
                            setProgressBar(false)
                            navigateBack()
                        }
                    }, {
                        view.run {
                        view.setProgressBar(false)
                        view.showError(Throwable("Place not found ! Try some different search."))
                }
            }
            )
        }
    }

    override fun onViewActive(view: WeatherSearchContract.View) {

    }

    override fun onBackPress() {
        view.navigateBack()
    }


    private fun checkIfLocationIsEnabled() :Boolean {
        var isLocationEnabled = false
        try {
            isLocationEnabled = view.locationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) { }

        return isLocationEnabled
    }

    private fun accessUserLocation() {
        if(checkIfLocationIsEnabled()){
            view.listenToLocations(true)
        } else {
            resolveLocation()
        }
    }


    private fun resolveLocation(){
        view.locationRequestTask().addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                try {
                    view.listenToLocations(true)
                } catch (e: SecurityException) {
                    view.showError(Throwable("Something Went Wrong!!"))
                } catch (ex: Exception) {
                    view.showError(Throwable("Something Went Wrong!!"))
                }
            } catch (ex: ApiException) {
                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            (ex as ResolvableApiException).startResolutionForResult(view.activity(), Constant.RequestCode.EnableLocation)
                        } catch (ex: IntentSender.SendIntentException) {
                            view.showError(Throwable("Something Went Wrong!!"))
                        } catch (e: ClassCastException) {
                            view.showError(Throwable("Something Went Wrong!!"))
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        view.showError(Throwable("Something Went Wrong!!"))
                    }
                }
            }
        }
    }

}