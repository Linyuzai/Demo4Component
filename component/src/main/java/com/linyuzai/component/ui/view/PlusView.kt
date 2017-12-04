package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import com.linyuzai.kotlinextension.centerCrop
import org.jetbrains.anko.dip

/**
 * Created by linyuzai on 2017/11/6.
 * @author linyuzai
 */
class PlusView(context: Context) : ImageView(context) {
    private var paint: Paint = Paint()
    private var plusWidth = 0
    private var plusHeight = 0
    private var spacing = 0f
    private var drawPlus = true

    init {
        paint.color = Color.parseColor("#d8d8d8")
        paint.isAntiAlias = true
        paint.strokeWidth = context.dip(2).toFloat()
        spacing = context.dip(15).toFloat()
        centerCrop()
    }

    override fun onDraw(canvas: Canvas?) {
        if (drawPlus) {
            canvas?.drawLine(spacing, plusHeight / 2f, plusWidth / 2f - paint.strokeWidth / 2f, plusHeight / 2f, paint)
            canvas?.drawLine(plusWidth / 2f + paint.strokeWidth / 2f, plusHeight / 2f, plusWidth.toFloat() - spacing, plusHeight / 2f, paint)
            canvas?.drawLine(plusWidth / 2f, spacing, plusWidth / 2f, plusHeight.toFloat() - spacing, paint)
        } else
            super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        plusWidth = w
        plusHeight = h
    }

    fun drawPlus() {
        drawPlus = true
        invalidate()
    }

    fun clearPlus() {
        drawPlus = false
        invalidate()
    }
}