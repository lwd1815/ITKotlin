package com.example.deepbay.ityjs

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.hazz.kotlinmvp.utils.DisplayManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import kotlin.properties.Delegates


/**
 * @author     IT烟酒僧
 * created   2018/10/24 15:50
 * desc:
 */

class MyApplication : Application(){

    private var refWatcher: RefWatcher? = null

    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        refWatcher = setupLeakCanary()
        initConfig()
        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initBugly()

    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    private fun  initBugly(){
        Bugly.init(this,"c8f3b1e599",false)
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true
        // 补丁回调接口
        Beta.betaPatchListener = object : BetaPatchListener {
            override fun onPatchReceived(patchFile: String) {
                //        Toast.makeText(getApplication(), "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
            }

            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                //        Toast.makeText(getApplication(),
                //                String.format(Locale.getDefault(), "%s %d%%",
                //                        Beta.strNotificationDownloading,
                //                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)),
                //                Toast.LENGTH_SHORT).show();
            }

            override fun onDownloadSuccess(msg: String) {
                //        Toast.makeText(getApplication(), "补丁下载成功", Toast.LENGTH_SHORT).show();
            }

            override fun onDownloadFailure(msg: String) {
                //        Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();

            }

            override fun onApplySuccess(msg: String) {
                //        Toast.makeText(getApplication(), "补丁应用成功", Toast.LENGTH_SHORT).show();
            }

            override fun onApplyFailure(msg: String) {
                //        Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
            }

            override fun onPatchRollback() {

            }
        }
    }



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


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        Beta.installTinker()
    }
}
