package com.example.deepbay.ityjs.view

import android.os.Build
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import com.hazz.kotlinmvp.utils.DisplayManager
import java.lang.reflect.Field


/**
 * @author     IT烟酒僧
 * created   2019/3/26 17:28
 * desc: 利用反射修改indicator宽度
 */
object TablayoutHelper{
    fun setUpIndicatorWidth(tabLayout: TabLayout){
        val tabLayoutClass=tabLayout.javaClass
        var tabStrip:Field?=null
        try {
            tabStrip=tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip!!.isAccessible=true
        }catch (e:NoSuchFieldException){
            e.printStackTrace()
        }

        var layout:LinearLayout?=null

        try {
            if (tabStrip!=null){
                //as 相当于java中的强转
                layout= tabStrip.get(tabLayout) as LinearLayout
            }


            for (i in 0 until layout!!.childCount){
                val child=layout.getChildAt(i)

                child.setPadding(0,0,0,0)

                val params=LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1f)

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
                    params.marginStart=com.hazz.kotlinmvp.utils.DisplayManager.dip2px(50f)!!

                    params.marginEnd=DisplayManager.dip2px(50f)!!
                }

                child.layoutParams=params
                child.invalidate()
            }


        }catch (e:IllegalAccessException){
            e.printStackTrace()
        }
    }
}