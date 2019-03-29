package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.CategoryDetailContract
import com.example.deepbay.ityjs.mvp.model.CategoryDetailModel


/**
 * @author     IT烟酒僧
 * created   2019/3/29 10:27
 * desc:
 */
class CategoryDetailPresenter:BasePresenter<CategoryDetailContract.View>(),CategoryDetailContract.Presenter{
    private val categoryDetailModel by lazy { CategoryDetailModel() }

    private var nextPageUrl:String?=null

    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        val disposable=categoryDetailModel.getCategoryDetailList(id)
                .subscribe({
                    issue->
                    mRootView?.apply {
                        nextPageUrl=issue.nextPageUrl
                        setCateDetailList(issue.itemList)
                    }
                },{
                    throwable->
                    mRootView?.apply {
                        showError(throwable.toString())
                    }
                })

        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable=nextPageUrl?.let {
            categoryDetailModel.loadMoreData(it)
                    .subscribe({
                        issue->
                        mRootView?.apply {
                            nextPageUrl=issue.nextPageUrl
                            setCateDetailList(issue.itemList)
                        }
                    },{
                        throwable->
                        mRootView?.apply {
                            showError(throwable.toString())
                        }
                    })
        }
        disposable?.let { addSubscription(it) }
    }
}