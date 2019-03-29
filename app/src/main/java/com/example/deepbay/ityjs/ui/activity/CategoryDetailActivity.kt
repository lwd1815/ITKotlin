package com.example.deepbay.ityjs.ui.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.deepbay.ityjs.Constants
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseActivity
import com.example.deepbay.ityjs.mvp.contract.CategoryDetailContract
import com.example.deepbay.ityjs.mvp.model.bean.CategoryBean
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.mvp.presenter.CategoryDetailPresenter
import com.example.deepbay.ityjs.ui.adapter.CategoryDetailAdapter
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_category_detail.*


/**
 * @author     IT烟酒僧
 * created   2019/3/29 10:38
 * desc:
 */
class CategoryDetailActivity:BaseActivity(),CategoryDetailContract.View{

    private val mPresenter by lazy { CategoryDetailPresenter() }

    private val mAdapter by lazy { CategoryDetailAdapter(this,itemList,R.layout.item_category_detail) }

    private var categoryData:CategoryBean?=null

    private var itemList=java.util.ArrayList<HomeBean.Issue.Item>()

    init {
        mPresenter.attachView(this)
    }

    /**
     * 是否加载更多
     */

    private var loadingMore=false

    override fun layoutId(): Int {
        return R.layout.activity_category_detail
    }

    override fun initData() {
       categoryData=intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
    }

    override fun initView() {
       setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{finish()}

        Glide.with(this)
                .load(categoryData?.headerImage)
                .apply(RequestOptions().placeholder(R.color.color_darker_gray))
                .into(imageView)

        tv_category_desc.text="#${categoryData?.description}#"
        collapsing_toolbar_layout.title=categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)

        mRecyclerView.layoutManager=LinearLayoutManager(this)
        mRecyclerView.adapter=mAdapter

        //实现自动加载

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount=mRecyclerView.layoutManager.itemCount
                val lastVisibleItem=(mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore&&lastVisibleItem==(itemCount-1)){
                    loadingMore=true
                    mPresenter.loadMoreData()
                }
            }
        })

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this,toolbar)

    }

    override fun start() {
        categoryData?.id?.let {
            mPresenter.getCategoryDetailList(it)
        }
    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore=false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}