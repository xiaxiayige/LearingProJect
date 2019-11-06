package com.xiaxiayige.rxjavademo.myrxjava;

/***
 * 消息发送接口
 * @param <T>
 */
public  interface ObservableEmitter<T>  {
    void accapt(T t);
}
