package com.xiaxiayige.rxjavademo.myrxjava;

/***
 * 观察者
 * @param <T>
 */
public interface Observer<T> {

    void onNext(T t);
}
