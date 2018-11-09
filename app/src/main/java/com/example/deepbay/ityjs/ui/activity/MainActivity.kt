package com.example.deepbay.ityjs.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseActivity
import com.example.deepbay.ityjs.mvp.TabEntity
import com.example.deepbay.ityjs.ui.fragment.DisCoveryFragment
import com.example.deepbay.ityjs.ui.fragment.HomeFragment
import com.example.deepbay.ityjs.ui.fragment.HotFragment
import com.example.deepbay.ityjs.ui.fragment.MineFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mTitles= arrayOf("每日精选","发现","热门","我的")

    //未被选中的图标
    private val mIconUnSelectIds= intArrayOf(R.mipmap.ic_home_normal,R.mipmap.ic_discovery_normal,R.mipmap.ic_hot_normal,R.mipmap.ic_mine_normal)
    //被选中的图标
    private val mIconSelectIds= intArrayOf(R.mipmap.ic_home_selected,R.mipmap.ic_discovery_selected,R.mipmap.ic_hot_selected,R.mipmap.ic_mine_selected)
    private val mTabEntities=ArrayList<CustomTabEntity>()

    private var mHomeFragment:HomeFragment?=null
    private var mDiscoveryFragment:DisCoveryFragment?=null
    private var mHotFragment:HotFragment?=null
    private var mMineFragment:MineFragment?=null
    //当前选中索引
    private var mIndex=0



    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState!=null){
            mIndex=savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)

        initTab()
        it_main_tab_layout.currentTab=mIndex
        switchFragment(mIndex)
    }

    //初始化底部菜单
    private fun initTab(){
        (0 until mTitles.size).mapTo(mTabEntities){
            TabEntity(mTitles[it],mIconSelectIds[it],mIconUnSelectIds[it])
        }
        //为tab赋值
        it_main_tab_layout.setTabData(mTabEntities)
        it_main_tab_layout.setOnTabSelectListener(object :OnTabSelectListener{

            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }
            override fun onTabReselect(position: Int) {

            }
        })
    }


    /**
     * 切换Fragment
     */
    private fun switchFragment(position: Int){
        val transaction=supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when(position){
            0
            ->mHomeFragment?.let {
                transaction.show(it)
            }?:HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment=it
                transaction.add(R.id.it_main_fl_container,it,"home")
            }

        }
    }

    override fun layoutId(): Int =R.layout.activity_main

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }
    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }


}
