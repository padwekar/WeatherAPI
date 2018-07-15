package bytemarktask.lifesavi.app.weatherx.data.annotation

import android.support.annotation.IdRes

@Target(AnnotationTarget.CLASS)
annotation class InRelationShipWith(@IdRes val resource : Int,val dataBindingEnabled : Boolean = false)