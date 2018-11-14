package com.example.deepbay.ityjs.net

import com.example.deepbay.ityjs.MyApplication
import com.example.deepbay.ityjs.api.ApiService
import com.example.deepbay.ityjs.api.UriConstant
import com.hazz.kotlinmvp.utils.AppUtils
import com.hazz.kotlinmvp.utils.NetworkUtil
import com.hazz.kotlinmvp.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @author     IT烟酒僧
 * created   2018/11/9 12:27
 * desc:
 */
object RetrofitManager {
    /**
     * kotlin中属性在声明的同时也要求要被初始化,否则会报错,可是有时候,我们并不想声明一个类型可控的对象,而且也没有办法在对象刚声明的时候就为它初始化,
     * 那么这时就需要用到kotlin提供的延迟初始化
     *
     * lateinit 和lazy 是kotlin中的两种不同的延迟初始化的实现
     *
     * lateinit 只用于变量var,而lazy只用于常量val
     *
     * lazy 应用于单例模式,而且当且仅当变量被第一次调用的时候,委托方法才会执行
     */

    //第一次调用后被初始化之后就不在走此方法
    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }
    private var token: String by Preference("token", "")

    private fun getRetrofit(): Retrofit {
        //获取retrofit的实例
        return Retrofit.Builder()
                .baseUrl(UriConstant.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //设置 请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50)

        return OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor())//添加参数
                .addInterceptor(addHeaderInterceptor())//token过滤
                .addInterceptor(httpLoggingInterceptor)//日志,所有的请求响应度看到
                .cache(cache)//添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()

    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requst: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                    .build()
            requst = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(requst)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)

        }
    }

    /**
     * 设置缓存
     */

    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                //有网络时,设置缓存超时时间0小时,意思就是不读取缓存数据,只对get有用,post没有缓存
                response.newBuilder()
                        .header("Cache-Control","public,max-age="+maxAge)
                        .removeHeader("Retrofit")//清除头信息,因为服务器如果不支持,会返回一些干扰信息,不清除下面无法生效
                        .build()
            }else{
                //无网络时,设置超时为4周,只对get有用,post没有缓存
                val maxStale=60*60*24*28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }
}