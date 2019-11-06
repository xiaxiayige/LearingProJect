package com.xiaxiayige.rxjavademo.myrxjava;

/***
 * 被观察者
 * @param <T>
 */
public abstract class Observable<T> {

    /***
     * 创建一个Observable
     * @param emitter
     * @param <T>
     * @return
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<T> emitter) {
        //需要包装一层
        return new ObservableCreate<T>(emitter);
    }

    /***
     * 订阅观察者
     * @param observer
     */
    public  void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    /***
     * 内部实现
     * @param observer
     */
    abstract void subscribeActual(Observer<? super T> observer);
}
