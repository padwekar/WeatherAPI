package bytemarktask.lifesavi.app.weatherx.data.model

data class ForecastResponse(var city_name : String,var data : Array<Forecast>)

data class Forecast(val valid_date: String, val wind_spd : Double,val rh :Double,val temp:Double,val weather: Weather,var city_name: String,var isSelected : Boolean)

data class Weather(var code : Int, var  description: String, var icon: String)


