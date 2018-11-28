package com.example.deepbay.ityjs.view.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.deepbay.ityjs.view.recyclerview.MultipleType
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder


/**
 * @author     IT烟酒僧
 * created   2018/11/28 11:07
 * desc:
 */
abstract class CommonAdapter<T>(var mContex:Context,var mData:ArrayList<T>,private var mLayoutId:Int): RecyclerView.Adapter<ViewHolder>(){
    protected var mInflater:LayoutInflater?=null
    private var mTypesupport:MultipleType<T>?=null


    //使用接口回调点击事件

    private var mItemClickListener:OnItemClickListener?=null

    //使用接口回调点击事件

    private var mItemLongClickListener:OnItemLongClickListener?=null

    init {
        mInflater= LayoutInflater.from(mContex)
    }

    //需要多布局

    constructor(context: Context,data:ArrayList<T>,typeSupport:MultipleType<T>):this(context,data,-1){
        this.mTypesupport=typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypesupport!=null){
            mLayoutId=viewType
        }
        //创建view
        val view=mInflater?.inflate(mLayoutId,parent,false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        return mTypesupport?.getLayoutId(mData[position],position)?:super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //绑定数据
        bindData(holder,mData[position],position)

        mItemClickListener?.let { holder.itemView.setOnClickListener{mItemClickListener!!.onItemClick(mData[position],position)} }

        //长按点击事件
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }
    }

    /**
     * 将必要参数传递出去
     */
    protected abstract fun bindData(holder: ViewHolder,data:T,position: Int)


    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        this.mItemClickListener=itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener){
        this.mItemLongClickListener=itemLongClickListener
    }
}

