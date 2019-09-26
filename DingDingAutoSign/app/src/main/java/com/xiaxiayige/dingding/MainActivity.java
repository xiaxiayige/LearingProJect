package com.xiaxiayige.dingding;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
    }

    private void initService() {
        Intent intent = new Intent(this, SignService.class);

        startService(intent);
    }


    //开启无障碍服务
    private void autoOpenAccessibilityService(Context context) {
        if (!isStartAccessibilityServiceEnable(context)) {
            Settings.Secure.putString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                    context.getPackageName() + "/" + "SignService");
            Settings.Secure.putInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED, 1);
            Log.d("system", "SETTING ACCESSIBILITY SUCCESS!");
        }
    }

    /**
     * 判断无障碍服务是否开启
     *
     * @param context
     * @return
     */
    private boolean isStartAccessibilityServiceEnable(Context context) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager)
                        context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        assert accessibilityManager != null;
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(
                        AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().contains(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
