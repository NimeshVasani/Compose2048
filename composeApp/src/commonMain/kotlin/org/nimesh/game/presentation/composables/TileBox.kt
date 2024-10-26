package org.nimesh.game.presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TileBox(value: Int, modifier: Modifier = Modifier,lastValue: Int) {
    // Initial font size
    val initialTextStyle = TextStyle(
        color = Color.Black,
        fontSize = 40.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif
    )

    // State for text style
    var resizedTextStyle by remember { mutableStateOf(initialTextStyle) }

    // State for whether to draw the content
    var shouldDraw by remember { mutableStateOf(false) }

    // State to remember the last value

    val scale = remember { Animatable(1f) }
    println("first :$lastValue")

    LaunchedEffect(value) {
        if (value == lastValue*2) {
            // Animate new tile
            scale.animateTo(
                targetValue = 1.5f, // Scale up for the animation
                animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
            )
            scale.animateTo(
                targetValue = 1f, // Scale back to original size
                animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
            )
            println("second :$lastValue")

        }
    }


    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(80.dp)
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .background(getTileColor(value))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (value > 0) {
            Text(
                text = value.toString(),
                softWrap = false,
                maxLines = 1,
                style = resizedTextStyle,
                onTextLayout = { result ->
                    if (result.hasVisualOverflow) {
                        // If there's an overflow, reduce the font size
                        resizedTextStyle = resizedTextStyle.copy(
                            fontSize = resizedTextStyle.fontSize * 0.95
                        )
                    } else {
                        shouldDraw = true
                    }
                },
                modifier = modifier.drawWithContent {
                    if (shouldDraw) {
                        drawContent()
                    }
                },
            )
        }
    }
}
