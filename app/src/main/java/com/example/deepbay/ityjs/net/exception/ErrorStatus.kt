package com.example.deepbay.ityjs.net.exception


/**
 * @author     IT烟酒僧
 * created   2018/11/10 11:22
 * desc: 用object声明一个类表示这是一个单类
 */
object ErrorStatus{

    /**
     * java中静态变量和静态方法可以直接用类名调用
     * kotlin中,静态方法和静态变量会被放在companion object中,当成伴生方法和伴生常量,如果想直接用类名调用,需要添加注解
     * 在companion object中的公共函数必须用使用@JvmStatic注解才能暴露为静态方法
     * 如果没有这个注解,这些函数仅可用作静态Companion字段上的实例方法
     *
     */
    /**
     * 响应成功
     */
    @JvmField
    val SUCCESS=0
    /**
     * 未知错误
     */
    @JvmField
    val UNKNOWN_ERROR=1002
    /**
     * 服务器内部错误
     */
    @JvmField
    val SERVER_ERROR=1003
    /**
     * 网络连接超时
     */
    @JvmField
    val NETWORK_ERROR=1004
    /**
     * API解析异常(或者第三方数据结构更改)等其他异常
     */
    @JvmField
    val API_ERROR=1005
}