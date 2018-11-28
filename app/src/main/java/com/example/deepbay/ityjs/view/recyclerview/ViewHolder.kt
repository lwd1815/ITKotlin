package com.example.deepbay.ityjs.view.recyclerview

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


/**
 * @author     IT烟酒僧
 * created   2018/11/28 11:09
 * desc:
 */
class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    //用于缓存已找的界面
    private var mView:SparseArray<View>?=null
    init {
        mView= SparseArray()
    }

    fun <T:View>getView(viewId:Int):T{
        //对已有的View做缓存
        var view:View?=mView?.get(viewId)
        //使用缓存的方式减少findviewById的次数
        if (view==null){
            view=itemView.findViewById(viewId)
            mView?.put(viewId,view)
        }
        return view as T
    }


    //将view和viewid存储到集合中,获取前,现根据id查询,如果有,获取,如果没有findviewbyid获取,然后存入
    fun <T:ViewGroup>getViewGroup(viewId: Int):T{
        var view:View?=mView?.get(viewId)
        if (view==null){
            view=itemView.findViewById(viewId)
            mView?.put(viewId,view)
        }
        return view as T
    }


    @SuppressLint("SetTextI18n")
    //通用功能进行封装,设置文本 设置条目点击事件, 设置图片
    fun setText(viewId: Int,text:CharSequence):ViewHolder{
        val view=getView<TextView>(viewId)
        view.text=""+text
        return this
    }

    fun setHintText(viewId: Int,text: CharSequence):ViewHolder{
        val view=getView<TextView>(viewId)
        view.hint=""+text
        return this
    }

    /**
     * 设置本地图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    /**
     * 加载图片资源路径
     *
     * @param viewId
     * @param imageLoader
     * @return
     */
    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String) {

        /**
         * 需要去复写这个方法加载图片
         *
         * @param iv
         * @param path
         */
        abstract fun loadImage(iv: ImageView, path: String)
    }

    /**
     * 设置View的Visibility
     */
    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    /**
     * 设置条目点击事件
     */
    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    /**
     * 设置条目长按事件
     */
    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }

}