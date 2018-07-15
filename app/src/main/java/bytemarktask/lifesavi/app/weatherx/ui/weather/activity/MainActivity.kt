@file:Suppress("DEPRECATION")

package bytemarktask.lifesavi.app.weatherx.ui.weather.activity

import android.os.Bundle
import android.support.annotation.DrawableRes
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.base.BaseActivity
import bytemarktask.lifesavi.app.weatherx.ui.weather.detail.DetailWeatherFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replace(DetailWeatherFragment.newInstance(),R.id.container)
    }


    fun setImage(@DrawableRes resource : Int){
        Picasso.get().load(resource).placeholder(imageView.getDrawable()).fit()
                .centerCrop().into(imageView)
    }


}
