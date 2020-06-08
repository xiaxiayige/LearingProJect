package com.xiaxiayige.skindemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class WrapTextView extends TextView {
    public WrapTextView(Context context) {
        super(context);
    }

    public WrapTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    public void changeSkin(Resources resources, String packageName) {
//
//        int textColor = resources.getIdentifier("textColor", "color", packageName);
//        int bgColor = resources.getIdentifier("bgColor", "color", packageName);
//
//        ColorStateList colorStateList = resources.getColorStateList(textColor);
//        setTextColor(colorStateList);
//        setBackgroundColor(resources.getColor(bgColor));
//
//    }

}
