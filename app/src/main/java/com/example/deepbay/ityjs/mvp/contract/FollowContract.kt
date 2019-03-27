package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2019/3/27 11:17
 * desc:
 */
interface FollowContract{
    interface View:IBaseView{
        /**
         * 设置关注信息数据
         */

        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Persenter:IPresenter<View>{
        /**
         * 获取List
         */

        fun requestFollowList()

        /**
         * 加载更多
         */

        fun loadMoreData()
    }
}