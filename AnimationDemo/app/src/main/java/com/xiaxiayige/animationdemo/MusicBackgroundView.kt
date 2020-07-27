package com.xiaxiayige.animationdemo

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.provider.CalendarContract
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target


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


    fun changeBg(bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            var targetColor:Int
            var isWhite=false;
            var swatchForTarget = it?.getSwatchForTarget(Target.VIBRANT)
            if(swatchForTarget==null){
                swatchForTarget=it?.dominantSwatch
            }

            if(!isWhiteOrBlack(swatchForTarget?.hsl!!)){
                targetColor=swatchForTarget.rgb
                bgColor=targetColor
                postInvalidate()
            }
        }


//        val oldColor = bgColor
//        val valueAnimator = ValueAnimator.ofInt(oldColor, newColor)
//        with(valueAnimator) {
//            addUpdateListener {
//                bgColor = it.animatedValue as Int
//                postInvalidate()
//            }
//            setEvaluator(ArgbEvaluator())
//            duration = 800
//            start()
//        }

    }

    private fun isWhiteOrBlack(hsl: FloatArray): Boolean {
        val hslToColor = ColorUtils.HSLToColor(hsl)
        return hslToColor==Color.WHITE || hslToColor == Color.BLACK
    }


}