package com.example.deepbay.ityjs.rx


/**
 * @author     IT烟酒僧
 * created   2018/11/10 16:15
 * desc:
 */
object SchedulerUtils{
    fun <T> inToMain():IoMainScheduler<T>{
        return IoMainScheduler()
    }
}