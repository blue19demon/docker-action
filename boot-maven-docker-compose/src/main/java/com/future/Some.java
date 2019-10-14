package com.future;


import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;

@Log4j
public class Some {

	public static void main(String[] args) {
		B b = new B();
		b.setName("a");
		A a = b;
		init(b);
		String c = "10";
		System.out.println(c);
		b.init(c);
		System.out.println(c);
		log.info(JSONObject.toJSONString(b));
		log.info(JSONObject.toJSONString(a));

		Biz biz = new BizInstace();
		biz.save();
		biz.delete();
		Integer f=1;
		System.out.println(f);
		init(f);
		System.out.println(f);
		
	}

	public static void init(A a) {
		a.setName("b");
	}
	public static void init(Integer c) {
		c=2;
	}

}

class BizInstace extends MySuperInstace implements Biz {
	@Override
	public void delete() {
		System.out.println("delete");
	}
}

interface Biz extends MySuper {
	void delete();
}

interface MySuper {
	void save();
}

abstract class MySuperInstace implements MySuper {
	@Override
	public void save() {
		System.out.println("save");
	}
}

@Data
class A {
	private String name;
}

@Data
@EqualsAndHashCode(callSuper = false)
class B extends A {
	private String ext;

	public void init(String ext) {
		ext = "20";
	}
}