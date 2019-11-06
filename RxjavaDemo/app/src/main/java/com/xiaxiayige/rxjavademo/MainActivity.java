package com.xiaxiayige.rxjavademo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.xiaxiayige.rxjavademo.obsevable.Bug;
import com.xiaxiayige.rxjavademo.obsevable.Develop;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void aa1(View view) {
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


    public void aa2222(View view) {
         Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("11111");
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(String s) {
                //接收bug
                Log.i(TAG, "onNext: 开发人员接收到了 -- " + s);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {

            }
        });
    }

    public void aa2(View view) {
        //bug
        Observable<String> bug = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("11111");
            }
        });
        //开发
        Observer<String> develop = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                //接收bug
                Log.i(TAG, "onNext: 开发人员接收到了 -- " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //订阅
        bug.subscribe(develop);
    }


    public void aa3(View view) {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("发送数据");
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext: " + o);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void aa4(View view) {
        Observable.just("data1", 2, "data3", "data4", 5.0f, true)
                .subscribe(new Observer<Serializable>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Serializable serializable) {
                        if (serializable instanceof String) {
                            Log.i(TAG, "onNext: serializable ====> " + serializable);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void aa5(View view) {
        String[] s = {"1", "2", "3"};
        Observable.fromArray("111", "2222", "2222222")
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void aa6(View view) {

        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                //这里不会回调
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });

    }

    public void aa7(View view) {

        Observable.range(5, 15).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    //变换型
    public void aa8(View view) {

        Observable.just("张三")
                .throttleFirst(500, TimeUnit.SECONDS)
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String name) throws Exception {
                        return name.equals("张三");
                    }
                }).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean b) {
                Log.i(TAG, "onNext: " + b);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    public void aa9(View view) {
        Observable.just("张三")
                .flatMap(new Function<String, Observable<String>>() {
                    @Override
                    public Observable<String> apply(String name) throws Exception {
                        return Observable.just("name = " + name);
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String str) {
                Log.i(TAG, "onNext: " + str);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void aa10(View view) {
        Observable.just("www.baidu.com", "www.sina.com", "www.163.com")
                .concatMap(new Function<String, Observable<String>>() {
                    @Override
                    public Observable<String> apply(String hostName) throws Exception {
                        return getIPAddress(hostName);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String str) {
                        Log.i(TAG, " " + str + " , currentThread =  " + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    private Observable<String> getIPAddress(final String hostName) {


        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                InetAddress ip = null;
                try {
                    ip = InetAddress.getByName(hostName);
                    String hostAddress = ip.getHostAddress();
                    emitter.onNext(hostName + " => " + hostAddress);
                    emitter.onComplete();
                } catch (UnknownHostException e) {
//                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }


    public void aa11(View view) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer > 3 ? "小学" : "幼儿园";
                    }
                }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {

                System.out.println("stringIntegerGroupedObservable = [" + stringIntegerGroupedObservable.getKey() + "]");
                stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("stringIntegerGroupedObservable.getKey() [" + integer + "]");
                    }
                });
            }
        });
    }


    public void aa12(View view) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(4)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.i(TAG, "onNext: " + integers);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //过滤型操作符
    //filter  过滤
    //take //取数
    //distint  去重
    //elementAt //
    //TakeUntil  添加停止条件

    public void aa13(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        Observable.fromIterable(users)
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return user.age > 4 && user.name.equals("888");
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        System.out.println("user = [" + user + "]");
                    }
                });
    }


    public void aa14(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 9);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        Observable.fromIterable(users)
//                .takeUntil(new Predicate<User>() {
//                    @Override
//                    public boolean test(User user) throws Exception {
//                        return user.age == 9;
//                    }
//                })
                .take(3)
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        System.out.println("user = [" + user + "]");
                    }
                });
    }


    public void aa15(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);
        final User user7 = new User("666", 6);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user6); //重复添加
        users.add(user5); //重复添加
        users.add(user6); //重复添加
        users.add(user4); //重复添加
        users.add(user2); //重复添加
        users.add(user7);
        users.add(user7);

        Observable.fromIterable(users)
                .distinct()
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        System.out.println("user = [" + user + "]");
                    }
                });
    }


    public void aa16(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        Observable.fromIterable(users)
//                .elementAt(0)
                .elementAt(10, new User("000", 0))
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        Log.i(TAG, "onSuccess: " + user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    //条件型操作符
    // all
    // any
    //contains
    //isEmpty


    public void aa17(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);
        User user7 = new User("000", 7);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);

        Observable.fromIterable(users)
                .all(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return "000".equals(user.name);
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println("aBoolean = [" + aBoolean + "]");
            }
        });

    }


    public void aa18(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);
        User user7 = new User("000", 7);
        User user8 = new User("aaaaa", 7);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        Observable.fromIterable(users)
                .contains(user8).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.i(TAG, "accept: " + aBoolean);
            }
        });

    }


    public void aa19(View view) {
        User user1 = new User("111", 1);
        User user2 = new User("333", 3);
        User user3 = new User("999", 9);
        User user4 = new User("222", 2);
        User user5 = new User("888", 8);
        User user6 = new User("777", 7);
        User user7 = null;

        Observable.just(11)
                .isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.i(TAG, "accept: " + aBoolean);
            }
        });

    }

    //合并操作符
    public void aa20(View view) {
        Observable.just(4, 5)
                .startWith(3)
                .startWith(2)
                .startWith(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: " + integer);
                    }
                });
    }


    public void aa21(View view) {
        Observable.just(4, 5)
                .concatWith(new ObservableSource<Integer>() {
                    @Override
                    public void subscribe(Observer<? super Integer> observer) {
                        observer.onNext(1);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: " + integer);
            }
        });
    }

    public void aa22(View view) {

        Observable<Long> interval1 = Observable.interval(0, 1, TimeUnit.SECONDS);
        Observable<Long> interval2 = Observable.interval(0, 1, TimeUnit.SECONDS);
        Observable.merge(interval1, interval2).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.i(TAG, "accept: " + aLong);
            }
        });
    }


    public void aa23(View view) {
        Observable.zip(getIPAddress("www.baidu.com"),
                getIPAddress("www.1nihaodasdas.com"),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return "s1  " + s + ", s2  = " + s2;
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "accept: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });
    }

    //异常操作符
    //onErrorResumeNext 拦截发射的异常  ,能够处理 throw Exception级别的异常，能够接收异常消息，需要返回一个新的Observable，执行发送 ，不能处理 throw Error级别异常
    //onErrorReturn     拦截发射的异常，能够处理 throw Execption级别异常 ，能够接收异常消息，可以提供返回一个默认值，但是不能处理 throw Error级别异常
    //onExceptionResumeNext     拦截发射的异常,能够处理 throw Execption级别异常 不接受异常信息，获得Observer继续发送，同样不能处理 Error级别的异常
    public void aa24(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
//                emitter.onError(new RuntimeException("hahahhaa"));
                throw new RuntimeException("hahahhaa"); // 下游onErrorResumeNext 能够处理
//                throw new IllegalAccessError("hahahhaa"); //下游onErrorResumeNext 不能处理

            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext("4");
                                emitter.onNext("5");
                                emitter.onNext("6");
                            }
                        });
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    //拦截发射的异常
    public void aa25(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onError(new RuntimeException("hahahhaa"));
                throw new RuntimeException("hahahhaa"); //  下游onErrorReturn 能够处理
//                throw new IllegalAccessError("hahahhaa"); //下游onErrorResumeNext 不能处理
            }
        })
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        return "default";
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }


    //拦截发射的异常
    @SuppressLint("CheckResult")
    public void aa26(View view) {
        //127

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
//                emitter.onError(new RuntimeException("hahahhaa"));
                throw new RuntimeException("hahahhaa"); //  下游onErrorReturn 能够处理
                //throw new IllegalAccessError("hahahhaa"); //下游onErrorResumeNext 不能处理
            }
        })
                .onExceptionResumeNext(new ObservableSource<String>() {
                    @Override
                    public void subscribe(Observer<? super String> observer) {
                        observer.onNext("4");
                        observer.onNext("5");
                        observer.onNext("6");
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });


    }

    ConnectableObservable<Integer> publish;

    public void aa27(View view) {
        publish = Observable.just(1).publish();
        publish.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: " + integer);
            }
        });


    }

    public void aa28(View view) {
        publish.connect();
    }


    //背压模式&flowable
    //背压三种模式 （缓存池128）
    //1.BackpressureStrategy.ERROR   //上游发送大量数据，下游处理不过来，放入缓存池 ，如果池子满了，就会抛出异常
    //2.BackpressureStrategy.BUFFER  //游发送大量数据，下游处理不过来 ，等待下游接收事件处理
    //3.BackpressureStrategy.DROP（不常用） //上游发送大量数据，下游处理不过来 放入缓存池 ，如果池子满了，就会把后面的数据丢弃
    //4.BackpressureStrategy.LATEST（不常用） //上游发送大量数据，下游处理不过来 ,只存储128条数据,最后还会发送最后一条数据
    public void aa29(View view) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 200; i++) {
                    emitter.onNext("===> " + i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(200);
            }

            @Override
            public void onNext(String s) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError: " + t);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });


    }

    public void aa30(View view) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 200; i++) {
                    emitter.onNext("===> " + i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(200);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });


    }

    public void aa31(View view) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 200; i++) {
                    emitter.onNext("===> " + i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(200);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });


    }


    public void aa32(View view) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                for (int i = 0; i < 150; i++) {
                    emitter.onNext("===> " + i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    public void aa33(View view) {
        Observable.just(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: "+integer);
            }
        });

    }


}
