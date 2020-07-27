package com.xiaxiayige.animationdemo

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RawRes
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
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
        val drawables = arrayOf(
            R.drawable.ic_teacher_head_default,
            R.drawable.icon_studies_my_flow,
            R.drawable.icon_studies_my_study
        )
        var position = 0
        btnNext.setOnClickListener {
            if (position >= colors.size) {
                position = 0
            }
            val bitmap = changeBitmap(drawables, position)
            bgView.changeBg(bitmap)
            position++
        }
        changeBitmap(drawables, 0)
    }

    private fun changeBitmap(drawables: Array<Int>, position: Int):Bitmap {
        val bitmapDrawable = resources.getDrawable(drawables[position]) as BitmapDrawable
        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmapDrawable.bitmap)
        rounded.isCircular = true
        imageView.setImageDrawable(rounded)
        return  bitmapDrawable.bitmap
    }
}