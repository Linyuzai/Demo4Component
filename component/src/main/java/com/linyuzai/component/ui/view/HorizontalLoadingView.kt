package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
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
    private var oneLine: Boolean = true
    private var max: Int = 100

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas?) {
        mPaint.strokeWidth = mHeight.toFloat()
        if (mPaint.strokeCap == Paint.Cap.ROUND)
            canvas?.drawLine(mHeight / 2f, mHeight / 2f, (mWidth - mHeight / 2f) * progress / max.toFloat(), mHeight / 2f, mPaint)
        else
            canvas?.drawLine(0f, mHeight / 2f, mWidth * progress / max.toFloat(), mHeight / 2f, mPaint)
        if (!oneLine) {
            mPaint.strokeWidth = mHeight / 3f
            canvas?.drawLine((mWidth - mHeight / 2f) * progress / max.toFloat(), mHeight / 2f, mWidth.toFloat() - mHeight / 2f, mHeight / 2f, mPaint)
        }
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

    fun setCapRound(): HorizontalLoadingView = apply {
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    fun setOneLine(oneLine: Boolean): HorizontalLoadingView = apply {
        this.oneLine = oneLine
    }

    fun setMax(max: Int): HorizontalLoadingView = apply {
        this.max = max
    }

    fun autoIncrease(time: Long) {
        val delay = time / max
        postDelayed({
            if (progress == max + 1)
                return@postDelayed
            update(progress)
            progress++
            autoIncrease(time)
        }, delay)
    }
}