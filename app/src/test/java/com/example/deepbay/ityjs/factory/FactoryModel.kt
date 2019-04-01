package com.example.deepbay.ityjs.factory


/**
 * @author     IT烟酒僧
 * created   2019/4/1 12:44
 * desc:    简单工厂模式
 */
class FactoryModel{
    companion object {
        fun creatOperate(operate:String):Operation{
            var oper: Operation? =null
            when(operate){
                "+"->
                    oper=OperratinAdd();
                "-"->
                    oper=OperratinSub()
                "*"
                ->
                    oper=OperratinMul()
                "/"->
                    oper=OperratinDiv()
            }

            return oper!!
        }
    }
}