package com.qilin.AnimationLearing;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/23.
 */

public class FrameAnimationDemo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fame_layout);
        ImageView imageView= (ImageView) findViewById(R.id.bg);
        AnimationDrawable mailAnimation = (AnimationDrawable) imageView.getBackground();
        mailAnimation.start();

        ImageView img2= (ImageView) findViewById(R.id.img_2);
        img2.setBackgroundResource(R.drawable.anim_fame_2);

        AnimationDrawable animationDrawable= (AnimationDrawable) img2.getBackground();
        animationDrawable.start();

    }
}
