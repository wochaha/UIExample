package com.example.uiexample.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DragRoundView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0) :
    View(context, attributeSet, defStyleAttr, defStyleRes) {

    private var rawX: Float = 0f
    private var rawY: Float = 0f

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    private val paint = Paint().apply {
        strokeWidth = 30f
        color = Color.parseColor("#FF0000")
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        canvas.drawCircle(width / 2f, height / 2f, width / 3f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        rawX = event.x
        rawY = event.y
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = rawX
                lastY = rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = rawX - lastX
                val offsetY = rawY - lastY
                Log.d("DragEvent","x: $lastX, y: $lastY")
                layout(left+offsetX.toInt(), top+offsetY.toInt(), right+offsetX.toInt(), bottom+offsetY.toInt())
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }

}