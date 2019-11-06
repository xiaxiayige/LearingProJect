package com.xiaxiayige.rxjavademo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("dasdasdasd", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("aaa",true).apply();
        Log.i(TAG, "onCreate: "+sharedPreferences.getBoolean("aaa",false));
        setContentView(R.layout.activity_main2);

    }

    public void aaa(View view) {
        startActivity(new Intent(this,Main3Activity.class));
    }
}
