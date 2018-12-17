package com.example.deepbay.ityjs.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
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
import io.reactivex.Observable
import java.util.*


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
        when (getItemViewType(position)){
            ITEM_TYPE_BANNER->{
                //take 返回一个从头到尾的集合
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList=ArrayList<String>()
                val bannerTitleList=ArrayList<String>()
                //取出banner显示的img和title
                Observable.fromIterable(bannerItemData)
                        .subscribe{list->
                            bannerFeedList.add(list.data?.cover?.feed?:"")
                            bannerTitleList.add(list.data?.title?:"")
                        }
                //设置banner
                /**
                 * with函数是将某个对象作为参数,在函数中可以用this代替改对象,this可以省略
                 */
                with(holder){
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size>1)
                        setData(bannerFeedList,bannerFeedList)
                        setAdapter{
                            banner, _, feedImageUrl, position ->
                            Glide.with(mContex)
                                    .load(feedImageUrl)
                                    .transition(DrawableTransitionOptions().crossFade())
                                    .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                                    .into(banner.getItemImageView(position))
                        }
                    }
                }

                //没有使用到的参数kotlin中用"-"代替
                holder.getView<BGABanner>(R.id.banner).setDelegate{
                    _,imageView,_,i->
                    goToVideoPlayer(mContex as Activity,imageView,bannerItemData[i])
                }

            }
            //textHeader
            ITEM_TYPE_TEXT_HEADER->{
                holder.setText(R.id.tvHeader,mData[position+bannerItemSize-1].data?.text?:"")
            }

            //content
            ITEM_TYPE_CONTENT->{
                setVideoItem(holder,mData[position+bannerItemSize-1])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            ITEM_TYPE_BANNER->
                ViewHolder(inflaterView(R.layout.item_home_banner,parent))
            ITEM_TYPE_TEXT_HEADER->
                ViewHolder(inflaterView(R.layout.item_home_header,parent))
            else->
                ViewHolder(inflaterView(R.layout.item_home_content,parent))
        }
    }

    /**
     * 加载content item
     */
    private fun setVideoItem(holder: ViewHolder,item:HomeBean.Issue.Item){
        val itemData=item.data

        val defAvatar=R.mipmap.default_avatar
        val cover=itemData?.cover?.feed
        var avatar=itemData?.author?.icon
        var tagText:String?="#"
        //作者出处为空,就显获取提供者的信息
        if (avatar.isNullOrEmpty()){
            avatar=itemData?.provider?.icon
        }

        //加载封面页
        Glide.with(mContex)
                .load(cover)
                .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_cover_feed))
        //如果提供了作者信息为空,就显示默认
        if (avatar.isNullOrEmpty()){
            Glide.with(mContex)
                    .load(cover)
                    .apply(RequestOptions().placeholder(R.mipmap.default_avatar))
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))
        }else{
            Glide.with(mContex)
                    .load(cover)
                    .apply(RequestOptions().placeholder(R.mipmap.default_avatar))
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))
        }

        //holder.setText(R.id.title,itemData?.title?:"")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText+=(it.name+"/")
        }
        //格式化时间
        val timeFormat= durationFormat(itemData?.duration)
        tagText+=timeFormat
        holder.setText(R.id.tv_tag,tagText!!)
        holder.setText(R.id.tv_category,"#"+itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContex as Activity,holder.getView(R.id.iv_cover_feed),item)
        })
    }

    /**
     * 跳转到视屏详情页播放
     */

    private fun goToVideoPlayer(activity: Activity,view: View,itemData:HomeBean.Issue.Item){
        val intent=Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA,itemData)
        //intent.putExtra(VideoDetailActivity.)
    }
    /**
     * 加载布局
     */

    private fun inflaterView(mLayoutId: Int, parent: ViewGroup):View{
        val view =mInflater?.inflate(mLayoutId,parent,false)
        return view?:View(parent.context)
    }
}


