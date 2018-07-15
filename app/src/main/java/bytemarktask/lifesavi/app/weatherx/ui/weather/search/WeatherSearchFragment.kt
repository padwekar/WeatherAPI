package bytemarktask.lifesavi.app.weatherx.ui.weather.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.base.BaseView
import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.annotation.InRelationShipWith
import bytemarktask.lifesavi.app.weatherx.data.dependency.component.DaggerLocationComponent
import bytemarktask.lifesavi.app.weatherx.data.dependency.module.ContextModule
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_search_weather.*
import javax.inject.Inject

@Suppress("DEPRECATION")
@InRelationShipWith(R.layout.fragment_search_weather)
class WeatherSearchFragment : BaseView(), WeatherSearchContract.View,LocationListener {

    override fun locationRequestTask(): Task<LocationSettingsResponse> {
        return locationTask
    }

    override fun locationManager(): LocationManager {
        return locationManager
    }

    @Inject
    protected lateinit var locationTask : Task<LocationSettingsResponse>

    @Inject
    lateinit var locationManager : LocationManager

    var loactionRequested : Boolean = false

    override fun isViewPresent() : Boolean {
        return this.isAdded
    }

    companion object {
        fun newInstance() = WeatherSearchFragment()
    }

    lateinit var presenter: WeatherSearchContract.Presenter


    override fun prepare() {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }


    override fun begin() {
        presenter = WeatherSearchPresenter(view = this@WeatherSearchFragment)
        DaggerLocationComponent.builder().contextModule(ContextModule(context = viewContext())).build().inject(this)

        imageViewSearch.setOnClickListener {
            presenter.onPlaceSearch(editTextPlaceName.text.toString())
        }

        imageViewBack.setOnClickListener {
            presenter.onBackPress()
        }

        imageViewLocation.setOnClickListener {
            loactionRequested = true
            presenter.onLocationRequest()
        }

        editTextPlaceName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onTextChange(s.toString())
            }

        })
    }

    override fun changeTextSize(size: Float) {
        if(!isAdded){return}
        editTextPlaceName.textSize = size
    }

    override fun showError(throwable: Throwable) {
        if(!isAdded){return}
        progressBar.visibility = View.INVISIBLE
        textViewMessage.visibility = View.VISIBLE
        imageViewSearch.visibility = View.INVISIBLE
        textViewMessage.text = throwable.localizedMessage
    }

    override fun viewContext(): Context {
        return this@WeatherSearchFragment.context!!
    }

    override fun setProgressBar(show: Boolean) {
        if(!isAdded){return}
        when (show) {
            true -> {
                progressBar.visibility = View.VISIBLE
                textViewMessage.visibility = View.INVISIBLE
                imageViewSearch.visibility = View.INVISIBLE
            }
            false -> {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun navigateBack() {
        dismiss()
    }

    override fun hideError() {
        if(!isAdded){return}
        textViewMessage.visibility = View.INVISIBLE
        imageViewSearch.visibility = View.VISIBLE
    }

    override fun listenToLocations(listen : Boolean){

        if(listen){
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)
            }catch (e : SecurityException){
                presenter.onException(Throwable())
            }
        } else {
            locationManager.removeUpdates(this)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

    override fun onLocationChanged(location: Location?) {
        presenter.onCurrentLocationReceived(location)
    }

    //Run Time Permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constant.RequestCode.PermissionLocation){
            if(grantResults.first()==(PackageManager.PERMISSION_GRANTED)){
                presenter.onLocationRequest()
        }}else{
                //Location Permission Denied //Do Nothing
        }
    }

    //Turn On GPS Request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constant.RequestCode.EnableLocation) {
            when (resultCode) {
                Activity.RESULT_OK -> presenter.onLocationRequest()
                Activity.RESULT_CANCELED ->  Log.e("LocationCancelled","ERROR") // do Nothing
            }
        }
    }
}