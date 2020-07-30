package com.xiaxiayige.animationdemo

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target


/***
 * 背景渐变View
 */
class MusicBackgroundView : View {
    var paint: Paint = Paint()
    var wavepaint: Paint = Paint()
    var bgColor = Color.parseColor("#30e8bf")
    var mTranPath: Path? = null
    var waveHeight_A = 100f //浪高
    var waveHeight_B = 80f  //浪低
    var mHheight = waveHeight_A+ waveHeight_B //水平高度  距离底部100高度

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        paint.isAntiAlias = true

        mTranPath = Path()
        wavepaint.isAntiAlias = true

//        wavepaint.style = Paint.Style.STROKE
        val s="{\"aaa\":\"ccc\",\"name\":\"value\"}"
    }

    private fun getShader(from: Int, to: Int): Shader {
        return LinearGradient(0f, 0f, 0f, height.toFloat(), from, to, Shader.TileMode.CLAMP)
    }

    var xOffset = 0f
    var X_SPEED = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.setColor(bgColor)
        paint.shader = getShader(calculateColor(), bgColor)
        val r = Rect(0, 0, width, height)
        canvas?.drawRect(r, paint)

        canvas?.save()

        mHheight = height - waveHeight_A

        mTranPath?.moveTo(0f, mHheight)

        val fl = width / 4f // 四分之1的波浪

        canvas?.translate(-xOffset,0f)

        mTranPath?.quadTo(fl, mHheight-waveHeight_A, width / 2f, mHheight)
        mTranPath?.quadTo(width / 2f+ fl, mHheight+waveHeight_B, width.toFloat() , mHheight)

        mTranPath?.quadTo(width +fl, mHheight-waveHeight_A, width.toFloat()+fl*2 , mHheight)

        mTranPath?.quadTo(width +fl*3, mHheight+waveHeight_B, width +fl*4 , mHheight)

        mTranPath?.lineTo(width.toFloat()*2,height.toFloat())
        mTranPath?.lineTo(0f,height.toFloat())
        mTranPath?.close()
//        wavepaint.style=Paint.Style.STROKE
        wavepaint.setColor(Color.WHITE)

        canvas?.drawPath(mTranPath!!, wavepaint)

        canvas?.restore()

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
            var targetColor: Int
            var isWhite = false;
            var swatchForTarget = it?.getSwatchForTarget(Target.VIBRANT)
            if (swatchForTarget == null) {
                swatchForTarget = it?.dominantSwatch
            }

            if (!isWhiteOrBlack(swatchForTarget?.hsl!!)) {
                targetColor = swatchForTarget.rgb
                val valueAnimator = ValueAnimator.ofInt(bgColor, targetColor)
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

        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        with(valueAnimator) {
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                xOffset = animatedValue*width
                if (xOffset > width) {
                    xOffset -= width
                }
                invalidate()
            }
            duration = 2800
            start()
        }

    }

    private val TAG = "MusicBackgroundView"

    private fun isWhiteOrBlack(hsl: FloatArray): Boolean {
        val hslToColor = ColorUtils.HSLToColor(hsl)
        val b = hslToColor == Color.WHITE || hslToColor == Color.BLACK
        Log.i(TAG, "isWhiteOrBlack: $b")
        return b
    }


}