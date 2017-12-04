package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

/**
 * Created by linyuzai on 2017/11/13.
 * @author linyuzai
 */
class HorizontalLoadingView(context: Context) : View(context) {
    private val mPaint: Paint = Paint()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var progress: Int = 0

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        mPaint.strokeWidth = mHeight.toFloat()
        canvas?.drawLine(mHeight / 2f, mHeight / 2f, (mWidth - mHeight / 2f) * progress / 100f, mHeight / 2f, mPaint)
        mPaint.strokeWidth = mHeight / 3f
        canvas?.drawLine((mWidth - mHeight / 2f) * progress / 100f, mHeight / 2f, mWidth.toFloat() - mHeight / 2f, mHeight / 2f, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    fun update(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setColor(color: Int): HorizontalLoadingView = apply {
        mPaint.color = color
    }
}