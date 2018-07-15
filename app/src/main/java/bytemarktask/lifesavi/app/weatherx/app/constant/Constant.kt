package bytemarktask.lifesavi.app.weatherx.app.constant

import bytemarktask.lifesavi.app.weatherx.data.model.Forecast
import bytemarktask.lifesavi.app.weatherx.data.model.Weather

class Constant {
    object Defaults {
        val City = "Delhi"
    }

    object Key {
        val Weather = "d86f9cfdbd9349098e6f774f88fe0318"
    }

    object NetworkUrl {
        const val Base = "https://api.weatherbit.io/v2.0/forecast/"
        const val BaseImageUrl = "https://www.weatherbit.io/static/img/icons/%s.png"

    }

    object FontPath {
        val MontserratRegular = "fonts/montserrat_regular.otf"
        val MontserratBold = "fonts/montserrat_bold.otf"
        val MontserratLight = "fonts/montserrat_light.otf"
        val MontserratMedium = "fonts/montserrat_medium.otf"
        val MontserratSemiBold = "fonts/montserrat_semibold.otf"
        val MontserratThin = "fonts/montserrat_thin.otf"
    }

    object RequestCode {
        val PermissionLocation = 100
        val EnableLocation = 110
    }

    object App {
        val TextCharacterLimit = 12
    }
}