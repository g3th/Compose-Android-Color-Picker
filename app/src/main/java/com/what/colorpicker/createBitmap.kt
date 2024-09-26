package com.what.colorpicker

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

fun createBitmap(boxW: Float, boxH: Float, start: Color, end: Color): ImageBitmap{
    val canvasDrawScope = CanvasDrawScope()
    val bitmap = ImageBitmap(boxW.roundToInt(), boxH.roundToInt())
    val canvas = Canvas(bitmap)
    val paint = Paint()
    paint.shader = LinearGradientShader(
        from=Offset.Zero,
        to=Offset(boxW, boxH),
        colors = listOf(end, start),
        tileMode = TileMode.Clamp
    )
    for (i in 0..boxW.roundToInt()) {
        canvas.drawLine(p1 = Offset.Zero,
            p2 = Offset(i.toFloat(), boxH),
            paint = paint
        )
    }
    return bitmap
}