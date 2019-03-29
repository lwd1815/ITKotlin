package com.example.deepbay.ityjs.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.deepbay.ityjs.Constants
import com.example.deepbay.ityjs.R
import com.example.deepbay.ityjs.durationFormat
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.ui.activity.VideoDetailActivity
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder
import com.example.deepbay.ityjs.view.recyclerview.adapter.CommonAdapter


/**
 * @author     IT烟酒僧
 * created   2019/3/28 17:17
 * desc: 分类详情 Adapter
 */
class CategoryDetailAdapter(context: Context,datalist:ArrayList<HomeBean.Issue.Item>,layoutId:Int):
        CommonAdapter<HomeBean.Issue.Item>(context,datalist,layoutId){
    fun addData(datalist: ArrayList<HomeBean.Issue.Item>){
        this.mData.addAll(datalist)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        setvideoItem(holder,data)
    }

    /**
     * 加载content item
     */

    private fun setvideoItem(holder: ViewHolder,item:HomeBean.Issue.Item){
        val itemData=item.data
        val conver=itemData?.cover?.feed?:""
        //加载封页图
        Glide.with(mContex).load(conver)
                .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_image))

        holder.setText(R.id.tv_title,itemData?.title?:"")
        //格式化时间
        val timeFormat= durationFormat(itemData?.duration)
        holder.setText(R.id.tv_tag,"#${itemData?.category}/${timeFormat}")

        holder.setOnItemClickListener(listener= View.OnClickListener {
            goToVideoPlay(mContex as Activity,holder.getView(R.id.iv_image),item)
        })

        //正常的点击
//        holder.setOnItemClickListener(object :View.OnClickListener{
//            override fun onClick(v: View?) {
//
//            }
//        })
    }

    /**
     * 跳转到视屏详情页播放
     */

    private fun goToVideoPlay(activity:Activity,view:View,itemData:HomeBean.Issue.Item){
        val intent =Intent(activity,VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA,itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION,true)

        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP){
            val pair=Pair<View,String>(view,VideoDetailActivity.IMG_TRANSITION)
            val activityOptions=ActivityOptionsCompat.makeSceneTransitionAnimation(activity,pair)
            ActivityCompat.startActivity(activity,intent,activityOptions.toBundle())
        }else{
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in,R.anim.anim_out)
        }
    }
}
