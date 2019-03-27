package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:26
 * desc:
 */
class CategoryFragment:BaseFragment(){

    private var mTitle:String?=null

    companion object {
        fun getInstance(title:String):CategoryFragment{
            val fragment=CategoryFragment()
            val bundle=Bundle()
            fragment.mTitle=title
            fragment.arguments=bundle
            return fragment;
        }
    }

    override fun getlayoutId(): Int = R.layout.fragment_category

    override fun initView() {

    }

    override fun lazyLoad() {

    }

}