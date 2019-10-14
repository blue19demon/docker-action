package com.future.jdk8.annos.inherited;

import java.util.Arrays;

@DocumentA(100)
class A {
}

class B extends A {
}

@DocumentB
class C {
}

class D extends C {
}

//测试
public class DocumentDemo {

	public static void main(String... args) {
		B instanceB = new B();
		/**
		 * @Inherited 可以让注解被继承，但这并不是真的继承， 只是通过使用@Inherited，
		 *     可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解
		 */
		System.out.println("已使用的@Inherited注解:" + Arrays.toString(instanceB.getClass().getAnnotations()));
		Class<?> clazz = instanceB.getClass();
		if (clazz.isAnnotationPresent(DocumentA.class)) {
			DocumentA documentA = clazz.getAnnotation(DocumentA.class);
			System.out.println(documentA.value());
		}
		C instanceC = new D();

		System.out.println("没有使用的@Inherited注解:" + Arrays.toString(instanceC.getClass().getAnnotations()));
	}

	/**
	 * 运行结果: 已使用的@Inherited注解:[@com.zejian.annotationdemo.DocumentA()]
	 * 没有使用的@Inherited注解:[]
	 */
}