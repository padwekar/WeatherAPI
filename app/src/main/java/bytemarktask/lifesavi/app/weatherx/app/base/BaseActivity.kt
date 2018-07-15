package bytemarktask.lifesavi.app.weatherx.app.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun replace(fragment: BaseFragment, @IdRes container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(fragment@ this.localClassName)
        transaction.replace(container, fragment)
        transaction.commit()
    }

    fun add(fragment: BaseFragment, @IdRes container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(container, fragment)
        transaction.commit()
    }

    fun navigationMode(isNavigationMode: Boolean) {
        onNavigationUpdate(isNavigationMode)
    }

    open fun onNavigationUpdate(isNavigationMode: Boolean) {}

    fun setTitle(title: String, subTitle: String = "") {
        supportActionBar?.title = title
        supportActionBar?.subtitle = subTitle
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.fragments.last().onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (!supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.fragments.last().onActivityResult(requestCode, resultCode, data)
        }
    }
}