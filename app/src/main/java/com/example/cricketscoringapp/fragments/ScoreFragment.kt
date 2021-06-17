package com.example.cricketscoringapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.BatsmanModel
import com.example.cricketscoringapp.models.BowlerModel
import com.example.cricketscoringapp.models.PlayerModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_score.*


class ScoreFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeScore()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScore()

    }


    private fun setupScore(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val firstBatting = sharedPreferences.getInt("first_batting", 1)

        var teamName = ""
        teamName = if(firstBatting==1 ){
            sharedPreferences.getString("team_1_name", "").toString()
        } else{
            sharedPreferences.getString("team_2_name", "").toString()
        }
        val score = sharedPreferences.getInt("score", 0)
        val balls = sharedPreferences.getInt("balls", 0)
        val wickets = sharedPreferences.getInt("wickets", 0)
        val crr = sharedPreferences.getString("crr", "0.00")?.toDouble()
        val overs = balls/6
        val remBalls = balls%6
        val innings = sharedPreferences.getInt("innings", 0)
        tv_score_team_name.text = teamName.toUpperCase()
        tv_runs_wickets.text = "${score}-${wickets}"
        tv_overs.text = "(${overs}.${remBalls})"
        if(innings == 1) {
            ll_score_crr.visibility = View.INVISIBLE
            tv_req.text = "CRR"
            tv_score_req.text = "$crr"
            tv_target.visibility = View.GONE
        }



        setupBatsmen()
        setupBowler()

    }

    private fun setupBatsmen(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        val batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        tv_score_batsman1_name.text = batsman1.name
        tv_score_batsman1_runs.text = batsman1.runs.toString()
        tv_score_batsman1_balls.text = batsman1.ballsFaced.toString()
        tv_score_batsman1_fours.text = batsman1.fours.toString()
        tv_score_batsman1_sixes.text = batsman1.sixes.toString()
        tv_score_batsman1_strike_rate.text = batsman1.strikeRate.toString()

        val batsman2JSON = sharedPreferences.getString("batsman2","")
        val batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        tv_score_batsman2_name.text = batsman2.name
        tv_score_batsman2_runs.text = batsman2.runs.toString()
        tv_score_batsman2_balls.text = batsman2.ballsFaced.toString()
        tv_score_batsman2_fours.text = batsman2.fours.toString()
        tv_score_batsman2_sixes.text = batsman2.sixes.toString()
        tv_score_batsman2_strike_rate.text = batsman2.strikeRate.toString()

    }


    private fun setupBowler(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        tv_score_bowler_name.text = bowler.name
        tv_score_bowler_overs.text = bowler.overs.toString()
        tv_score_bowler_maiden_overs.text = bowler.maidens.toString()
        tv_score_bowler_runs.text = bowler.runsconceded.toString()
        tv_score_bowler_wickets.text = bowler.wickets.toString()
        tv_score_bowler_economy_rate.text = bowler.eco.toString()

    }

    private fun initializeScore(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("balls", 0)
        editor.putInt("score", 0)
        editor.putInt("wickets" , 0)
        editor.putString("crr", "0.00")
        editor.putInt("innings" , 1)
        editor.commit()
    }




}