package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.base.BaseFragmentAdapter
import com.example.deepbay.ityjs.mvp.contract.HotTabContract
import com.example.deepbay.ityjs.mvp.model.bean.TabInfoBean
import com.example.deepbay.ityjs.mvp.presenter.HotTabPresenter
import com.example.deepbay.ityjs.net.exception.ErrorStatus
import com.example.deepbay.ityjs.showToast
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*
import java.util.*


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:41
 * desc:      热门
 */
class HotFragment:BaseFragment(),HotTabContract.View{

    private val mPresenter by lazy { HotTabPresenter() }

    private var mTitle:String?=null

    /**
     * 存放tab标题
     */

    private val mTabTitleList=ArrayList<String>()

    private val mFragmentList = ArrayList<android.support.v4.app.Fragment>()

    companion object {
        fun getInstance(title:String):HotFragment{
            val fragment=HotFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            fragment.mTitle=title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }


    override fun getlayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        mLayoutStatusView=multipleStatusView
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it,toolbar) }
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList){
            it.name
        }
       tabInfoBean.tabInfo.tabList.mapTo(mFragmentList){
           RankFragment.getInstance(it.apiUrl)
       }

        mViewPager.adapter=BaseFragmentAdapter(childFragmentManager,mFragmentList,mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if(errorCode==ErrorStatus.NETWORK_ERROR){
                    multipleStatusView.showNoNetwork()
                }else{
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}