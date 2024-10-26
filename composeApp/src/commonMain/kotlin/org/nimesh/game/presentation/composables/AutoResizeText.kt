package org.nimesh.game.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle

@Composable
fun AutoResizeText(modifier: Modifier = Modifier, value: String,initialTextStyle: TextStyle) {

    // State for text style
    var resizedTextStyle by remember { mutableStateOf(initialTextStyle) }

    // State for whether to draw the content
    var shouldDraw by remember { mutableStateOf(false) }


    Text(
        text = value,
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