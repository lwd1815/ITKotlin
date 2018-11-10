package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.HomeContract
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.mvp.presenter.HomePresenter


/**
 * @author     IT烟酒僧
 * created   2018/10/26 12:38
 * desc:
 */
class HomeFragment:BaseFragment(),HomeContract.View{


    private val mPresenter by lazy {
        HomePresenter()
    }

    private var mTitle:String?=null
    private var num:Int=1
   // private var mHomeAdapter: HomeAdapter?=null

    companion object {
        fun getInstance(title:String):HomeFragment{
            val fragment=HomeFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            return fragment;
        }
    }


    override fun getlayoutId(): Int=R.layout.fragment_home


    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun setHomeData(homeBean: HomeBean) {

    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}