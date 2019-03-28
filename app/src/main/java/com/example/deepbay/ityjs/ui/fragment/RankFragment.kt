package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.RankContract
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.mvp.presenter.RankPresenter
import com.example.deepbay.ityjs.net.exception.ErrorStatus
import com.example.deepbay.ityjs.showToast
import com.example.deepbay.ityjs.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.fragment_rank.*


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:44
 * desc:
 */
class RankFragment:BaseFragment(),RankContract.View{

    private val mPresenter by lazy { RankPresenter() }
    private var itemList=ArrayList<HomeBean.Issue.Item>()
    private var apiUrl:String?=null
    private val mAdapter by lazy { activity?.let { CategoryDetailAdapter(it,itemList,R.layout.item_category_detail) } }

    companion object {
        fun getInstance(apiUrl:String):RankFragment{
            val fragment=RankFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            fragment.apiUrl=apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getlayoutId(): Int = R.layout.fragment_rank

    override fun initView() {
        mRecyclerView.layoutManager=LinearLayoutManager(activity)
        mRecyclerView.adapter=mAdapter
        mLayoutStatusView=multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()){
            mPresenter.requestRankList(apiUrl!!)
        }
    }


    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()
        mAdapter?.addData(itemList)
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

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}