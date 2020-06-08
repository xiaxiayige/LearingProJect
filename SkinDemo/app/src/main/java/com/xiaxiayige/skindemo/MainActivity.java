package com.xiaxiayige.skindemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MainActivity extends AppCompatActivity {
    MyLayoutInflater myLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new MyFactory2());
//        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);

        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "app.skin");
                SkinManager.getInstance().loadSkinResource(MainActivity.this, file.getAbsolutePath());
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View decorView = getWindow().getDecorView();
                FrameLayout contentView = decorView.findViewById(android.R.id.content);
                SkinManager.getInstance().changeSkin1(contentView);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View decorView = getWindow().getDecorView();
                FrameLayout contentView = decorView.findViewById(android.R.id.content);
                SkinManager.getInstance().changeSkin2(contentView);
            }
        });

//        try {
//            Class<?> aClass = getClassLoader().loadClass("com.android.commands.pm.Pm");
//
//            PackageInstaller packageInstaller = getPackageManager().getPackageInstaller();
//            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL);
//
//            int sessionId = packageInstaller.createSession(sessionParams);
//
//            PackageInstaller.Session session = packageInstaller.openSession(sessionId);
//
//
//            File file1 = new File(Environment.getExternalStorageDirectory(), "hello.apk");
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,200, new Intent(this,MainActivity.class),FLAG_UPDATE_CURRENT);
//            IntentSender intentSender = pendingIntent.getIntentSender();
//
//
//
//            session.commit(intentSender);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private static final String TAG = "MainActivity";

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        myLayoutInflater = new MyLayoutInflater(this, name, attrs);
        return myLayoutInflater.applySkinView();
    }
}
