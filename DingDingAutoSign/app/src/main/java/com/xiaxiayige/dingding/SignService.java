package com.xiaxiayige.dingding;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/****
 * 已完成 从从消息页面跳转到工作页面，从工作页面点击考勤打卡 进入到打开页面
 *
 * 未完成 在打卡页面 点击立即打卡
 */
public class SignService extends AccessibilityService {

    private static final String PACKAGE_NAME = "com.alibaba.android.rimet";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName().equals(PACKAGE_NAME)) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("android.widget.FrameLayout")) {
                nodeInfos.clear();
                final AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                if (nodeInfo == null) return;
                //跳转到工作页面
                targetWorkFragment(nodeInfo);
                //等待5秒数据加载完成 再执行其他操作
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findSignNodeInfo(nodeInfos, nodeInfo);
                        for (AccessibilityNodeInfo info : nodeInfos) {
                            if ("考勤打卡".equals(info.getContentDescription())) {
                                AccessibilityNodeInfo parent = info.getParent();
                                if (parent != null) {
                                    performClick(parent);
                                }
                                break;
                            }
                        }
                    }
                }, 5000);
            }

        } else {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (event.getClassName().equals("com.alibaba.android.user.login.SignUpWithPwdActivity--------")) {//登录页面
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    if (nodeInfo == null) {
                        return;
                    }
                    gotoRegisterPage(nodeInfo);
                } else if (event.getPackageName().equals("com.alibaba.android.rimet.biz.LaunchHomeActivity")) {
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    if (nodeInfo == null) {
                        return;
                    }
                    sign(nodeInfo);
                }
            } else if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {

            }
        }


    }

    /***
     * 查找签到按钮
     * @param parentNodeInfo
     */
    private List<AccessibilityNodeInfo> findSignNodeInfo(List<AccessibilityNodeInfo> list, AccessibilityNodeInfo parentNodeInfo) {
        int parentCount = parentNodeInfo.getChildCount();
        for (int i = 0; i < parentCount; i++) {
            AccessibilityNodeInfo child = parentNodeInfo.getChild(i);
            if (child != null) {
                findSignNodeInfo(nodeInfos, child);
                nodeInfos.add(child);
            }
        }


        return nodeInfos;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void targetWorkFragment(AccessibilityNodeInfo nodeInfo) {
        List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = nodeInfo.findAccessibilityNodeInfosByViewId("com.alibaba.android.rimet:id/home_bottom_tab_button_work");
        if (accessibilityNodeInfosByViewId == null || accessibilityNodeInfosByViewId.size() <= 0) {
            return;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfosByViewId.get(0);
        if (accessibilityNodeInfo != null) {
            performClick(accessibilityNodeInfo);
        }

    }

    @Override
    public void onInterrupt() {

    }

    public void gotoRegisterPage(AccessibilityNodeInfo nodeInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> et_phone_input = nodeInfo.findAccessibilityNodeInfosByViewId("com.alibaba.android.rimet:id/login_new_user");
            if (et_phone_input != null || et_phone_input.size() > 0) {
                AccessibilityNodeInfo accessibilityNodeInfo = et_phone_input.get(0);
                performClick(accessibilityNodeInfo);
            } else {
                System.out.println("=====> 没有找到控件");
            }
        }
    }

    /***
     * 签到
     * @param nodeInfo
     */
    public void sign(AccessibilityNodeInfo nodeInfo) {
        AccessibilityNodeInfo workNodeInfo = findNodeInfoById(nodeInfo, "com.alibaba.android.rimet:id/home_bottom_tab_button_work");
        if (workNodeInfo != null) {
            if (workNodeInfo.isFocusable() == true) return;
            performClick(workNodeInfo);
        }
    }

    public AccessibilityNodeInfo findNodeInfoById(AccessibilityNodeInfo nodeInfo, String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> et_phone_input = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (et_phone_input != null || et_phone_input.size() > 0) {
                AccessibilityNodeInfo accessibilityNodeInfo = et_phone_input.get(0);
                return accessibilityNodeInfo;
            } else {
                return null;
            }
        }
        return null;
    }

    private static final String TAG = "SignService";

    public void performClick(final AccessibilityNodeInfo nodeInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Log.i(TAG, "nodeInfo =  " + nodeInfo);
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

}
