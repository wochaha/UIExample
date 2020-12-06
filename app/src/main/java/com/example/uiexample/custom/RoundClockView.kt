package com.example.uiexample.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

class RoundClockView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0)
    : View(context, attributes, defStyleAttr, defStyleRes) {

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 0f
    }

    private var initClockStroke: Boolean = false

    private val middleLineLenght = 40f
    private val longestLineLength = 55f

    private var secondDegree = 0f
    private var minuteDegree = 0f
    private var hourDegree = 0f

    private val timer: Timer = Timer()
    private val timerTask: TimerTask = object : TimerTask(){
        override fun run() {
            if (secondDegree == 360f) secondDegree = 0f
            if (minuteDegree == 360f) minuteDegree = 0f
            if (hourDegree == 360f) hourDegree = 0f
            secondDegree += 6f
            minuteDegree += 0.1f
            hourDegree += 1f / 120f
            postInvalidate()
        }

    }

    public fun start() {
        initTime()
        timer.schedule(timerTask, 0, 1000)
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        val timeMilliSecond = System.currentTimeMillis()
        calendar.timeInMillis = timeMilliSecond
        val second = calendar.get(Calendar.SECOND)
        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR_OF_DAY) - 12
        Log.d("TOME", "$hour : $minute : $second")
        secondDegree = 6f * second
        minuteDegree = minute * 6f + (second / 10f)
        hourDegree = hour * 30f + (minute / 60f * 30f) + (second / 60f * 6f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        paint.strokeWidth = 3f
        canvas.drawCircle(width / 2f, height / 2f, width / 3f, paint)

        paint.strokeWidth = 10f
        canvas.drawPoint(width / 2f, height / 2f, paint)

        //画刻度线
        paint.strokeWidth = 2f
        canvas.translate(width / 2f, height / 2f)

        for (i in 0 until 360) {
            if (i % 30 == 0) {
                canvas.drawLine(width / 3f - longestLineLength, 0f, width / 3f, 0f, paint)
            } else if (i % 6 == 0) {
                canvas.drawLine(width / 3f - middleLineLenght, 0f, width / 3f, 0f, paint)
            }
            canvas.rotate(1f)
        }

        canvas.save()
        paint.apply {
            textSize = 40f
            style = Paint.Style.FILL
        }

        val textBound = Rect()
        for (i in 0 until 12) {
            if (i == 0) {
                paint.getTextBounds("12", 0, "12".length, textBound)
                canvas.drawText("12", - textBound.width() / 2f, -(width / 3f - 100), paint)
            } else {
                paint.getTextBounds("$i", 0, "$i".length, textBound)
                canvas.drawText("$i", - textBound.width() / 2f, -(width / 3f - 100), paint)
            }
            canvas.rotate(30f)
        }
        canvas.restore()

        drawSecondLine(canvas, paint)
        drawMinuteLine(canvas, paint)
        drawHourLine(canvas, paint)
    }

    private fun drawSecondLine(canvas: Canvas, paint: Paint) {
        canvas.save()
        paint.apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.rotate(secondDegree)
        canvas.drawLine(0f, 0f, 0f, -190f, paint)
        canvas.restore()
    }

    private fun drawMinuteLine(canvas: Canvas, paint: Paint) {
        canvas.save()
        paint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
        canvas.rotate(minuteDegree)
        canvas.drawLine(0f, 0f, 0f, -130f, paint)
        canvas.restore()
    }

    private fun drawHourLine(canvas: Canvas, paint: Paint) {
        canvas.save()
        paint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 12f
        }
        canvas.rotate(hourDegree)
        canvas.drawLine(0f, 0f, 0f, -100f, paint)
        canvas.restore()
    }
}