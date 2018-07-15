package bytemarktask.lifesavi.app.weatherx.app.base

interface IBasePresenter<ViewT> {
    fun onViewActive(view: ViewT)
    fun onViewInactive(){}
}
