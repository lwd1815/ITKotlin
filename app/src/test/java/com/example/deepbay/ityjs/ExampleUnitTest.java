package com.example.deepbay.ityjs;

import com.example.deepbay.ityjs.factory.FactoryModel;
import com.example.deepbay.ityjs.factory.Operation;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Operation mOperation;

    @Test
    public void addition_isCorrect() {
        //assertEquals(4, 2 + 2);
        mOperation = FactoryModel.Companion.creatOperate("+");

        mOperation.setNumberA(1);
        mOperation.setNumberB(4);

        double v = mOperation.GetResult();

        System.out.println("v=="+v);

    }
}