package com.example.deepbay.ityjs.net.exception


/**
 * @author     IT烟酒僧
 * created   2018/11/10 11:20
 * desc:
 */
class ApiException :RuntimeException{
    private var code:Int?=null
    constructor(throwable: Throwable,code:Int):super(throwable){
        this.code=code
    }

    constructor(message:String):super(Throwable(message))
}