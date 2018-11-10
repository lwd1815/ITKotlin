package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2018/11/9 12:25
 * desc:   首页精选model
 */
class HomeModel{
    /**
     * 获取首页Banner数据
     */
    fun requestHomeData(num:Int):Observable<HomeBean>{
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.inToMain())
    }

    /**
     * 加载更多
     */

    fun loadMoreData(url:String):Observable<HomeBean>{
        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.inToMain())
    }
}