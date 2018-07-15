package bytemarktask.lifesavi.app.weatherx.data.remote

import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.dependency.component.DaggerRemoteComponent
import bytemarktask.lifesavi.app.weatherx.data.model.ForecastResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface ForecastOperation {
    @GET("daily")
    fun fetchForecast(@Query("city") string: String,@Query("key") key: String = Constant.Key.Weather)
            : Single<ForecastResponse>

    @GET("daily")
    fun fetchForecastFromLatLong(@Query("lat") lat: Double,@Query("lon") lon: Double,@Query("key") key: String = Constant.Key.Weather)
            : Single<ForecastResponse>
}

class RemoteDataSource {

    @Inject
    lateinit var operation: ForecastOperation

    init {
        DaggerRemoteComponent.builder().build().inject(this)
    }

    fun fetchForecast(place : String) : Single<ForecastResponse>{
        return operation.fetchForecast(place).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchForecastFromLatLong(lat : Double,lon: Double) : Single<ForecastResponse> {
        return operation.fetchForecastFromLatLong(lat,lon).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}