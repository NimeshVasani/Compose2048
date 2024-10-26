package org.nimesh.game.data.repositories

import kotlin.random.Random


class GameRepository {

    // Generates the initial grid
    fun generateInitialGrid(): MutableList<MutableList<Int>> {
        val grid = MutableList(4) { MutableList(4) { 0 } }
        grid[0][0] = 2
        grid[1][1] = 2
        return grid
    }

    // Move tiles up
    fun moveUp(grid: List<List<Int>>): Pair<MutableList<MutableList<Int>>, Int> =
        mergeTilesWithScore(transpose(grid).map { mergeAndMoveLeftWithScore(it) }).let {
            Pair(transpose(it.first), it.second)
        }

    // Move tiles down
    fun moveDown(grid: List<List<Int>>): Pair<MutableList<MutableList<Int>>, Int> =
        mergeTilesWithScore(transpose(grid).map { mergeAndMoveRightWithScore(it) }).let {
            Pair(transpose(it.first), it.second)
        }

    // Move tiles left
    fun moveLeft(grid: List<List<Int>>): Pair<MutableList<MutableList<Int>>, Int> =
        mergeTilesWithScore(grid.map { mergeAndMoveLeftWithScore(it) })

    // Move tiles right
    fun moveRight(grid: List<List<Int>>): Pair<MutableList<MutableList<Int>>, Int> =
        mergeTilesWithScore(grid.map { mergeAndMoveRightWithScore(it) })

    // Transpose the grid for vertical movements
    private fun transpose(grid: List<List<Int>>): MutableList<MutableList<Int>> {
        return List(grid.size) { row ->
            MutableList(grid.size) { col -> grid[col][row] }
        }.toMutableList()
    }

    // Merge and move tiles to the left, with score tracking
    private fun mergeAndMoveLeftWithScore(row: List<Int>): Pair<MutableList<Int>, Int> {
        var score = 0
        val newRow = row.filter { it != 0 }.toMutableList()

        newRow.indices.forEach { i ->
            if (i < newRow.size - 1 && newRow[i] == newRow[i + 1]) {
                newRow[i] *= 2
                score += newRow[i]
                newRow[i + 1] = 0
            }
        }

        return Pair(newRow.filter { it != 0 }.toMutableList().apply {
            repeat(4 - size) { add(0) }
        }, score)
    }

    // Merge and move tiles to the right, with score tracking
    private fun mergeAndMoveRightWithScore(row: List<Int>): Pair<MutableList<Int>, Int> {
        var score = 0
        val newRow = row.filter { it != 0 }.toMutableList()

        (newRow.size - 1 downTo 1).forEach { i ->
            if (newRow[i] == newRow[i - 1]) {
                newRow[i] *= 2
                score += newRow[i]
                newRow[i - 1] = 0
            }
        }

        return Pair(newRow.filter { it != 0 }.toMutableList().apply {
            repeat(4 - size) { add(0, 0) }
        }, score)
    }

    // Merge the tiles after movement and return the updated grid and score
    private fun mergeTilesWithScore(grid: List<Pair<MutableList<Int>, Int>>): Pair<MutableList<MutableList<Int>>, Int> {
        var totalScore = 0
        val mergedGrid = grid.map { (row, score) ->
            totalScore += score
            row
        }.toMutableList()

        return Pair(mergedGrid, totalScore)
    }

    // Generate a new tile in a random empty position
    fun generateNewTile(grid: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val emptyCells = grid.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, value -> if (value == 0) Pair(i, j) else null }
        }

        emptyCells.randomOrNull()?.let { (x, y) ->
            grid[x][y] = if (Random.nextFloat() < 0.9) 2 else 4
        }

        return grid
    }

    // Check if the game is over (no empty cells and no possible merges)
    fun isGameOver(grid: List<List<Int>>): Boolean {
        val canMergeHorizontally = canMerge(grid)
        val canMergeVertically = canMerge(transpose(grid))
        return grid.none { row -> row.contains(0) } && !canMergeHorizontally && !canMergeVertically
    }

    // General merge check for both directions
    private fun canMerge(grid: List<List<Int>>): Boolean =
        grid.any { row -> row.zipWithNext().any { (a, b) -> a == b } }
}

