package com.example.deepbay.ityjs.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author     IT烟酒僧
 * created   2018/10/24 16:48
 * desc:
 */

open  class BasePresenter<T:IBaseView> :IPresenter<T>{

    var mRootView:T?=null
    private set


    private var compositeDisposable=CompositeDisposable()



    override fun attachView(mRootView: T) {
       this.mRootView=mRootView
    }

    override fun detachView() {
        mRootView=null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed){
            compositeDisposable.clear()
        }
    }

    private val isViewAttached:Boolean
    get()=mRootView!=null

    fun checkViewAttached(){
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscription(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor():RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}