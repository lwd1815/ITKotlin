package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.FollowContract
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.mvp.presenter.FollowPresenter
import com.example.deepbay.ityjs.net.exception.ErrorStatus
import com.example.deepbay.ityjs.showToast
import com.example.deepbay.ityjs.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:39
 * desc:关注
 */
class FollowFragment:BaseFragment(),FollowContract.View{

    private var mTitle:String?=null

    private var itemList=ArrayList<HomeBean.Issue.Item>()

    private val mPresenter by lazy { FollowPresenter() }

    private val mFollowAdapter by lazy { activity?.let { FollowAdapter(it,itemList) } }

    /**
     * 是否加载更多
     */

    private var loadingMore=false

    init {
        mPresenter.attachView(this)
    }

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
        mRecyclerView.layoutManager=LinearLayoutManager(activity)
        mRecyclerView.adapter=mFollowAdapter

        /**
         * 实现自动加载
         */

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemcount=mRecyclerView.layoutManager.itemCount
                val lastvisibleItem=(mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!loadingMore && lastvisibleItem==(itemcount-1)){
                    loadingMore=true
                    mPresenter.loadMoreData()
                }
            }
        })

        mLayoutStatusView=mLayoutStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore=false
        itemList=issue.itemList
        mFollowAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
    showToast(errorMsg)
        if (errorCode==ErrorStatus.NETWORK_ERROR){
            multipleStatusView.showNoNetwork()
        }else{
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}