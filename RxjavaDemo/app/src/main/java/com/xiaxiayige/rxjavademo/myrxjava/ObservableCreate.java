package com.xiaxiayige.rxjavademo.myrxjava;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/***
 * 实际的Oberveable方法
 * @param <T>
 */
public class ObservableCreate<T> extends Observable<T> {

    ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> emitter) {
        this.source = emitter;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> createEmitter = new CreateEmitter<T>(observer);
        try {
            source.subscribe(createEmitter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class CreateEmitter<T> implements ObservableEmitter<T> {
        private Observer<? super T> observer;
        public CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }
        @Override
        public void accapt(T t) {
            observer.onNext(t);
        }
    }
}
