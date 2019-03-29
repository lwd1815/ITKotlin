package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/29 12:37
 * desc:
 */
class VideoDetailModel{
    fun requestRelatedData(id:Long):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getRelateData(id)
                .compose(SchedulerUtils.inToMain())
    }
}