package com.linyuzai.component.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

/**
 * Created by linyuzai on 2017/10/23.
 * @author linyuzai
 */
class Circle(context: Context) : View(context) {
    private var x: Int = 0
    private var y: Int = 0
    private val paint: Paint = Paint()

    init {
        paint.isDither = true //防抖
        paint.isAntiAlias = true //抗锯齿
    }

    fun update(color: Int) {
        paint.color = color
        invalidate()
    }

    fun color(): Int = paint.color

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(x.toFloat(), y.toFloat(), Math.max(x, y).toFloat(), paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        x = w / 2
        y = h / 2
    }
}