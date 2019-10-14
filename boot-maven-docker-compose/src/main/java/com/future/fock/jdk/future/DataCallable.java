package com.future.fock.jdk.future;

import java.util.concurrent.Callable;

public class DataCallable implements Callable<String> {

    private String result;

    public DataCallable(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(result);
        //模拟耗时的构造数据过程
        Thread.sleep(1000);
        return sb.toString();
    }
}