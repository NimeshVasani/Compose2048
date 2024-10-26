package org.nimesh.game.data.model

sealed class GameIntent {
    data object ResetGame : GameIntent()
    data object SwipeUp : GameIntent()
    data object SwipeDown : GameIntent()
    data object SwipeLeft : GameIntent()
    data object SwipeRight : GameIntent()
}

data class GameState(
    val grid: List<List<Int>> = emptyList(),
    val prevGrid: List<List<Int>> = emptyList(),
    val score: Int = 0,
    val highScore: Long = 0L,
    val isSwipeEnabled: Boolean = true,
    val isGameOver: Boolean = false,
    val mergedPositions: List<Pair<Int, Int>> = emptyList(),
    val newTilePosition: Pair<Int, Int>? = null,
    val tileChanges: List<List<Boolean>> = List(4) { List(4) { false } }  // Track changes here

)
