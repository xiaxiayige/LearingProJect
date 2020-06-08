package com.xiaxiayige.skindemo.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.xiaxiayige.skindemo.SkinWidget;

public class SkinTextView extends AppCompatTextView implements SkinWidget {
    public SkinTextView(Context context) {
        super(context);
    }

    private boolean skinTag = false;

    public SkinTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        skinTag = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "skinTag", false);
    }

    @Override
    public void changeSkin(Resources resources, String packageName) {
        if (TextUtils.isEmpty(packageName) || skinTag == false) {
            packageName = getContext().getPackageName();
            resources=getResources();
        }

        int color = resources.getIdentifier("text_color", "color", packageName);
        int bgColor = resources.getIdentifier("bg_color", "color", packageName);
        ColorStateList colorStateList = resources.getColorStateList(color);
        setTextColor(colorStateList);
        setBackgroundColor(resources.getColor(bgColor));
    }
}
