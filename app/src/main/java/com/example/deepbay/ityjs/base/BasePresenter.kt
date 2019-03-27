package com.example.deepbay.ityjs.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author     IT烟酒僧
 * created   2018/10/24 16:48
 * desc:
 */

//T : IbaseView 表示上线为Ibaseview I可以是Ibaseview及其子类
open  class BasePresenter<T:IBaseView> :IPresenter<T>{

    var mRootView:T?=null
    private set

   // Disposable类
    //rxjava虽然好用，但是总所周知，容易遭层内存泄漏。
    // 也就说在订阅了事件后没有及时取阅，导致在activity或者fragment销毁后仍然占用着内存，
    // 无法释放。而disposable便是这个订阅事件，可以用来取消订阅。但是在什么时候取消订阅呢？我知道有两种方式:

    //使用使用CompositeDisposable
    //一个disposable的容器，可以容纳多个disposable，添加和去除的复杂度为O(1)
    //如果这个CompositeDisposable容器已经是处于dispose的状态，那么所有加进来的disposable都会被自动切断。
    //1、可以快速解除所有添加的Disposable类.
    //2、每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
    // 在退出的时候, 调用CompositeDisposable.clear() 即可快速解除.

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