package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:39
 * desc:
 */
class FollowFragment:BaseFragment(){

    private var mTitle:String?=null

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

}