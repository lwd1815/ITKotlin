package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.CategoryContract
import com.example.deepbay.ityjs.mvp.model.CategoryModel
import com.example.deepbay.ityjs.net.exception.ExceptionHandle


/**
 * @author     IT烟酒僧
 * created   2019/3/28 11:24
 * desc:
 */
class CategoryPresenter:BasePresenter<CategoryContract.View>(),CategoryContract.Presenter{

    /**
     * 先获取model实类
     */

    private val categoryModel:CategoryModel by lazy { CategoryModel() }

    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()

        val disposble=categoryModel.getCategoryData()
                .subscribe({
                    categoryList->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                },{
                    t->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposble)
    }

}