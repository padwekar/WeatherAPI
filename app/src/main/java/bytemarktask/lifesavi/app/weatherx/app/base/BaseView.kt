package bytemarktask.lifesavi.app.weatherx.app.base

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

abstract class BaseView :  BaseFragment(), IBaseView {

    override fun setProgressBar(show: Boolean) {

    }

    override fun activity(): FragmentActivity {
        return activity!!
    }

}
