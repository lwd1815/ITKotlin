package com.example.deepbay.ityjs.rx

import io.reactivex.*
import org.reactivestreams.Publisher


/**
 * @author     IT烟酒僧
 * created   2018/11/10 15:20
 * desc:    RxJava2.x 5中基础相应类型
 * https://www.jianshu.com/p/89af18d2daba
 */

//Transformer 顾名思义是转换器的意思,早在Rxjava1.x版本就有了
//Transformer 能够将一个Observable/Flowable/Single/Completable/Maybe对象转换成另一个Observable/Flowable/Single/Completable/Maybe
abstract class BaseScheduler<T> protected constructor(private val subscribeOnScheduler: Scheduler,
                                                      private val observeOnScheduler: Scheduler
                                                      ):ObservableTransformer<T,T>,
        SingleTransformer<T,T>,
        MaybeTransformer<T,T>,
        CompletableTransformer,
        FlowableTransformer<T,T>{


    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }
}