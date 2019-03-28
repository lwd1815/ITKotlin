package com.example.deepbay.ityjs.mvp.model

import com.example.deepbay.ityjs.mvp.model.bean.CategoryBean
import com.example.deepbay.ityjs.net.RetrofitManager
import com.example.deepbay.ityjs.rx.SchedulerUtils
import io.reactivex.Observable


/**
 * @author     IT烟酒僧
 * created   2019/3/28 11:18
 * desc: 分类数据模型
 */

class CategoryModel{
    /**
     * 获取分类信息
     */

    fun getCategoryData():Observable<ArrayList<CategoryBean>>{
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.inToMain())
    }
}