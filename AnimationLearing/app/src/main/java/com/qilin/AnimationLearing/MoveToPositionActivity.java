package com.qilin.AnimationLearing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/9.
 */

public class MoveToPositionActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private static final int RECORD_AUDIO = 155;
    private static final int WRITE_EXTERNAL_STORAGE = 222;
    private TextView text;
    TextView t1, t2;
    RelativeLayout rlayout_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movetoposition);
        t1 = (TextView) findViewById(R.id.txtv_1);
        t2 = (TextView) findViewById(R.id.txtv_2);
        rlayout_root = (RelativeLayout) findViewById(R.id.rlayout_root);


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView textView = new TextView(MoveToPositionActivity.this);
                textView.setText(t1.getText());
                textView.setTextColor(Color.YELLOW);
//                textView.setBackgroundColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);

                int[] stc = new int[2];
                t1.getLocationInWindow(stc);

                int[] stc2 = new int[2];
                t2.getLocationInWindow(stc2);

                Log.i("MainActivity", "onClick: stc =" + stc[0] + " , " + stc[1] + " , Width = " + t1.getWidth() + " , height = " + t1.getHeight());
                Log.i("MainActivity", "onClick: stc =" + stc2[0] + " , " + stc2[1] + " , Width = " + t2.getWidth() + " , height = " + t2.getHeight());


                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(t1.getWidth(), t1.getHeight());
                textView.setPadding(t1.getPaddingLeft(), t1.getPaddingTop(), t1.getPaddingRight(), t1.getPaddingBottom());
                layoutParms.setMargins(stc[0], stc[1]-getStatusBarHeight(), 0, 0);

                textView.setLayoutParams(layoutParms);

                rlayout_root.addView(textView);


                int formX = 0;
                int toX;
                int formY = 0;
                int toY;

                if(stc[0]>stc2[0]){ //x轴往左边移动
                    toX=(stc[0]-stc2[0])*-1;
                }else{ //x轴往右边移动
                    toX=stc2[0]-stc[0];
                }

                if(stc[1]>stc2[0]){ //Y轴往上移动
                    toY=(stc[1]-stc2[1])*-1;
                }else{ //Y轴往下移动
                    toY=stc2[1]-stc[1];
                }
                TranslateAnimation tran = new TranslateAnimation(formX, toX, formY, toY);
                tran.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
//                        t2.setText(textView.getText());
//                        textView.setVisibility(View.GONE);
                        rlayout_root.removeView(textView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tran.setDuration(500);
                textView.setAnimation(tran);
                tran.start();

            }
        });

//
    }

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }
}