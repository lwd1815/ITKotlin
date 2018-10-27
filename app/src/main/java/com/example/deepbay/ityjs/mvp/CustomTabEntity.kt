package com.example.deepbay.ityjs.mvp

import com.flyco.tablayout.listener.CustomTabEntity


/**
 * @author     IT烟酒僧
 * created   2018/10/27 16:59
 * desc:
 */
class TabEntity(var title:String,private var selectedIcon:Int,private var unSelectedIcon:Int):CustomTabEntity{
    override fun getTabSelectedIcon(): Int =selectedIcon
    override fun getTabTitle(): String =title
    override fun getTabUnselectedIcon(): Int =unSelectedIcon
}