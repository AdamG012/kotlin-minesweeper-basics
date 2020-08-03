package com.example.minesweeper

import kotlin.math.sqrt

/**
 * An enum used to determine the game size and parameters such as number of bombs.
 * Can be extended with a custom size for future editions
 */
enum class GameState(val sizeX: Int, val sizeY: Int) {
    SMALL(5, 5),
    MEDIUM(8, 8),
    LARGE(12, 20);

    // The number of bombs in this grid
    val bombs = sqrt((sizeX * sizeY).toDouble()).toInt()
}