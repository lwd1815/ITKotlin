package com.example.deepbay.ityjs.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.deepbay.ityjs.MyApplication
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.mvp.model.bean.CategoryBean
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder
import com.example.deepbay.ityjs.view.recyclerview.adapter.CommonAdapter


/**
 * @author     IT烟酒僧
 * created   2019/3/28 11:32
 * desc:
 */
class CategoryAdapter(mContext:Context,categoryList:ArrayList<CategoryBean>,layoutId:Int):
        CommonAdapter<CategoryBean>(mContext,categoryList,layoutId){


    private var textTypeface:Typeface?=null

    init {
        textTypeface= Typeface.createFromAsset(MyApplication.context.assets,"fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }


    /**
     * 设置新数据
     */

    fun setData(categoryList: ArrayList<CategoryBean>){
        mData.clear()
        mData=categoryList
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.setText(R.id.tv_category_name,"#${data.name}")
        //设置方正兰亭细黑简体
        holder.getView<TextView>(R.id.tv_category_name).typeface=textTypeface
        holder.setImagePath(R.id.iv_category,object:ViewHolder.HolderImageLoader(data.bgPicture){
            override fun loadImage(iv: ImageView, path: String) {
                Glide.with(mContex)
                        .load(path)
                        .apply(RequestOptions().placeholder(R.color.color_darker_gray))
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
            }
        })

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

            }
        })
    }
}