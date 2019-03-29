package com.example.deepbay.ityjs.mvp.presenter

import android.app.Activity
import com.example.deepbay.ityjs.MyApplication
import com.example.deepbay.ityjs.base.BasePresenter
import com.example.deepbay.ityjs.dataFormat
import com.example.deepbay.ityjs.mvp.contract.VideoDetailContract
import com.example.deepbay.ityjs.mvp.model.VideoDetailModel
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.exception.ExceptionHandle
import com.example.deepbay.ityjs.showToast
import com.hazz.kotlinmvp.utils.DisplayManager
import com.hazz.kotlinmvp.utils.NetworkUtil


/**
 * @author     IT烟酒僧
 * created   2019/3/29 12:38
 * desc:
 */
class VideoDetailPresenter:BasePresenter<VideoDetailContract.View>(),VideoDetailContract.Presenter{


    private val videoDetailModel by lazy { VideoDetailModel() }



    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo=itemInfo.data?.playInfo
        val netType= NetworkUtil.isWifi(MyApplication.context)
        //检查绑定情况
        checkViewAttached()
        if (playInfo!!.size>1){
            if (netType){
                for (i in playInfo){
                    if (i.type=="high"){
                        val playUrl=i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            }else{
                //否则就选高清适配
                for (i in playInfo){
                    if (i.type=="normal"){
                        val playUrl=i.url
                        mRootView?.setVideo(playUrl)
                        (mRootView as Activity).showToast("本次消耗${(mRootView as Activity).dataFormat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        }else{
            mRootView?.setVideo(itemInfo.data.playUrl)
        }

        //设置背景

        val backgroundUrl=itemInfo.data.cover.blurred+"/thumbnail/${DisplayManager.getScreenHeight()!!-DisplayManager.dip2px(250f)!!}X${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }
        mRootView?.setVideoInfo(itemInfo)
    }

    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable=videoDetailModel.requestRelatedData(id)
                .subscribe({
                    issue->
                    mRootView?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                },{
                    t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)
    }

}