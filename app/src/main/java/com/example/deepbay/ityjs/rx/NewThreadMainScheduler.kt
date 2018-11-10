package com.example.deepbay.ityjs.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author     IT烟酒僧
 * created   2018/11/10 16:14
 * desc:
 */
class NewThreadMainScheduler<T> private constructor():BaseScheduler<T>(Schedulers.newThread(),AndroidSchedulers.mainThread())