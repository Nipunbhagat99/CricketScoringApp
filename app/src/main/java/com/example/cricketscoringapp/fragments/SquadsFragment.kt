package com.example.cricketscoringapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

        setupButtonText()

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

    private fun setupButtonText(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team1Name : String? = sharedPreferences.getString("team_1_name" , "").toString()
        val team2Name : String? = sharedPreferences.getString("team_2_name" , "").toString()
        btn_squad_1.text = team1Name
        btn_squad_2.text = team2Name

    }


}