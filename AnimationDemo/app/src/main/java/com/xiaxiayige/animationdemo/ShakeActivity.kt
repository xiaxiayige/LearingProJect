package com.xiaxiayige.animationdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.CycleInterpolator
import kotlinx.android.synthetic.main.activity_shake.*

/***
 * 抖动效果
 * 使用场景：不能继续点击 或者点击无效的提示
 *
 */
class ShakeActivity : AppCompatActivity() {

    var isAnimator = false
    var rootView: ViewGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        rootView = window.decorView.findViewById(android.R.id.content)
        button.setOnClickListener {
            shakeWindow()
        }
    }

    private fun shakeWindow() {
        if (isAnimator) {
            return
        } else {
            rootView?.animate()
                ?.translationX(-3.0f)
                ?.setInterpolator(CycleInterpolator(2.0f))
                ?.setDuration(250)
                ?.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                        isAnimator = false
                    }

                    override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                        isAnimator = true

                    }
                })
                ?.start()
        }


    }
}