package bytemarktask.lifesavi.app.weatherx.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun getFormattedDate(date: String,expectedFormat: String,currentFormat : String = "yyyy-MM-dd") : String{
            val currentSimpleFormat = SimpleDateFormat(currentFormat, Locale.ENGLISH)
            val formattedDate = currentSimpleFormat.parse(date)

            val newFormat = SimpleDateFormat(expectedFormat, Locale.ENGLISH)
            return newFormat.format(formattedDate)
        }
    }
}