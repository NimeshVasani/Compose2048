package org.nimesh.game.presentation.composables

import androidx.compose.ui.graphics.Color

fun getTileColor(value: Int): Color {
    return when (value) {
        2 -> Color(0xFFD4CFC9)
        4 -> Color(0xFF355C7D)
        8 -> Color(0xFF725A7A)
        16 -> Color(0xFFC56C86)
        32 -> Color(0xFFFF7582)
        64 -> Color(0xFF4CAF50)
        128 -> Color(0xFFFFBD71)
        256 -> Color(0xFF2196F3)
        512 -> Color(0xFFD4CfC9)
        1024 -> Color(0xFF1976D2)
        2048 -> Color(0xFFE69D45)
        else -> Color(0xFFE8F5E9)
    }
}
