package bytemarktask.lifesavi.app.weatherx.app

import android.app.Application
import android.content.Context
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class WeatherXApplication : Application() {

    companion object {
        lateinit var application: WeatherXApplication
    }

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(Constant.FontPath.MontserratRegular)
                .setFontAttrId(R.attr.fontPath)
                .build())

        application = this

    }


}