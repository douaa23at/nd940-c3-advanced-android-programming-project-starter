package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var initialLeftPositionX = 0f
    private var initialLeftPositionY = 0f
    private var leftPositionX = 0f
    private var leftPositionY = 0f
    private var rightPositionX = 0f
    private var rightPositionY = 0f
    private var state = false
    private var showText = false
    private var loading = false

    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if (old != new) {
           invalidate()
        }
    }
    private val intialText = context.getString(R.string.button_initial_text)
    private val loadingText = context.getString(R.string.button_loading_text)

    val textPaint = Paint().apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        color = getColor(context, R.color.design_default_color_primary)
        typeface = Typeface.create("", Typeface.BOLD)
    }
    val infoPaint = Paint().apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        alpha = 0
        color = getColor(context, R.color.white_transparent)
        typeface = Typeface.create("", Typeface.BOLD)
    }

    val overlayPaint = Paint().apply {
        style = Paint.Style.FILL
        isDither = true
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        color = getColor(context, R.color.colorPrimaryDark)
        typeface = Typeface.create("", Typeface.BOLD)
    }

    val loadingPaint = Paint().apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        alpha = 0
        color = getColor(context, R.color.colorAccent)
        typeface = Typeface.create("", Typeface.BOLD)
    }

    var nextAngle = 0f
    private var rectF = RectF(
        (widthSize * 3 / 4).toFloat(),
        (heightSize / 8).toFloat(),
        (widthSize * 3 / 4).toFloat() + 20f,
        (heightSize / 8).toFloat() + 20f
    )


    init {
        isClickable = true
        setBackgroundColor(getColor(context, R.color.colorPrimary))

    }


    override fun performClick(): Boolean {
        super.performClick()
        contentDescription = resources.getString(R.string.button_name)
        state = false
        loading = true
        startAnimation()
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!loading) {
            canvas.drawText(
                intialText,
                (widthSize / 2).toFloat(),
                (heightSize / 2).toFloat(),
                textPaint
            )
        }

        if (loading) {
            initialLeftPositionX
            initialLeftPositionY = heightSize.toFloat()
            rightPositionX += 10
            rightPositionY += 10 - heightSize
            canvas.drawRect(
                initialLeftPositionX,
                initialLeftPositionY,
                rightPositionX,
                rightPositionY,
                overlayPaint
            )

            canvas.drawText(
                loadingText,
                (widthSize / 2).toFloat(),
                (heightSize / 2).toFloat(),
                infoPaint
            )
            nextAngle += 10f

            canvas.drawArc(
                rectF,
                0f,
                nextAngle,
                true,
                loadingPaint
            )

        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    private fun startAnimation() {
        rectF = RectF(
            (widthSize * 2 / 3).toFloat(),
            (heightSize / 4).toFloat(),
            (widthSize * 4 / 6).toFloat() + 40f,
            (heightSize / 4).toFloat() + 40f
        )
        valueAnimator.setIntValues(0, 1000)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            //if (animatedValue >= 1)
            // showText = true
            invalidate()
        }
        valueAnimator.start()
    }

}