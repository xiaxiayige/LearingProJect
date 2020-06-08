package com.xiaxiayige.skindemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaxiayige.skindemo.widget.SkinTextView;

public class MyFactory2 implements LayoutInflater.Factory2 {

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if(name.equals("TextView")){
            return new SkinTextView(context,attrs);
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return this.onCreateView((View)null, name, context, attrs);
    }
}
