package com.example.minesweeper

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import java.util.*

class MineSweeperFragment : Fragment() {

    // The media player for the sound while playing the game
    private lateinit var player: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.minesweeper_fragment, container, false)
    }


    // The arguments passed by the main fragment
    private val args: MineSweeperFragmentArgs by navArgs()


    /**
     * When the view is created add navigation back to the main menu.
     * Also on this fragment initialise the MineSweeper object and create the grid.
     * Begin playing sound file which is Megalovania - Undertale which is owned and produced by 'Toby "Radiation" Fox'
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add a navigation from the minesweeper game to the home page
        view.findViewById<Button>(R.id.back_button).setOnClickListener {
            stop()
            findNavController().navigate(R.id.action_MineSweeperFragment_to_MainMenuFragment)
        }

        // Start the player
        R.raw.sans_game_music.play(view)

        // Initialise the minesweeper class
        val mineSweeper = MineSweeper(getState(), player)
        mineSweeper.initGame()
        mineSweeper.createGrid(view)

        val textView = view.findViewById<TextView>(R.id.timer)

        // The timer will schedule at a fixed rate each second to update the timer text
        Timer("GameTime", true).scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    updateTime(textView)
                }
            }
        }, 0, 1000)
    }


    /**
     * Update the time of the text view every second
     */
    private fun updateTime(textView: TextView) {
        textView.text = ((textView.text.toString().toInt() + 1).toString())
    }


    /**
     * Get the size of the game given the selection
     */
    private fun getState(): GameState {

        return GameState.valueOf(args.gameSize)
    }


    /**
     * Play the media player and set looping to true
     */
    private fun Int.play(view: View) {
        player = MediaPlayer.create(view.context, this)
        player.start()
        player.isLooping = true
    }

    /**
     * Release and stop the media player when navigated outside of the view
     */
    private fun stop() {
        player.release()

    }

    /**
     * When the app is minimised make sure to release the media player
     */
    override fun onStop() {
        super.onStop()
        stop()
    }
}