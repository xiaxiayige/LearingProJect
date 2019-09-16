package com.xiaxiayige.andfixdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Replace {
    //需要替换的类路径名称
    String className();
    //需要替换的正确的方法名
    String methodName();


}
