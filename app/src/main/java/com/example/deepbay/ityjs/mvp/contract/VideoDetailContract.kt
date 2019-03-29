package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2019/3/29 11:49
 * desc:
 */
interface VideoDetailContract{
    interface View:IBaseView{
    /**
     * 设置视频播放源
     */

    fun setVideo(url:String)

    /**
     * 设置视频信息
     */

    fun setVideoInfo(itemInfo:HomeBean.Issue.Item)

    /**
     * 设置背景
     */

    fun setBackground(url: String)

    /**
     * 设置最新相关视频
     */

    fun setRecentRelatedVideo(itemList:ArrayList<HomeBean.Issue.Item>)

    /**
     * 设置错误信息
     */

    fun setErrorMsg(errorMsg:String)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 加载视频信息
         */

        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)
        /**
         * 请求相关的视频数据
         */

        fun requestRelatedVideo(id:Long)
    }
}