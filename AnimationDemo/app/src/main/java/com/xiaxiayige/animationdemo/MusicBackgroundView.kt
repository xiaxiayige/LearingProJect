package com.xiaxiayige.animationdemo

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.ColorUtils


/***
 * 背景渐变View
 */
class MusicBackgroundView : View {
    var paint: Paint = Paint()
    var bgColor = Color.parseColor("#30e8bf")

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        paint.isAntiAlias = true

    }

    private fun getShader(from: Int, to: Int): Shader {
        return LinearGradient(0f, 0f, 0f, height.toFloat(), from, to, Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.setColor(bgColor)
        paint.shader = getShader(calculateColor(), bgColor)

        val r = Rect(0, 0, width, height)

        canvas?.drawRect(r, paint)
    }

    val hls = FloatArray(3)
    private fun calculateColor(): Int {
        val red = Color.red(bgColor)
        val green = Color.green(bgColor)
        val blue = Color.blue(bgColor)
        ColorUtils.RGBToHSL(red, green, blue, hls)
        hls[2] = hls[2] / 1.3f
        if (hls[2] > 1) {
            hls[2] = 1f
        }
        return ColorUtils.HSLToColor(hls)
    }


    fun changeBg(newColor: Int) {
        val oldColor = bgColor
        val valueAnimator = ValueAnimator.ofInt(oldColor, newColor)
        with(valueAnimator) {
            addUpdateListener {
                bgColor = it.animatedValue as Int
                postInvalidate()
            }
            setEvaluator(ArgbEvaluator())
            duration = 800
            start()
        }

    }


}