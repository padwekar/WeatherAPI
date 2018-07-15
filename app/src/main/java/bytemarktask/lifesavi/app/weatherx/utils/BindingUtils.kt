package bytemarktask.lifesavi.app.weatherx.utils

import android.content.Context
import android.support.annotation.ArrayRes
import bytemarktask.lifesavi.app.weatherx.R


class ImageUtils {

    companion object {
        fun imageForCategory(weatherCode: Int,context: Context) :  Int  {
            val resource = when {
                weatherCode in 200..233 -> R.array.bg_thunderstrom_array
                weatherCode in 300..500 ->  R.array.bg_light_rain_array
                weatherCode in 501..522 ->  R.array.bg_heavy_rain_array
                weatherCode in 600..601 -> R.array.bg_light_snow_array
                weatherCode in 610..623 ->  R.array.bg_heavy_snow_array
                weatherCode in 700..751 ->  R.array.bg_fog_array
                weatherCode in 800..801 ->  R.array.bg_clear_array
                weatherCode in 802..804 ->  R.array.bg_overcast_array
                else -> R.array.bg_unknown_array
            }

            return randomImageFromArray(resource,context)
        }

        private fun randomImageFromArray(@ArrayRes arrayResource : Int,context: Context) : Int {
            val imgs = context.resources.obtainTypedArray(arrayResource)
            val resource = imgs.getResourceId((0 until imgs.length()).shuffled().last(),0)
            imgs.recycle()
            return resource
        }
    }




}