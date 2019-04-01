package com.example.deepbay.ityjs.factory


/**
 * @author     IT烟酒僧
 * created   2019/4/1 15:17
 * desc:
 */
class OperratinDiv: Operation() {
    override fun GetResult(): Double {
        var result:Double=0.0

        if (getNumberB()==0.0){
            throw Exception("除数不能为零")
        }
        result=getNumberA()/getNumberB()
        return result
    }
}