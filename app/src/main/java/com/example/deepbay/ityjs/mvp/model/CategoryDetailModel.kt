package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/29 10:22
 * desc:
 */
class CategoryDetailModel{
    /**
     * 获取分类下的List数据
     */
    fun getCategoryDetailList(id:Long):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getCategoryDetailList(id)
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
