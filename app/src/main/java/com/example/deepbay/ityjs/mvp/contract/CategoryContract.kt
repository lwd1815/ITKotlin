package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.CategoryBean


/**
 * @author     IT烟酒僧
 * created   2019/3/28 11:21
 * desc: 分类 契约类
 */

interface CategoryContract{
    interface View:IBaseView{
        /**
         * 显示分类信息
         */
        fun showCategory(categorylist:ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 获取分类信息
         */

        fun getCategoryData()
    }
}
