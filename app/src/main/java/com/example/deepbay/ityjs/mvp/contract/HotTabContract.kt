package com.example.deepbay.ityjs.mvp.contract

import com.example.deepbay.ityjs.base.IBaseView
import com.example.deepbay.ityjs.base.IPresenter
import com.example.deepbay.ityjs.mvp.model.bean.TabInfoBean


/**
 * @author     IT烟酒僧
 * created   2019/3/28 16:40
 * desc:
 */
interface HotTabContract{
    interface View:IBaseView{
        /**
         * 设置TabInfo
         */

        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 获取TabInfo
         */

        fun getTabInfo()
    }
}