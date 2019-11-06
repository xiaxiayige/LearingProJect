package com.xiaxiayige.rxjavademo.myrxjava;

/***
 * 主要通过ObservableEmitter发送消息
 * @param <T>
 */
public interface ObservableOnSubscribe<T> {

    void subscribe(ObservableEmitter<T> emitter) throws Exception;
}
