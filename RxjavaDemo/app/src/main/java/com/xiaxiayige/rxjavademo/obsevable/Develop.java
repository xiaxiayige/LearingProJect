package com.xiaxiayige.rxjavademo.obsevable;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者 开发人员
 */
public class Develop implements Observer {

    private String developName;

    public Develop(String developName) {
        this.developName = developName;
    }

    @Override
    public void update(Observable o, Object arg) {
        handMessage(arg);
    }


    public void handMessage(Object arg) {
        System.out.println(developName + " 收到了一个 " + arg);
    }
}
