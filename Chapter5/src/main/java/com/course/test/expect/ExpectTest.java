package com.course.test.expect;

import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

public class ExpectException {
    @Test(ExpectedExceptions = RuntimeException.class)
    public void expectFail(){
        System.out.println("这是一个失败的异常测试");
    }
}
