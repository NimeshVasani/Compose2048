package org.nimesh.game.presentation.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.nimesh.game.data.model.GameIntent
import org.nimesh.game.data.model.GameState
import org.nimesh.game.data.repositories.GameRepository

class GameViewModel(
    private val gameRepository: GameRepository,
    private val prefs: DataStore<Preferences>
) : ViewModel() {

    private val _gameState =
        MutableStateFlow(
            GameState(
                grid = gameRepository.generateInitialGrid(),
                prevGrid = gameRepository.generateInitialGrid()
            )
        )
    val gameState: StateFlow<GameState> = _gameState

    // Process user intents
    fun onIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.ResetGame -> resetGame()
            is GameIntent.SwipeUp -> updateGrid { gameRepository.moveUp(it) }
            is GameIntent.SwipeDown -> updateGrid { gameRepository.moveDown(it) }
            is GameIntent.SwipeLeft -> updateGrid { gameRepository.moveLeft(it) }
            is GameIntent.SwipeRight -> updateGrid { gameRepository.moveRight(it) }
        }
    }

    private fun resetGame() {
        _gameState.value = _gameState.value.copy(
            grid = gameRepository.generateInitialGrid(),
            highScore = _gameState.value.highScore
        )
    }

    private fun updateGrid(moveAction: (List<List<Int>>) -> Pair<MutableList<MutableList<Int>>, Int>) {
        val (newGrid, scoreIncrease) = moveAction(_gameState.value.grid)
        if (newGrid != _gameState.value.grid) {
            val updatedGrid = gameRepository.generateNewTile(newGrid)
            _gameState.update { currentState ->
                currentState.copy(
                    grid = updatedGrid,
                    score = currentState.score + scoreIncrease,
                    isSwipeEnabled = false,
                    prevGrid = _gameState.value.grid,
                )
            }
            checkAndUpdateHighScore()
        }
    }

    // Handle high score
    private fun checkAndUpdateHighScore() {
        if (_gameState.value.score > _gameState.value.highScore) {
            _gameState.update { it.copy(highScore = _gameState.value.score.toLong()) }
        }
    }

    fun saveHighScore(highScore: Long) {
        println("Saving high score: $highScore")
        viewModelScope.launch {
            prefs.edit { it[longPreferencesKey("highScore")] = highScore }
        }
    }

    fun loadHighScore() {
        viewModelScope.launch {
            prefs.data.map { it[longPreferencesKey("highScore")] ?: 0L }
                .collect { data -> _gameState.update { it.copy(highScore = data) } }
        }
    }

    // Check if game over
    fun isGameOver() = gameRepository.isGameOver(_gameState.value.grid)
    fun enableSwipe() {
        _gameState.value = _gameState.value.copy(isSwipeEnabled = true)
    }
}