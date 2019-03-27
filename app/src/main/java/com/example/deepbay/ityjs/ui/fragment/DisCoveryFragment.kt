package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.base.BaseFragmentAdapter
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*
import java.util.*


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:36
 * desc:
 */
class DisCoveryFragment:BaseFragment(){

    private val tablist=ArrayList<String>()

    private val fragments=ArrayList<android.support.v4.app.Fragment>()

    private var mTitle:String?=null

    companion object {
        fun getInstance(title: String):DisCoveryFragment{
            val fragment=DisCoveryFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            fragment.mTitle=title
            return fragment
        }
    }

    override fun getlayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it,toolbar) }

        tv_header_title.text=mTitle

        tablist.add("关注")
        tablist.add("分类")

        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))


        /**
         *getSupportFragmentManager() 替换为getChildFragmentManager()
         */

        mViewPager.adapter=BaseFragmentAdapter(childFragmentManager,fragments,tablist)

        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun lazyLoad() {

    }

}