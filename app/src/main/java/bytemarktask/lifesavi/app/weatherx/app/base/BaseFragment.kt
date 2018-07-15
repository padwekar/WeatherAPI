package bytemarktask.lifesavi.app.weatherx.app.base

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import bytemarktask.lifesavi.app.weatherx.data.annotation.InRelationShipWith

open class BaseFragment : DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepare()
        val relationShipWith = this::class.annotations.find { it is InRelationShipWith } as? InRelationShipWith
        relationShipWith?.run {
            return view(relationShipWith.resource,inflater,container)
        }?: kotlin.run {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    open fun prepare() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationMode(false)
        begin()
    }

    open fun begin() {}

    protected fun replace(fragment: BaseFragment, @IdRes container: Int) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).replace(fragment, container)
        }
    }

    protected fun add(fragment: BaseFragment, @IdRes container: Int) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).add(fragment, container)
        }
    }

    protected fun navigationMode(isNavigationMode: Boolean){
        if (activity is BaseActivity) {
            (activity as BaseActivity).navigationMode(isNavigationMode)
        }
    }

    protected fun setTitle(title : String , subTitle : String = ""){
        if (activity is BaseActivity) {
            (activity as BaseActivity).setTitle(title,subTitle)
        }
    }

    protected fun toast(msg: String) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun pop(){
        if (activity is BaseActivity) {
            (activity as BaseActivity).onBackPressed()
        }
    }

    protected open fun view(res: Int, inflater : LayoutInflater, container: ViewGroup?) : View {
        return inflater.inflate(res,container,false)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(childFragmentManager.fragments.size > 0){
            childFragmentManager.fragments.last().onRequestPermissionsResult(requestCode,permissions,grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(childFragmentManager.fragments.size > 0){
            childFragmentManager.fragments.last().onActivityResult(requestCode,resultCode,data)
        }
    }
}