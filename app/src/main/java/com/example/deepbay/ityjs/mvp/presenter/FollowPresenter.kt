package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.FollowContract
import com.example.deepbay.ityjs.mvp.model.FollowModel
import com.example.deepbay.ityjs.net.exception.ExceptionHandle


/**
 * @author     IT烟酒僧
 * created   2019/3/27 11:27
 * desc:  获取tabinfo presenter
 */
class FollowPresenter:BasePresenter<FollowContract.View>(),FollowContract.Persenter{

    //实例化model
    //利用by lazy 延迟初始化,作用,在调用的时候赋值,val 且值是唯一
    //延迟初始化的作用,在kotlin中 定义一个量,且不想让其为null,但又没有初始化的值时,就可以采用延迟初始化
    private val followModel by lazy { FollowModel() }

    private var nextPagerUrl:String?=null

    /**
     * 请求关注数据
     * apply函数扩展了所有的泛型对象,在闭包范围内可以任意调用该对象的任意方法,并在最后返回该对象.
     */

    override fun requestFollowList() {
        checkViewAttached()

        mRootView?.showLoading()

        val disposable=followModel.requestFollowList()
                .subscribe({
                    issue->
                    mRootView?.apply {
                        dismissLoading()
                        nextPagerUrl=issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                },{
                    throwable->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }


    override fun loadMoreData() {
        val disposable=nextPagerUrl?.let {
            followModel.loadMoreData(it)
                    .subscribe({
                        issue->
                        mRootView?.apply {
                            nextPagerUrl=issue.nextPageUrl
                            setFollowInfo(issue)
                        }
                    },{
                        t->
                        mRootView?.apply { showError(
                                ExceptionHandle.handleException(t),ExceptionHandle.errorCode
                        ) }
                    })
        }

        if (disposable!=null){
            addSubscription(disposable)
        }
    }

}