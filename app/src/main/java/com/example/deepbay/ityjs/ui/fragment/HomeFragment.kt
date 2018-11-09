package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment


/**
 * @author     IT烟酒僧
 * created   2018/10/26 12:38
 * desc:
 */
class HomeFragment:BaseFragment(){
    private val mPresenter by lazy {  }

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

}