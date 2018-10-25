package com.example.deepbay.ityjs

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.hazz.kotlinmvp.utils.DisplayManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.Bugly
import com.tencent.bugly.Bugly.applicationContext
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.loader.app.DefaultApplicationLike
import kotlin.properties.Delegates


/**
 * @author     IT烟酒僧
 * created   2018/10/25 18:23
 * desc:
 */
class ItApplicationLike :DefaultApplicationLike{


    private var refWatcher: RefWatcher? = null

    companion object {

        val TAG="TinkerItApplicationLike"

        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as ItApplicationLike
            return myApplication.refWatcher
        }

    }

    constructor(application:Application,tinkerFlags:Int,tinkerLoadVerifyFlag:Boolean,
                applicationStartElapsedTime:Long,applicationStartMillisTime:Long,tinkerResultIntent:Intent)
                :super(application,tinkerFlags,tinkerLoadVerifyFlag,applicationStartElapsedTime,applicationStartMillisTime,tinkerResultIntent)

    override fun onCreate() {
        super.onCreate()
        Bugly.init(application,"c8f3b1e599",false)
        context = applicationContext
//        refWatcher = setupLeakCanary()
        initConfig()
        DisplayManager.init(context)
        //registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
//        MultiDex.install(base)
        Beta.installTinker(this)
    }

//    private fun setupLeakCanary(): RefWatcher {
//        return if (LeakCanary.isInAnalyzerProcess(context)) {
//            RefWatcher.DISABLED
//        } else LeakCanary.install(this)
//    }


    /**
     * 初始化配置
     */
    private fun initConfig() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

}