package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.RankContract
import com.example.deepbay.ityjs.mvp.model.RankModel
import com.example.deepbay.ityjs.net.exception.ExceptionHandle


/**
 * @author     IT烟酒僧
 * created   2019/3/28 17:10
 * desc:      获取tabinfo presenter
 */

class RankPresenter:BasePresenter<RankContract.View>(),RankContract.Presenter{

    private val rankModel by lazy { RankModel() }

    /**
     * 请求排行榜数据
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val diaposable=rankModel.requestRankList(apiUrl)
                .subscribe({
                    issue->
                    mRootView?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
                    }
                },{
                    throwable->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
    }

}
