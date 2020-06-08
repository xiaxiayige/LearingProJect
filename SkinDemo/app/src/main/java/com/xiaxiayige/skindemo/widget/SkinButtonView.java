package com.xiaxiayige.skindemo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.xiaxiayige.skindemo.SkinWidget;

public class SkinButtonView extends AppCompatButton implements SkinWidget {
    private boolean skinTag = false;

    public SkinButtonView(Context context) {
        super(context);
    }

    public SkinButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        skinTag = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "skinTag", false);
    }

    public SkinButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        skinTag = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "skinTag", false);
    }


    @Override
    public void changeSkin(Resources resources, String packageName) {
        if ("".equals(packageName) || skinTag==false) {
            packageName = getContext().getPackageName();
            resources = getResources();
        }

        int color = resources.getIdentifier("text_color", "color", packageName);
        setTextColor(resources.getColorStateList(color));
    }
}
