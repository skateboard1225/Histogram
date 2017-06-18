package com.skateboard.histogram.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import com.skateboard.histogram.R

/**
 * Created by skateboard on 2017/6/16.
 */
class Histogram(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {


    private var datas: FloatArray = floatArrayOf(0f)

    private var yMount: Int = 0

    private var yStep: Int = 0

    private var xMount: Int = 0

    private var xStep: Int = 0

    private var itemBg: Drawable? = null

    private var dividerColor: Int = Color.BLACK

    private var paint: Paint = Paint(dividerColor)

    private var textPaint: Paint = Paint(Color.BLACK)

    private var itemPaint: Paint = Paint(Color.BLACK)

    private var textMarginLine: Float = 20f

    private var lineWidth: Float = 4f

    private var dividerHeight:Float=0f

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    init {

        initAttr(context, attrs)
        initTextPaint()
        initItemPaint()
    }


    private fun initAttr(context: Context?, attrs: AttributeSet?) {
        var typedArray = context?.obtainStyledAttributes(attrs, R.styleable.Histogram)

        yMount = typedArray?.getInt(R.styleable.Histogram_y_amount, 0) ?: 100

        yStep = typedArray?.getInt(R.styleable.Histogram_y_step, 0) ?: 0

        xMount = typedArray?.getInt(R.styleable.Histogram_x_amount, 0) ?: 0

        xStep = typedArray?.getInt(R.styleable.Histogram_x_step, 0) ?: 0

        itemBg = typedArray?.getDrawable(R.styleable.Histogram_item_background)

        dividerColor = typedArray?.getColor(R.styleable.Histogram_divider_color, Color.BLACK) ?: Color.BLACK
    }

    private fun initTextPaint() {
        textPaint = Paint(Color.BLACK)
        textPaint.strokeWidth = 60.0f
        textPaint.isAntiAlias = true
        textPaint.textSize = 60.0f
    }

    private fun initItemPaint() {
        itemPaint.isAntiAlias = true
        itemPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        var childCount = childCount
        for (i in 0..childCount - 1) {
            var child = getChildAt(i)
            child.layout(l, t, l + child.measuredWidth, t + child.measuredHeight)
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        drawAxisY(canvas)
        drawItems(canvas)
    }

    fun setDatas(datas: FloatArray)
    {
        this@Histogram.datas = datas
        invalidate()
    }

    private fun drawAxisY(canvas: Canvas?) {
        var yDividerNum = Math.ceil(yMount / yStep.toDouble()).toInt()
        dividerHeight = ((height - yDividerNum * paint.strokeWidth - paddingTop - paddingBottom) / yDividerNum)
        var mostTextWidth = getTextWidth("" + yMount * yStep)
        var bottom = (height - paddingBottom - getTextWidth("" + xMount) - textMarginLine).toFloat()
        var left = (paddingLeft + mostTextWidth + textMarginLine).toFloat()
        paint.strokeWidth = lineWidth

        //draw vertical
        canvas?.drawLine(left, paddingTop.toFloat(), left, bottom, paint)

        //draw rows
        for (i in 0..yDividerNum - 1) {
            canvas?.drawText("" + i * yStep, paddingLeft.toFloat(), bottom, textPaint)
            canvas?.drawLine(left, bottom, (width - paddingRight).toFloat(), bottom, paint)
            bottom -= dividerHeight
        }


    }

    private fun getTextWidth(text: String): Int {
        var rect = Rect()
        textPaint.getTextBounds(text, 0, text.length, rect)
        return rect.width()
    }

    private fun drawItems(canvas: Canvas?) {
        // draw histogramitems
        var mostTextWidth = getTextWidth("" + yMount * yStep)
        var xDividerNum = Math.ceil(xMount / xStep.toDouble()).toInt()
        var minDis = ((width - paddingLeft - paddingRight - getTextWidth("" + yMount) - textMarginLine - lineWidth) / (2 * xDividerNum + 1))
//        var minDis=divideWidth/2
        var bottom = height - paddingBottom - getTextWidth("" + xMount) - textMarginLine
        var left = paddingLeft + mostTextWidth + textMarginLine
        textPaint.textAlign = Paint.Align.CENTER
        for (i in 0..xDividerNum - 1) {
            var item=0f
            if(i<=datas.size-1) {
                item = datas[i]
            }
            canvas?.drawText("" + (i * xStep + xStep), left + minDis, (height - paddingBottom).toFloat(), textPaint)
            canvas?.drawRect(left + minDis, bottom-dividerHeight*item, Math.min(left + 2 * minDis, (width - paddingRight).toFloat()), bottom, itemPaint)
            left = left + 2 * minDis
        }
    }
}