package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2018/11/9 12:20
 * desc:
 */
interface HomeContract{
    interface View:IBaseView{
        /**
         * 設置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)
        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList:ArrayList<HomeBean.Issue.Item>)
        /**
         * 设置错误信息
         */
        fun showError(msg:String,errorCode: Int)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num:Int)
        /**
         * 加载更多数据
         */
        fun loadMoreData()
    }
}