package com.example.deepbay.ityjs.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.view.recyclerview.MultipleType
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder
import com.example.deepbay.ityjs.view.recyclerview.adapter.CommonAdapter


/**
 * @author     IT烟酒僧
 * created   2019/3/27 16:10
 * desc: 关注Adapter
 */
class FollowAdapter(context:Context,dataList:ArrayList<HomeBean.Issue.Item>)
    :CommonAdapter<HomeBean.Issue.Item>(context,dataList,object :MultipleType<HomeBean.Issue.Item>{
    override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
        return when{
            item.type=="videoCollectionWithBrief"->
                R.layout.item_follow
            else->
                throw IllegalAccessException("Api 解析错误,出现其他类型")
        }
    }
}){
    fun addData(dataList: ArrayList<HomeBean.Issue.Item>){
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when{
            data.type=="videoCollectionWithBrief"->setAuthorInfo(data,holder)
        }
    }

    /**
     * 加载作者信息
     */

    private fun setAuthorInfo(item:HomeBean.Issue.Item,holder: ViewHolder){
        val headerData=item.data?.header
        /**
         * 加载作者头像
         */
        holder.setImagePath(R.id.iv_avatar,object :ViewHolder.HolderImageLoader(headerData?.icon!!){
            override fun loadImage(iv: ImageView, path: String) {
                Glide.with(mContex)
                        .load(path)
                        .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_avatar))
            }
        })

        holder.setText(R.id.tv_title,headerData.title)
        holder.setText(R.id.tv_desc,headerData.description)

        val recyclerView=holder.getView<RecyclerView>(R.id.fl_recyclerView)

        /**
         * 设置嵌套水平的RecyclerView
         */

        recyclerView.layoutManager=LinearLayoutManager(mContex as Activity,LinearLayoutManager.HORIZONTAL,false)
       // recyclerView.adapter=Followh

    }
}