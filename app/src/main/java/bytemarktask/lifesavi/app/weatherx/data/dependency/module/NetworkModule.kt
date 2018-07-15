package bytemarktask.lifesavi.app.weatherx.data.dependency.module

import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.remote.ForecastOperation
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constant.NetworkUrl.Base)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun forecastOperation(retrofit: Retrofit): ForecastOperation {
        return retrofit.create(ForecastOperation::class.java)
    }

}