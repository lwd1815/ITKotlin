package com.example.deepbay.ityjs.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.CategoryContract
import com.example.deepbay.ityjs.mvp.model.bean.CategoryBean
import com.example.deepbay.ityjs.mvp.presenter.CategoryPresenter
import com.example.deepbay.ityjs.net.exception.ErrorStatus
import com.example.deepbay.ityjs.showToast
import com.example.deepbay.ityjs.ui.adapter.CategoryAdapter
import com.hazz.kotlinmvp.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:26
 * desc:
 */
class CategoryFragment:BaseFragment(),CategoryContract.View{


    private var mTitle:String?=null

    private val mPresenter by lazy { CategoryPresenter() }

    private var mcategoryList=java.util.ArrayList<CategoryBean>()

    private val mAdapter by lazy { activity?.let { CategoryAdapter(it,mcategoryList,R.layout.item_category) } }


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
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView

        mRecyclerView.adapter=mAdapter
        mRecyclerView.layoutManager=GridLayoutManager(activity,2)
        mRecyclerView.addItemDecoration(object :RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
                val position=parent.getChildPosition(view)
                val offset=DisplayManager.dip2px(2f)!!
                outRect.set(if (position%2==0)0 else offset,offset,if (position%2==0) offset else 0,offset)
            }
        })


    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    override fun showCategory(categorylist: ArrayList<CategoryBean>) {
        mcategoryList=categorylist
        mAdapter?.setData(mcategoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode==ErrorStatus.NETWORK_ERROR){
            multipleStatusView?.showNoNetwork()
        }else{
            multipleStatusView?.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView?.showContent()
    }

}