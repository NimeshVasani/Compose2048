package org.nimesh.game.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import org.nimesh.game.data.model.GameState

@Composable
fun DecoratedText(state: State<GameState>) {
    // Define the text with different color spans
    val text = buildAnnotatedString {
        append("Your goal is to get the ")
        val targetTile = if (state.value.highScore >= 2048) 4096 else 2048
        withStyle(style = SpanStyle(color = Color.Green)) {
            append("$targetTile")
        }
        append(" tile!")
    }

    Box(contentAlignment = Alignment.Center) {
        Text(
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    blurRadius = 3f,
                    offset = Offset(2f, 5f)
                )
            ),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White, // Default color for the rest of the text
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}
