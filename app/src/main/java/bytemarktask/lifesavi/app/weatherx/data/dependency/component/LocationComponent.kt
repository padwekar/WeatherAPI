package bytemarktask.lifesavi.app.weatherx.data.dependency.component

import bytemarktask.lifesavi.app.weatherx.data.dependency.module.LocationModule
import bytemarktask.lifesavi.app.weatherx.data.dependency.scope.WeatherScope
import bytemarktask.lifesavi.app.weatherx.ui.weather.activity.MainActivity
import bytemarktask.lifesavi.app.weatherx.ui.weather.search.WeatherSearchFragment
import dagger.Component

@WeatherScope
@Component(modules = [LocationModule::class])
interface LocationComponent {
    fun inject(fragment: WeatherSearchFragment)
}