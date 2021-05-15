package com.example.cricketscoringapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.activities.MatchActivity
import com.example.cricketscoringapp.activities.SquadActivity
import kotlinx.android.synthetic.main.fragment_squads.*


class SquadsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_squads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_squad_1.setOnClickListener {
            val intent = Intent(activity, SquadActivity::class.java)
            intent.putExtra("team_no" , 1)
            startActivity(intent)

        }
        btn_squad_2.setOnClickListener {
            val intent = Intent(activity, SquadActivity::class.java)
            intent.putExtra("team_no" , 2)
            startActivity(intent)

        }
    }


}