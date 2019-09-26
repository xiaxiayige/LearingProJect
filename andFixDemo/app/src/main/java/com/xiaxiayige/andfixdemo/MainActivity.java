package com.xiaxiayige.andfixdemo;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

        // Example of a call to a native method
        final TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());


        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculation calculation = new Calculation();
                int calculation1 = calculation.calculation();
                tv.setText("--->" + calculation1);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fix();

            }
        });

    }

    private void fix() {
        //1.加载dex文件
        File dexFilePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "fix.dex");
        try {
            DexFile dexFile = DexFile.loadDex(dexFilePath.getAbsolutePath(), new File(getCacheDir(), "opt").getAbsolutePath(), Context.MODE_PRIVATE);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String element = entries.nextElement();
                Class aClass = dexFile.loadClass(element, getClassLoader());
                Method[] declaredMethods = aClass.getMethods();
                for (Method dest : declaredMethods) {
                    Replace annotation = dest.getAnnotation(Replace.class);
                    if (annotation == null) {
                        continue;
                    }
                    //获取要替换方法的类
                    String className = annotation.className();
                    //获取要替换类方法的方法
                    String methodName = annotation.methodName();
                    try {
                        //原始类
                        Class errorClass = Class.forName(className);
//                        Class errorClass =  dexFile.loadClass(className,getClassLoader()); 错误方式
                        Class<?>[] parameterTypes = dest.getParameterTypes();
                        System.out.println("parameterTypes = " + parameterTypes);
                        Method srcMethod = errorClass.getDeclaredMethod(methodName, parameterTypes);
                        System.out.println("errorMethod = " + srcMethod);
                        replaceMethod(srcMethod, dest);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public native String stringFromJNI();

    public native void replaceMethod(Method srcMethod, Method destMethod);
}
