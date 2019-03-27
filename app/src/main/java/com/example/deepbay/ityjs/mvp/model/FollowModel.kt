package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/27 11:33
 * desc: 关注model
 */

class FollowModel{
    /**
     * 获取关注信息
     */

    fun requestFollowList():Observable<HomeBean.Issue>{
        return RetrofitManager.service.getFollowInfo()
                .compose(SchedulerUtils.inToMain())
    }


    /**
     * 加载更多
     */

    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.inToMain())
    }
}