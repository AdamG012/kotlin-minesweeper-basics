package com.example.minesweeper

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class ScoreFragment : Fragment() {
    // The media player
    private lateinit var player: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game_over_screen, container, false)
    }

    // The argument collector given from the minesweeper fragment
    private val args: ScoreFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add a navigation back to the home page
        view.findViewById<Button>(R.id.back_button_score).setOnClickListener {
            stop()
            findNavController().navigate(R.id.action_ScoreFragment_to_MainMenuFragment)

        }

        // If the game is won then display winning text and play winning sound
        if (args.wonGame) {
            view.findViewById<TextView>(R.id.gameOverText).text = getText(R.string.winMsg)
            play(R.raw.win_sound)
        } else {
            view.findViewById<TextView>(R.id.gameOverText).text = getText(R.string.loseMsg)

            // Change the image view to the loss image
            view.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.image_lose)
            play(R.raw.loss_sound)
        }

        // Display the score
        view.findViewById<TextView>(R.id.gameOverScore).text = args.numRevealed.toString()
    }


    /**
     * Play the media given the R.raw
     */
    private fun play(id: Int) {
        player = MediaPlayer.create(this.context, id)
        player.setOnCompletionListener { stop() }
        player.start()
    }


    /**
     * Stop the media from being played, releasing it such that it stops taking resources.
     */
    private fun stop() {
        player.release()
    }

    /**
     * When the app is closed ensure that all media is closed as well
     */
    override fun onStop() {
        super.onStop()
        stop()
    }

}