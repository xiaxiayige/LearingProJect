package com.xiaxiayige.rxjavademo.obsevable;

import java.util.Observable;

/***
 * 被观察者 bug
 */
public class Bug extends Observable {

    public void postBug() {
        setChanged(); //发生改变
        notifyObservers("Bug"); //通知观察者
    }
}
