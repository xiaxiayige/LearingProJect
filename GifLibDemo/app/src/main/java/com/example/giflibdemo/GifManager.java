package com.example.giflibdemo;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;

public class GifManager {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //存储gif的内存地址
    long gifAddress;

    public GifManager() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "demo6.gif");
        gifAddress = loadGifFile(file.getAbsolutePath());
    }


    public native int getWidth(long gifAddress);

    public native int getHeight(long gifAddress);

    public native long loadGifFile(String gifFile);

    public native int updateFrame(long gifAddress,Bitmap bitmap);
}
