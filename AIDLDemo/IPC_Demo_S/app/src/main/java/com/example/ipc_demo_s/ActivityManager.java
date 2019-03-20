package com.example.ipc_demo_s;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private volatile static ActivityManager instance=new ActivityManager();

    private List<Activity> activityList;

    private ActivityManager() {
        activityList = new ArrayList<>();
    }

    public static ActivityManager getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public Activity get() {
        return activityList.get(0);
    }
}
