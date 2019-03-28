package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.TabInfoBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/28 16:38
 * desc: 热门Model
 */
class HotTabModel{
    /**
     * 获取tabinfo
     */

    fun getTabInfo():Observable<TabInfoBean>{
        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.inToMain())
    }
}