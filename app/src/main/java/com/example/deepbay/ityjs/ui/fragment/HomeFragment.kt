package com.example.deepbay.ityjs.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.base.BaseFragment
import com.example.deepbay.ityjs.mvp.contract.HomeContract
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.mvp.presenter.HomePresenter
import com.example.deepbay.ityjs.net.exception.ErrorStatus
import com.example.deepbay.ityjs.showToast
import com.example.deepbay.ityjs.ui.adapter.HomeAdapter
import com.hazz.kotlinmvp.utils.StatusBarUtil
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author     IT烟酒僧
 * created   2018/10/26 12:38
 * desc:
 */
class HomeFragment:BaseFragment(),HomeContract.View{


    private val mPresenter by lazy {
        HomePresenter()
    }

    private var mTitle:String?=null
    private var num:Int=1
    private var mHomeAdapter: HomeAdapter?=null
    private var loadingMore=false
    private var isRefresh=false
    private var mMaterialHeader:MaterialHeader?=null
    companion object {
        fun getInstance(title:String):HomeFragment{
            val fragment=HomeFragment()
            val bundle=Bundle()
            fragment.arguments=bundle
            fragment.mTitle=title
            return fragment;
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }
    override fun getlayoutId(): Int=R.layout.fragment_home


    override fun initView() {
        //此处的this 代表view:Ibaseview
        //initView 给fragment设置数据,设置完后绑定
        mPresenter.attachView(this)
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener{
            isRefresh=true
            mPresenter.requestHomeData(num)
        }

        mMaterialHeader=mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主体颜色

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    val childCount=mRecyclerView.childCount
                    val itemCount=mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem=(mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem+childCount==itemCount){
                        if (!loadingMore){
                            loadingMore=true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition==0){
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text=""
                }else{
                    if (mHomeAdapter?.mData!!.size>1){
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList=mHomeAdapter!!.mData
                        val item=itemList[currentVisibleItemPosition+mHomeAdapter!!.bannerItemSize-1]
                        if (item.type=="textHeader"){
                            tv_header_title.text=item.data?.text
                        }else{
                            tv_header_title.text=simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        //iv_search.setOnClickListener()
        mLayoutStatusView=multipleStatusView
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it,toolbar) }
    }

    override fun lazyLoad() {
        //因为持有p对象,调用p的方法,将参数传递过去,p请求出来结果后,更新model

        mPresenter.requestHomeData(num)
    }

    //因为p持有view对象,数据加载完成后,调用此方法,将数据传递过来,利用传递过来的数据,更新ui
    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)

        //Adapter
        mHomeAdapter=activity?.let { HomeAdapter(it,homeBean.issueList[0].itemList) }
        //设置banner大小
        //mHomeAdapter?.

        mRecyclerView.adapter=mHomeAdapter
        mRecyclerView.layoutManager=linearLayoutManager
        mRecyclerView.itemAnimator=DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode==ErrorStatus.NETWORK_ERROR){
            mLayoutStatusView?.showNoNetwork()
        }else{
            mLayoutStatusView?.showError()
        }
    }


    //因为show和dismiss是公共方法,所以需要抽取出来放到IbaseView接口中,setData,setMoreData,showError是home独有方法,需要另写Ihomeview接口

    //p中加载数据前,调用此方法,传递展示progress意图,activiy中实现此方法,进行具体的ui逻辑
    override fun showLoading() {
        if (isRefresh){
            isRefresh=false;
            mLayoutStatusView?.showLoading()

        }
    }

    //p中数据加载完成后,调用此方法,传递展示progress意图,activiy中实现此方法,进行具体的ui逻辑
    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getColor(colorId:Int):Int{
        return resources.getColor(colorId)
    }
}