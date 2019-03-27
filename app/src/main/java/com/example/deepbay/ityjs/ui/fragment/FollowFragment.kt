package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.FollowContract
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:39
 * desc:关注
 */
class FollowFragment:BaseFragment(),FollowContract.View{
    private var mTitle:String?=null

    private var itemList=ArrayList<HomeBean.Issue.Item>()

    private val mPresenter by lazy {  }



    companion object {
        fun getInstance(title:String):FollowFragment{
            val fragment=FollowFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            fragment.mTitle=title
            return fragment;
        }
    }
    override fun getlayoutId(): Int = R.layout.layout_recyclerview

    override fun initView() {

    }

    override fun lazyLoad() {

    }

    override fun setFollowInfo(issue: HomeBean.Issue) {

    }

    override fun setError(errorMsg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}