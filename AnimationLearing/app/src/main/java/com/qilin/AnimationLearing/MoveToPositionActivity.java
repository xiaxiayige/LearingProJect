package com.qilin.AnimationLearing;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/9.
 */

public class MoveToPositionActivity extends AppCompatActivity {
    private static final String TAG = "MoveToPositionActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movetopositionactivity);

        final TextView txtv_1 = (TextView) findViewById(R.id.txtv_1);
        final TextView txtv_2 = (TextView) findViewById(R.id.txtv_2);
        txtv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = txtv_1.getLayoutParams();
                ViewGroup.LayoutParams layoutParams2 = txtv_2.getLayoutParams();
                final int[] txtv_1_locations = new int[2];
                txtv_1.getLocationOnScreen(txtv_1_locations);
                Log.i(TAG, "onClick called : x = " + txtv_1_locations[0] + " , y = " + txtv_1_locations[1]);

                final int[] txtv_2_locations = new int[2];
                txtv_2.getLocationOnScreen(txtv_2_locations);

//                ObjectAnimator valueAnimator=ObjectAnimator.ofInt(txtv_2,"translationX",txtv_1_locations[0]);

                ValueAnimator valueX=ValueAnimator.ofInt(txtv_2_locations[0],txtv_1_locations[0]);
                valueX.setDuration(1000);
                valueX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value= (int) animation.getAnimatedValue();
                        txtv_2.setTranslationX(txtv_1_locations[0]-value);
                    }
                });

                ValueAnimator valueY=ValueAnimator.ofInt(txtv_2_locations[1],txtv_1_locations[1]);
                valueX.setDuration(1000);
                valueX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value= (int) animation.getAnimatedValue();
                        txtv_2.setTranslationY(-value);
                    }
                });

                valueX.start();
//                valueY.start();

            }
        });

    }

    class WrapView {
        private View view;
        private int moveToXY;

        public WrapView(View view) {
            this.view = view;
        }

        public int getMoveToXY() {
            return moveToXY;
        }

        public void setMoveToXY(int moveToXY) {
            this.moveToXY = moveToXY;
        }
    }
}
