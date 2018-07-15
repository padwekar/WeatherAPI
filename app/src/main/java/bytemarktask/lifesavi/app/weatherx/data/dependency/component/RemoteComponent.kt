package bytemarktask.lifesavi.app.weatherx.data.dependency.component

import bytemarktask.lifesavi.app.weatherx.data.dependency.module.NetworkModule
import bytemarktask.lifesavi.app.weatherx.data.dependency.scope.WeatherScope
import bytemarktask.lifesavi.app.weatherx.data.remote.RemoteDataSource
import dagger.Component

@WeatherScope
@Component(modules = [NetworkModule::class])
interface RemoteComponent {
    fun inject(dataSource: RemoteDataSource)
}

