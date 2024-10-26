package org.nimesh.game.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.nimesh.game.data.model.GameIntent
import org.nimesh.game.presentation.composables.TileBox
import org.nimesh.game.presentation.viewmodels.GameViewModel
import kotlin.math.abs

@Composable
fun GameGrid(viewModel: GameViewModel = koinInject()) {
    val gameState by viewModel.gameState.collectAsState()
    remember { gameState }
    var lastValue by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()
    val swipeModifier = Modifier.pointerInput(gameState.isSwipeEnabled) {
        detectDragGestures(onDrag = { change, dragAmount ->
            change.consume()

            if (gameState.isSwipeEnabled) {
                val (dx, dy) = dragAmount
                if (abs(dx) > abs(dy)) {
                    if (dx > 0) {
                        viewModel.onIntent(GameIntent.SwipeRight)
                    } else {
                        viewModel.onIntent(GameIntent.SwipeLeft)
                    }
                } else {
                    if (dy > 0) {
                        viewModel.onIntent(GameIntent.SwipeDown)
                    } else {
                        viewModel.onIntent(GameIntent.SwipeUp)
                    }
                }
                coroutineScope.launch {
                    delay(200)
                    viewModel.enableSwipe()
                }
            }
        }, onDragEnd = {
            viewModel.enableSwipe()
        })
    }

    Box {
        Column(
            modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(8.dp)).fillMaxWidth()
                .aspectRatio(1f).background(Color.White).then(swipeModifier),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            gameState.grid.forEachIndexed { i, row ->
                Row(modifier = Modifier) {
                    row.forEachIndexed { j, tileValue ->
                        lastValue = gameState.prevGrid[i][j]
                        TileBox(value = tileValue, lastValue = lastValue)

                    }
                }
            }
        }
        if (viewModel.isGameOver()) {
            Column(
                modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(8.dp)).fillMaxWidth()
                    .aspectRatio(1f).background(Color.Black.copy(0.5f)).clickable(onClick = {
                        viewModel.onIntent(GameIntent.ResetGame)
                    }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over",
                    color = Color.Red,
                    fontSize = 28.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.ExtraBold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black, blurRadius = 3f, offset = Offset(2f, 5f)
                        )
                    ),
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}
