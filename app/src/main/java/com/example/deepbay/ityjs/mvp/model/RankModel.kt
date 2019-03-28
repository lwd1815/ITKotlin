package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/28 17:05
 * desc:      排行榜model
 */
class RankModel{
    /**
     * 获取排行榜
     */

    fun requestRankList(apiUrl:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.inToMain())
    }
}