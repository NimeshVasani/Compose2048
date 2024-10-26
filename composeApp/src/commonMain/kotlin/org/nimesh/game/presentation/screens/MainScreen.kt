package org.nimesh.game.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import org.nimesh.game.data.model.GameIntent
import org.nimesh.game.presentation.composables.AutoResizeText
import org.nimesh.game.presentation.composables.CustomAlertDialog
import org.nimesh.game.presentation.composables.DecoratedText
import org.nimesh.game.presentation.viewmodels.GameViewModel

@Composable
fun MainScreen(
    viewModel: GameViewModel = koinInject()
) {
    val state = viewModel.gameState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF03A38E), Color(0xFF3C7055)
                )
            )
        ).safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The rest of your UI remains the same
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1976D2)
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AutoResizeText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = "2048",
                    initialTextStyle = TextStyle(
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Card(
                modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD4CFC9)
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AutoResizeText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = "Score",
                    initialTextStyle = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black.copy(0.72f),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                AutoResizeText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = state.value.score.toString(),
                    initialTextStyle = TextStyle(
                        fontSize = 30.sp,
                        color = Color.Black.copy(0.72f),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

            }
            Card(
                modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE69D45)
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Spacer(modifier = Modifier.weight(1f))

                AutoResizeText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = "Highest",
                    initialTextStyle = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                AutoResizeText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    value = state.value.highScore.toString(),
                    initialTextStyle = TextStyle(
                        fontSize = 30.sp,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        DecoratedText(state)
        Spacer(modifier = Modifier.height(16.dp))

            GameGrid()


        Spacer(modifier = Modifier.height(16.dp))
        // Reset Button
        Button(onClick = {
            if (viewModel.isGameOver()){
                viewModel.onIntent(GameIntent.ResetGame)
            }
            else
                showDialog.value = true

        }) {
            Text("Reset Game")
        }

    }
    CustomAlertDialog(
        showDialog = showDialog,
        title = "Reset Game",
        message = "Are you sure you want to reset the game?",
        onConfirm = {
            viewModel.onIntent(GameIntent.ResetGame)
        },
        onDismiss = {
            showDialog.value = false
        }
    )
}

