package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ChooseBatsmenAdapter
import com.example.cricketscoringapp.adapters.ChooseBowlerAdapter
import com.example.cricketscoringapp.models.PlayerModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_choose_batsmen.*
import kotlinx.android.synthetic.main.activity_choose_bowler.*

class ChooseBowlerActivity : AppCompatActivity() {
    var teamNo =0
    private var list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_bowler)
        teamNo = intent.getIntExtra("team_no",0)
        getList(teamNo)
        rv_choose_bowler.layoutManager = LinearLayoutManager(this)

        val chooseBowlerAdapter = ChooseBowlerAdapter(this ,list )

        btn_next_start_match.setOnClickListener {
            val intent = Intent(this , MatchActivity::class.java)
            startActivity(intent)
            finish()

        }

        rv_choose_bowler.adapter = chooseBowlerAdapter
    }

    private fun getList(x : Int){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val bowlingTeam = sharedPreferences.getString("team_$x", emptyList<PlayerModel>().toString())
        list = Gson().fromJson(bowlingTeam, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
        rv_choose_bowler.adapter?.notifyDataSetChanged()

    }
}