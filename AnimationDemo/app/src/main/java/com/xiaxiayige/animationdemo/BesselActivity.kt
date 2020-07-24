package com.xiaxiayige.animationdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bessel.*

/***
 * 贝塞尔曲线 波浪效果
 */
class BesselActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bessel)
        //#ffc3a0
        val colors = arrayOf(
            Color.parseColor("#fdc830"),
            Color.parseColor("#ff8235"),
            Color.parseColor("#ffc3a0")
        )
        var position = 0
        btnNext.setOnClickListener {
            if (position >= colors.size) {
                position = 0
            }
            bgView.changeBg(colors[position])
            position++
        }
    }
}