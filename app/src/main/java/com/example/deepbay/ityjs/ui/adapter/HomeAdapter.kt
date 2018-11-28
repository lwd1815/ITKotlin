package com.example.deepbay.ityjs.ui.adapter

import android.content.Context
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder
import com.example.deepbay.ityjs.view.recyclerview.adapter.CommonAdapter


/**
 * @author     IT烟酒僧
 * created   2018/11/10 18:05
 * desc:
 */
class HomeAdapter(context: Context,data:ArrayList<HomeBean.Issue.Item>):CommonAdapter<HomeBean.Issue.Item>(context,data,-1){



    //banner作为recyclerview的第一项

    var bannerItemSize=0

    companion object {
        private const val ITEM_TYPE_BANNER=1
        private const val ITEM_TYPE_TEXT_HEADER=2
        private const val ITEM_TYPE_CONTENT=3

    }

    /**
     * 设置banner大小
     */

    fun setBannerSize(count:Int){
        bannerItemSize=count
    }

    /**
     * 添加更多数据
     */

    fun addItemData(itemList:ArrayList<HomeBean.Issue.Item>){
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    /**
     * 得到item类型
     */
    override fun getItemViewType(position: Int): Int {

        return when {
            position == 0 ->
                ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when{
            mData.size>bannerItemSize->mData.size-bannerItemSize+1
            mData.isEmpty()->0
            else->1
        }
    }


    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {

    }

}


