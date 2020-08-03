package com.example.minesweeper

/*
 * Enum BlockState to cycle through states of the blocks
 */
enum class BlockState(
    private val nextState: Int,
    val minesweeperButtonBg: Int,
    val text: String
) {
    UNCHECKED(1, R.drawable.minesweeper_button_bg, ""),
    FLAGGED(2, R.drawable.minesweeper_flagged_bg, "\uD83D\uDEA9"),
    UNKNOWN(0, R.drawable.minesweeper_unknown_bg, "?"),
    REVEALED(3, R.drawable.minesweeper_revealed_bg, "");

    /**
     * Change the state of the block
     */
    fun changeState(): BlockState {
        return values()[nextState]
    }

}