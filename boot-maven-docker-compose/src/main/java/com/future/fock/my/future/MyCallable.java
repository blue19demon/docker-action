package com.future.fock.my.future;
/**
 * 返回数据的接口
 */
public interface MyCallable<T> extends SuperCallable<T>{

    T getResult();
    
    void setResult(T t);
}