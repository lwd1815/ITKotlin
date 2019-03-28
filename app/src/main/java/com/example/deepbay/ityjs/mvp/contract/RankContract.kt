package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2019/3/28 17:08
 * desc:
 */
interface RankContract{
    interface View:IBaseView{
        /**
         * 设置排行榜数据
         */

        fun setRankList(itemList:ArrayList<HomeBean.Issue.Item>)
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 获取tabinfo
         */

        fun requestRankList(apiUrl:String)
    }
}