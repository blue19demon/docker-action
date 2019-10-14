package com.future.fock.my.future;

public abstract class OCallable<T> implements MyCallable<T> {

    private T result;

	@Override
	public T getResult() {
		return result;
	}

	@Override
	public void setResult(T t) {
		this.result=t;
	}

}