package com.example.deepbay.ityjs.view

import android.support.design.widget.TabLayout
import java.lang.reflect.Field


/**
 * @author     IT烟酒僧
 * created   2019/3/26 17:28
 * desc:
 */
object TablayoutHelper{
    fun setUpIndicatorWidth(tabLayout: TabLayout){
        val tabLayoutClass=tabLayout.javaClass
        var tabStrip:Field?=null
        try {
            tabStrip=tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip!!.isAccessible=true
        }catch (e:NoSuchFieldError){
            e.printStackTrace()
        }
    }
}