package com.xiaxiayige.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xiaxiayige.rxjavademo.myrxjava.Observable;
import com.xiaxiayige.rxjavademo.myrxjava.ObservableCreate;
import com.xiaxiayige.rxjavademo.myrxjava.ObservableEmitter;
import com.xiaxiayige.rxjavademo.myrxjava.ObservableOnSubscribe;
import com.xiaxiayige.rxjavademo.myrxjava.Observer;

public class Main3Activity extends AppCompatActivity {
    private static final String TAG = "Main3Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }
    public void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.accapt("222222");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }
        });
    }
}
