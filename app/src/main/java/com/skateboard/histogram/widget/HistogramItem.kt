package com.skateboard.histogram.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.skateboard.histogram.R

/**
 * Created by skateboard on 2017/6/17.
 */

class HistogramItem(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    private var bgColor: Int = Color.BLACK

    private var paint: Paint = Paint(bgColor)

    private var height: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var width: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    init {
        val typedArray: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.Histogram)
        bgColor = typedArray?.getColor(R.styleable.Histogram_item_background, Color.BLACK) ?: Color.BLACK
        initPaint()
    }

    private fun initPaint() {
        paint.color = bgColor
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}
