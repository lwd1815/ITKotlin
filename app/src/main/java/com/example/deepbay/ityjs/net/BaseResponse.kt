package com.example.deepbay.ityjs.net


/**
 * @author     IT烟酒僧
 * created   2018/11/10 15:07
 * desc:    封装返回的数据
 */

class BaseResponse<T>(val code: Int,
                      val msg: String,
                      val data: T
)