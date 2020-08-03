package com.example.minesweeper;


import android.media.MediaPlayer
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController


class MineSweeper(private val gameState: GameState, private val player: MediaPlayer) {

    // The grid that stores all the blocks
    private val grid = Grid(gameState)

    // The number of non bomb blocks revealed
    private var numRevealed = 0

    /**
     * Initialise the game by calling the grid to setup
     */
    fun initGame() {
        grid.setup()
    }

    /**
     * Create grid using complete block list
     */
    fun createGrid(view: View) {
        // Create the layout
        val minesweeperLayout = view.findViewById<TableLayout>(R.id.minesweeper_layout)

        // For every single row
        for (row in grid.blocks) {
            val rowLayout = LinearLayout(minesweeperLayout.context)
            rowLayout.layoutParams = (LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ))

            // For every single block create the button and add it
            for (block in row) {
                block.createButton(view)

                // On short press change the state
                block.button.setOnClickListener {
                    changeState(block, view)

                }

                // On long press reveal the block
                block.button.setOnLongClickListener {
                    revealBlock(block, view)
                    true
                }
                rowLayout.addView(block.button)
            }
            minesweeperLayout.addView(rowLayout)
        }
    }

    /**
     * Change the state of the block when hold click
     */
    private fun changeState(block: Block, view: View) {
        block.changeState()
        if (checkWon()) {
            gameOver(view, true)
        }
    }


    /**
     * Reveal the block when clicked
     * If it is a bomb then signal game over
     */
    private fun revealBlock(block: Block, view: View) {
        if (block.getState() == BlockState.REVEALED) {
            return
        }

        // If block is not a bomb and has zero bombs next to it then reveal all surrounding
        if (!block.isBomb && block.numBombs == 0) {
            block.reveal()
            revealZero(block, view)
        }
        // otherwise reveal the block
        else {
            block.reveal()
        }

        // If the block is a bomb then call the game over method
        if (block.isBomb) {
            Toast.makeText(view.context, "Game Over", Toast.LENGTH_SHORT).show()
            gameOver(view, false)
            return
        }

        // Increment the number of revealed blocks
        numRevealed++

        // Increase the score to the number revealed
        increaseScore(view)

        // Check if the user has won if so call the game over method
        if (checkWon()) {
            Toast.makeText(view.context, "You Win, Congratulations", Toast.LENGTH_SHORT).show()
            gameOver(view, true)
            return
        }
    }


    /**
     * Helper function to update the view of the text every time a block is clicked on
     */
    private fun increaseScore(view: View) {
        view.findViewById<TextView>(R.id.score).text = numRevealed.toString()
    }


    /**
     * When there is a block with zero bombs then scan across adjacent bombs reveal them too
     */
    private fun revealZero(block: Block, view: View) {
        for (k in -1..1) {
            for (l in -1..1) {
                if (block.y - k < 0 || block.y - k >= gameState.sizeY) {
                    break
                }
                if (block.x - l < 0 || block.x - l >= gameState.sizeX) {
                    continue
                }

                // TODO may need to change this to only for the single
                revealBlock(grid.blocks[block.y - k][block.x - l], view)
            }
        }
    }


    /**
     * Release the player when navigating off of the view
     */
    private fun stop() {
        player.release()
    }


    /**
     * Navigate to the game over screen
     */
    private fun gameOver(view: View, wonGame: Boolean) {
        stop()
        view.findFragment<Fragment>().findNavController().navigate(MineSweeperFragmentDirections.actionMineSweeperFragmentToScoreFragment(wonGame, numRevealed))
    }


    /**
     * Through checking that all locations have been revealed or flagged
     * If won then return true
     */
    private fun checkWon(): Boolean {
        if (numRevealed < gameState.sizeX * gameState.sizeY - gameState.bombs) {
            return false
        }

        for (index in grid.bombLocations) {
            if (grid.blockMap[index]?.getState() != BlockState.FLAGGED) {
                return false
            }
        }
        return true
    }

}
