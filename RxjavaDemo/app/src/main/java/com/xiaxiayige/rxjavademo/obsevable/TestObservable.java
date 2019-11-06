package com.xiaxiayige.rxjavademo.obsevable;

public class TestObservable {

    public static void main(String[] args) {
        Bug bug = new Bug();
        Develop develop1 = new Develop("小张");
        Develop develop2 = new Develop("小李");
        Develop develop3 = new Develop("小王");
        Develop develop4 = new Develop("小红");
        //让被观察着和观察者建立关系
        bug.addObserver(develop1);
        bug.addObserver(develop2);
        bug.addObserver(develop3);
        bug.addObserver(develop4);
        //写一个bug
        bug.postBug();
    }
}
