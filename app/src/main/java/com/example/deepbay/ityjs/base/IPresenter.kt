package com.example.deepbay.ityjs.base


/**
 * @author     IT烟酒僧
 * created   2018/10/24 16:49
 * desc:
 */
interface IPresenter<in V:IBaseView>{
    fun attachView(mRootView:V)
    fun detachView()
}