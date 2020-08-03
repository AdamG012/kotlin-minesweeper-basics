package com.example.minesweeper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.play_button).setOnClickListener {
            findNavController().navigate(initPlayButtonDirections(view))
        }
    }


    /**
     * Given the view find the radio group and determine the select radio button
     * Once selected send through the string of the value and pass it to the Mine Sweeper fragment.
     */
    private fun initPlayButtonDirections(view: View): NavDirections {
        val id = view.findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId
        val str = view.findViewById<RadioButton>(id).text
        return MainFragmentDirections.actionMainMenuFragmentToMineSweeperFragment(str.toString())
    }
}