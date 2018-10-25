package com.example.deepbay.ityjs.base


/**
 * @author     IT烟酒僧
 * created   2018/10/24 16:49
 * desc:
 */
interface IPresenter<in V:IBaseView>{

   // ? extends E  其实就是使用点协变，允许传入的参数可以是泛型参数类型为 Number 子类的任意类型。

    //当然，也有 ? super E  的用法，这表示元素类型为 E 及其父类，这个通常也叫作逆变。
    fun attachView(mRootView:V)
    fun detachView()
}