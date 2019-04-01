package com.example.deepbay.ityjs.factory


/**
 * @author     IT烟酒僧
 * created   2019/4/1 12:46
 * desc:    运算类
 */
open class Operation{

    constructor()

    private var numberA:Double= 0.0
    private var numberB:Double=0.0

    fun setNumberA(A:Double){
        this.numberA=A;
    }

   fun getNumberA():Double{
        return numberA;
    }


    fun setNumberB(B:Double){
        this.numberB=B;
    }

    fun getNumberB():Double{
        return numberB;
    }


    open fun GetResult():Double{
        var result:Double=0.0
        return result;
    }
}