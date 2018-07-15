package bytemarktask.lifesavi.app.weatherx.app.base

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

interface IBaseView {

    fun isViewPresent() : Boolean

    fun setProgressBar(show: Boolean)

    fun showError(throwable: Throwable)

    fun hideError()

    fun activity() : FragmentActivity

    fun viewContext() : Context
}
