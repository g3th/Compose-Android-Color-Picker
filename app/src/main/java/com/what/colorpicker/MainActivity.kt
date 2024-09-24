package com.what.colorpicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.what.colorpicker.ui.theme.ColorpickerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColorpickerTheme {
                DragEvents()
            }
        }
    }
}

fun DrawScope.colourPickerPointer(width: Float, height: Float){
    drawCircle(Color.White, radius = 30f, center = Offset(width / 2, height / 2), style = Stroke(width = 6f))
    drawCircle(Color.White, radius = 15f, center = Offset(width / 2, height / 2), style = Stroke(width = 4f))
}

@Composable
fun DragEvents(){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.c)
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var resizedBitmap by remember { mutableStateOf(Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888)) }
    var boxDimensions by remember { mutableStateOf(Pair(0, 0))}
    var boxSizeH by remember { mutableStateOf(150.dp)}
    var boxSizeW by remember { mutableStateOf(150.dp)}
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(parseColor("#26b2bf")),
                        Color(parseColor("#26bf5c"))
                    )
                )
            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row{
            Text("Color Picker Module", fontSize = 25.sp)
        }
        HorizontalDivider(thickness = 15.dp, modifier = Modifier.width(5.dp),
            color = Color.Transparent)
        BoxWithConstraints(
            Modifier
                .size(boxSizeH, boxSizeW)
                .drawBehind {
                    drawImage(image = resizedBitmap.asImageBitmap())
                }){
            boxDimensions = Pair(constraints.maxWidth, constraints.maxHeight)
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, boxDimensions.first, boxDimensions.second, false)
            Canvas(Modifier
                .size(5.dp, 15.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        val canvasSize = this.size
                        val summedX = offsetX + dragAmount.x
                        val summedY = offsetY + dragAmount.y
                        offsetX = summedX.coerceIn(
                            Offset.Zero.x,
                            boxDimensions.first.toFloat() - canvasSize.width
                        )
                        offsetY = summedY.coerceIn(
                            Offset.Zero.y,
                            boxDimensions.second.toFloat() - canvasSize.height
                        )
                    }
                }, onDraw = {
                    colourPickerPointer(size.width, size.height)
            })
        }
        val color = resizedBitmap.getPixel(offsetX.roundToInt(), offsetY.roundToInt())
        HorizontalDivider(thickness = 15.dp, color=Color.Transparent,
            modifier = Modifier.width(width = 5.dp))
        Text("Increase/Decrease Bitmap Size")
        Row{
            val config = LocalConfiguration.current
            val density = LocalDensity.current
            val w = config.screenWidthDp
            val h = config.screenHeightDp
            TextButton(onClick = {
                if (boxSizeW <= 300.dp || boxSizeH <= 300.dp) {
                    boxSizeW += 5.dp
                    boxSizeH += 5.dp
                }
            }, modifier = Modifier.size(50.dp), shape = RoundedCornerShape(10),
                colors = ButtonColors(contentColor = Color.White,
                    containerColor = Color(parseColor("#26bcbf")),
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.White
                )){
                Text("+", fontSize = 30.sp)
            }
            VerticalDivider(thickness = 5.dp, color = Color.Transparent,
                modifier = Modifier
                    .width(5.dp)
                    .height(1.dp))
            TextButton(onClick = {
                if (boxSizeW >= 150.dp || boxSizeH >= 150.dp) {
                    boxSizeW -= 5.dp
                    boxSizeH -= 5.dp
                }
            }, modifier = Modifier.size(50.dp), shape = RoundedCornerShape(10),
                colors = ButtonColors(contentColor = Color.White,
                    containerColor = Color(parseColor("#26bcbf")),
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.White
                )){
                Text("-", fontSize = 25.sp)
            }
        }
        HorizontalDivider(thickness = 15.dp, color=Color.Transparent,
            modifier = Modifier.width(width = 5.dp))
        Box(
            Modifier
                .background(Color(color.red, color.green, color.blue, color.alpha))
                .size(50.dp, 50.dp)){

        }
        Text("Current Colour: ")
        var result by remember {mutableStateOf("")}
        SideEffect {
            scope.launch(Dispatchers.IO){
                result = apiCall(color.red, color.green, color.blue)
            }
        }
        Text(result)
        HorizontalDivider(thickness = 1.dp, color=Color.White,
            modifier = Modifier.width(width = 100.dp))
        Text("Original Bitmap Size: ")
        Text("${bitmap.width.dp} -- ${bitmap.height.dp}")
        HorizontalDivider(thickness = 1.dp, color=Color.White,
            modifier = Modifier.width(width = 100.dp))
        Text("Resized Bitmap Size: ")
        Text("${resizedBitmap.width.dp} -- ${resizedBitmap.height.dp}")
    }

}
