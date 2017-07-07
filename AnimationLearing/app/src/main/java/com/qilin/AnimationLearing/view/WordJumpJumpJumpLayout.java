package com.qilin.AnimationLearing.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qilin.AnimationLearing.R;

import java.util.List;

/**
 * 单词跳跳跳
 * Created by Administrator on 2017/7/7.
 */

public class WordJumpJumpJumpLayout extends LinearLayout {
    private static final String TAG = "WordJumpAnimationLayout";
    private int wordSize;
    private int maxMargin = 50;
    private int minMargin = 10;
    private int animationTime = 500;

    public WordJumpJumpJumpLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public WordJumpJumpJumpLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WordJumpJumpJumpLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setWord(List<String> words) {
        wordSize = words.size();
        addWord(words);
    }

    int i = 0;

    private void addWord(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            TextView v = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_word, null);
            v.setText(words.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(maxMargin, 0, maxMargin, 0);
            v.setLayoutParams(params);
            v.setVisibility(View.INVISIBLE);
            addView(v, i);
        }
        startJumpJumpJump();
    }

    private void startJumpJumpJump() {
        if (i >= getChildCount()) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(maxMargin, minMargin);
                    valueAnimator.setDuration(200);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            if (wordSize == 2) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view1 = getChildAt(0);
                                params.setMargins(maxMargin, 0, value, 0);
                                view1.setLayoutParams(params);
                                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view2 = getChildAt(1);
                                params2.setMargins(value, 0, maxMargin, 0);
                                view2.setLayoutParams(params2);
                            } else if (wordSize == 3) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view1 = getChildAt(0);
                                params.setMargins(maxMargin, 0, value, 0);
                                view1.setLayoutParams(params);
                                //左右2边减少 中间保持不动
                                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view2 = getChildAt(1);
                                params2.setMargins(minMargin, 0, minMargin, 0);
                                view2.setLayoutParams(params2);

                                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view3 = getChildAt(2);
                                params3.setMargins(value, 0, maxMargin, 0);
                                view3.setLayoutParams(params3);
                            } else if (wordSize > 3) {  //依次往中间靠
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                View view1 = getChildAt(0);
                                params.setMargins(maxMargin, 0, value, 0);
                                view1.setLayoutParams(params);
                                for (int m = 1; m < wordSize; m++) {
                                    params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                    View view = getChildAt(m);
                                    params.setMargins(value, 0, value, 0);
                                    view.setLayoutParams(params);
                                }
                            }
                        }
                    });

                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //整体放大效果
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1f, 1.3f, 1f,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            setAnimation(scaleAnimation);
                            scaleAnimation.setDuration(800);
                            scaleAnimation.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    valueAnimator.start();

                }
            }, 20);
            return;
        }
        final View view = getChildAt(i);
        view.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        animationSet.setDuration(animationTime);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1.1f, 0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        view.setAnimation(animationSet);
        animationSet.start();
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart called : EMPTY_STATE_SET");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                i++;
                startJumpJumpJump();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
