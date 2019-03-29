package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2019/3/29 10:19
 * desc:
 */
class CategoryDetailContract{
    interface View:IBaseView{
        /**
         * 设置列表数据
         */
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)
    }

    interface Presenter:IPresenter<View>{
        fun getCategoryDetailList(id:Long)
        fun loadMoreData()
    }
}