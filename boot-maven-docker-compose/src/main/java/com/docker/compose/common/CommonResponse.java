package com.docker.compose.common;

public class CommonResponse<T> {
	
	private final static String SUCCESS_CODE="200";
	private final static String ERROR_CODE="500";
	private String code;
	private String message;
	private T data;
	private Long timestamp= System.nanoTime();  
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public CommonResponse(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public CommonResponse() {
		super();
	}
	public CommonResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public static CommonResponse<Object> success() {
		return new CommonResponse<Object>(SUCCESS_CODE, "操作成功");
	}
	
	
	public static <E> CommonResponse<E> success(E data) {
		return new CommonResponse<E>(SUCCESS_CODE, "操作成功",data);
	}
	
	
	public static <E> CommonResponse<E> fail() {
		return new CommonResponse<E>(ERROR_CODE, "操作失败");
	}
	
	public static <E> CommonResponse<E> fail(String code, String message) {
		return new CommonResponse<E>(code,message);
	}
	
	public static <E> CommonResponse<E> fail(String message) {
		return new CommonResponse<E>(ERROR_CODE,message);
	}
}
