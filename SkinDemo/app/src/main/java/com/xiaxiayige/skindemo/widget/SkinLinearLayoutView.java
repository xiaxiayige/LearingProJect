package com.xiaxiayige.skindemo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.xiaxiayige.skindemo.SkinWidget;

public class SkinLinearLayoutView extends LinearLayout implements SkinWidget {
    public SkinLinearLayoutView(Context context) {
        super(context);
    }

    public SkinLinearLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkinLinearLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void changeSkin(Resources resources,String packageName) {
        int color = resources.getIdentifier("textColor", "color",packageName);
        setBackgroundColor(color);
    }
}
