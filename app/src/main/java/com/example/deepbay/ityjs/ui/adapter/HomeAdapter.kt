package com.example.deepbay.ityjs.ui.adapter

import android.content.Context
import com.example.deepbay.ityjs.mvp.model.bean.HomeBean
import com.example.deepbay.ityjs.view.recyclerview.ViewHolder
import com.example.deepbay.ityjs.view.recyclerview.adapter.CommonAdapter


/**
 * @author     IT烟酒僧
 * created   2018/11/10 18:05
 * desc:
 */
class HomeAdapter(context: Context,data:ArrayList<HomeBean.Issue.Item>):CommonAdapter<HomeBean.Issue.Item>(context,data,-1){
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {

    }

}


