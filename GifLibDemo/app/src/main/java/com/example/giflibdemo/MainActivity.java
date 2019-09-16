package com.example.giflibdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private ImageView img;
    private GifManager gifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        Button btn = findViewById(R.id.btn);
        img = findViewById(R.id.img);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifManager = new GifManager();

                bitmap = Bitmap.createBitmap(gifManager.getWidth(gifManager.gifAddress), gifManager.getHeight(gifManager.gifAddress), Bitmap.Config.ARGB_8888);
                img.setImageBitmap(bitmap);

                int delyTime = gifManager.updateFrame(gifManager.gifAddress, bitmap);

                handler.sendEmptyMessageDelayed(0, delyTime);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int delyTime = gifManager.updateFrame(gifManager.gifAddress, bitmap);
            handler.sendEmptyMessageDelayed(0, delyTime);
            img.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
