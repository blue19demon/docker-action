package com.future.jdk8.annos.inherited;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@DocumentA(value = 2)
class BB{ }

//继承了A类
@DocumentB
public class DocumentDemo02 extends BB{

    public static void main(String... args){

        Class<?> clazz = DocumentDemo02.class;
        //根据指定注解类型获取该注解
        DocumentA documentA=clazz.getAnnotation(DocumentA.class);
        System.out.println("A:"+documentA);

        //获取该元素上的所有注解，包含从父类继承
        Annotation[] an= clazz.getAnnotations();
        System.out.println("an:"+ Arrays.toString(an));
        //获取该元素上的所有注解，但不包含继承！
        Annotation[] an2=clazz.getDeclaredAnnotations();
        System.out.println("an2:"+ Arrays.toString(an2));

        //判断注解DocumentA是否在该元素上
        boolean b=clazz.isAnnotationPresent(DocumentA.class);
        System.out.println("b:"+b);

        /**
         * 执行结果:
         A:@com.zejian.annotationdemo.DocumentA()
         an:[@com.zejian.annotationdemo.DocumentA(), @com.zejian.annotationdemo.DocumentB()]
         an2:@com.zejian.annotationdemo.DocumentB()
         b:true
         */
    }
}
