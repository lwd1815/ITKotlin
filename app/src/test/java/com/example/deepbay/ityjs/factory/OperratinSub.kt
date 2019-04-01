package com.example.deepbay.ityjs.factory


/**
 * @author     IT烟酒僧
 * created   2019/4/1 15:17
 * desc:
 */
class OperratinSub: Operation() {
    override fun GetResult(): Double {
        var result:Double=0.0
        result=getNumberA()-getNumberB()
        return result
    }
}