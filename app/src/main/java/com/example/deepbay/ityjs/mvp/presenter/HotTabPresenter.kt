package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.HotTabContract
import com.example.deepbay.ityjs.mvp.model.HotTabModel
import com.example.deepbay.ityjs.net.exception.ExceptionHandle


/**
 * @author     IT烟酒僧
 * created   2019/3/28 16:42
 * desc:                获取tabinfo presenter
 */

class HotTabPresenter:BasePresenter<HotTabContract.View>(),HotTabContract.Presenter{

    private val hotTabModel by lazy { HotTabModel() }

    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable=hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    mRootView?.showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                })

        addSubscription(disposable)
    }

}
