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
                textView.setBackgroundColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);

                int[] stc = new int[2];
                t1.getLocationInWindow(stc);

                int[] stc2 = new int[2];
                t2.getLocationInWindow(stc2);

                Log.i("MainActivity", "onClick: stc =" + stc[0] + " , " + stc[1] + " , Width = "  +t1.getWidth() + " , height = " + t1.getHeight());
                Log.i("MainActivity", "onClick: stc =" + stc2[0] + " , " + stc2[1] + " , Width = "  +t2.getWidth() + " , height = " + t2.getHeight());





                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParms.setMargins(stc[0], stc[1] - (t1.getHeight() * 2), 0, 0);
                textView.setPadding(t1.getPaddingLeft(), t1.getPaddingTop(), t1.getPaddingRight(), t1.getPaddingBottom());
                textView.setLayoutParams(layoutParms);

                rlayout_root.addView(textView);


                int formX = 0;
                int toX;
                int formY = 0;
                int toY;


                TranslateAnimation tran = new TranslateAnimation(0, -(stc2[0] - (stc2[0] - Math.abs(t2.getWidth()))), 0, 0);
                tran.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
//                        t2.setText(textView.getText());
//                        textView.setVisibility(View.GONE);
//                        rlayout_root.removeView(textView);
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
}
