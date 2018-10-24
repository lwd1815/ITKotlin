package com.example.deepbay.ityjs.base

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * @author     IT烟酒僧
 * created   2018/10/24 16:08
 * desc: 该类生成的每一个fragment 都将保存在内存之中
 * 因此适用于那些相对静态的页,数据也比较少的那种
 * 如果需要处理有很多页面,并且数据动态性较大,占用内存较多的情况
 * 应该使用FragmentStatePagerAdapter
 */
class BaseFragmentAdapter:FragmentPagerAdapter{

    private var fragmentList:List<Fragment>?=ArrayList()
    private var mTitles:List<String>?=null

    constructor(fm:FragmentManager,fragmentlists:List<Fragment>):super(fm){
        this.fragmentList=fragmentlists;
    }

    constructor(fm:FragmentManager,fragmentlists:List<Fragment>,mTitle:List<String>):super(fm){
        this.mTitles=mTitle;
        setFragments(fm,fragmentlists,mTitle)
    }

    //刷新fragment
    @SuppressLint("CommitTransaction")
    private fun setFragments(fm: FragmentManager, fragmentlists: List<Fragment>, mTitle: List<String>){
        this.mTitles=mTitle;
        if (this.fragmentList!=null){
            val ft=fm.beginTransaction()
            fragmentList?.forEach{
                ft.remove(it)
            }

            ft?.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList=fragmentlists
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (null!=mTitles)mTitles!![position] else ""
    }

    //!!表示不能为空
    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return  fragmentList!!.size
    }

}