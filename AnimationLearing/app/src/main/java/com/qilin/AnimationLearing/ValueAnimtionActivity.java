package com.qilin.AnimationLearing;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/***
 * ValueAnimaton
 *
 */
public class ValueAnimtionActivity extends AppCompatActivity {
    private TextView textView;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animtion);
        textView = (TextView) findViewById(R.id.textView);
        valueAnimator = ValueAnimator.ofFloat(0, 100);

//        valueAnimator=ValueAnimator.ofObject(new MyTypeEvaluator(),0f,500f); //有问题。。。

        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                 textView.setTranslationX((int)value);
                textView.setText(String.format("%s", (int) value));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });

    }

    public class MyTypeEvaluator implements TypeEvaluator<Float> {


        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {
            return Float.valueOf(fraction + startValue + (startValue - endValue));
        }
    }
}
