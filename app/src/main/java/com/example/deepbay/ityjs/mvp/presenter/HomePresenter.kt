package com.example.deepbay.ityjs.mvp.presenter

import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.mvp.contract.HomeContract
import com.example.deepbay.ityjs.mvp.model.HomeModel
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.exception.ExceptionHandle


/**
 * @author     IT烟酒僧
 * created   2018/10/27 18:06
 * desc: 首页精选Presenter
 *
 * (数据是Banner数据和一页数据组合成的HomeBean,查看接口然后再分析就明白了)
 */

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {


    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = null //加载首页的banner 数据+-一页数据合并后,nextPageUrl没add

    private val homeModel: HomeModel by lazy {
        HomeModel()
    }

    /**
     * 获取首页精选数据banner 加一页数据
     */
    override fun requestHomeData(num: Int) {
        //检测是否绑定View
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
                .flatMap({ homeBean ->
                    //过滤掉Banner2(包含广告,等不需要的type),具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除item
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean //记录第一页是当前banner 数据

                    //根据nextPageUrl请求下一页数据
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                }).subscribe({ homebean ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = homebean.nextPageUrl
                        //过滤掉Banner2(包含广告,等不需要的Type),具体查看接口分析
                        val newBannerItemList = homebean.issueList[0].itemList
                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除item
                            newBannerItemList.remove(item)
                        }
                        //重新复制Banner长度
                        bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size
                        //赋值过滤后的数据+Banner数据
                        bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                        setHomeData(bannerHomeBean!!)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable=nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean->
                        mRootView?.apply {
                            //过滤掉Banner2(广告,等不需要的Type),具体查看接口分析
                            val newItemList=homeBean.issueList[0].itemList
                            newItemList.filter { item->
                                item.type=="banner2"||item.type=="horizontalScrollCard"
                            }.forEach{ item->
                                //移除item
                                newItemList.remove(item)
                            }
                            nextPageUrl=homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }
                    },{ t ->
                        mRootView?.apply {
                            dismissLoading()
                            showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                        }
                    })
        }
        if (disposable!=null){
            addSubscription(disposable)
        }
    }
}