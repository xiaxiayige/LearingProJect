package com.xiaxiayige.skindemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SkinManager {
    private static final SkinManager ourInstance = new SkinManager();

    static SkinManager getInstance() {
        return ourInstance;
    }

    private SkinManager() {
    }

    private Context context;
    private Resources resources;
    private Resources skinSource;
    private String packageName;

    public void loadSkinResource(Context context, String skinPath) {
        resources = context.getResources();
        this.context = context;
        loadSkin(skinPath);
    }

    private void loadSkin(String skinPath) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();

            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, skinPath);
            skinSource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
            //获取到包名
            packageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void changeSkin2(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                changeSkin2((ViewGroup) childAt);
            } else if (childAt instanceof SkinWidget) {
                ((SkinWidget) childAt).changeSkin(skinSource, packageName);
            }
        }

    }

    public void changeSkin1(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                changeSkin1((ViewGroup) childAt);
            } else if (childAt instanceof SkinWidget) {
                ((SkinWidget) childAt).changeSkin(resources, "");
            }
        }

    }
}
