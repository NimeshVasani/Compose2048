package org.nimesh.game

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.nimesh.game.presentation.screens.MainScreen
import org.nimesh.game.presentation.viewmodels.GameViewModel

@Composable
@Preview
fun App() {
    val gameViewModel: GameViewModel = koinInject()
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = gameViewModel.gameState.collectAsState()

    LaunchedEffect(key1 = lifecycleOwner) {
        gameViewModel.loadHighScore()
        //gameViewModel.saveHighScore(state.value.highScore)
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> gameViewModel.saveHighScore(state.value.highScore)
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        // Clean up the observer when the composable is disposed
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    MaterialTheme {
        MainScreen()
    }
}
