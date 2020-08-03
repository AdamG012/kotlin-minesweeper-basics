package com.example.minesweeper

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Block(var x: Int, var y: Int, private var id: Int) {

    var isBomb: Boolean = false
    var numBombs: Int = 0
    lateinit var button: Button

    // State object of the block
    private var state = BlockState.UNCHECKED

    /**
     * Change the state of the block to the next one when clicked
     */
    fun changeState() {
        state = state.changeState()
        button.setBackgroundResource(state.minesweeperButtonBg)
        displayText()
    }


    /**
     * Getter for button id
     */
    fun getID(): Int {
        return id
    }


    /**
     * Create the button and the text view
     */
    fun createButton(view: View) {
        button = Button(view.context)
        button.setBackgroundResource(R.drawable.minesweeper_button_bg)
        button.id = id
        button.layoutParams = (LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
    }


    /**
     * Display the text of the button, the number of bombs or * if it is a bomb
     */
    private fun displayText() {
        when {
            state != BlockState.REVEALED -> {
                button.text = state.text
            }
            isBomb -> {
                button.text = "\uD83D\uDCA3"
            }
            else -> {
                button.text = numBombs.toString()
            }
        }
    }


    /**
     * This function is set to the onclick listener for the button and will reveal it by making it invisible
     */
    fun reveal() {
        state = BlockState.REVEALED
        button.setBackgroundResource(state.minesweeperButtonBg)
        displayText()
    }

    fun getState(): BlockState {
        return this.state
    }

}
