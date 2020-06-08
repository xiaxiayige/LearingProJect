package com.xiaxiayige.skindemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatViewInflater;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiaxiayige.skindemo.widget.SkinButtonView;
import com.xiaxiayige.skindemo.widget.SkinTextView;

public class MyLayoutInflater extends AppCompatViewInflater {
    private String name;
    private AttributeSet attrs;
    private Context context;

    public MyLayoutInflater(Context context, String name, AttributeSet attrs) {
        this.name = name;
        this.attrs = attrs;
        this.context=context;
    }

    public View applySkinView() {
        View view = null;
        if (name.equals("TextView")) {
            view = new Button(context, attrs);
        }else if (name.equals("Button")){
            view = new SkinButtonView(context, attrs);
        }
        return view;
    }





}
