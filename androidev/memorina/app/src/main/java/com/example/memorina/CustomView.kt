package com.example.memorina

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var viewWidth = 0
    var viewHeight = 0

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        viewWidth = right - left
        viewHeight = bottom - top
        Log.d("ViewSize", "onLayout: width = $viewWidth, height = $viewHeight")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        Log.d("ViewSize", "onDraw: canvas width = $canvasWidth, canvas height = $canvasHeight")
    }
}
