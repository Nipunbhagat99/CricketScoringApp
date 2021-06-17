package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cricketscoringapp.R
import kotlinx.android.synthetic.main.activity_first_batting.*

class FirstBattingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_batting)

        setupTvText()

        tv_batting_team_1.setOnClickListener {
            chooseBatsmen(1)
        }

        tv_batting_team_2.setOnClickListener {
            chooseBatsmen(2)
        }


    }

    private fun setupTvText(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team1Name : String? = sharedPreferences.getString("team_1_name" , "").toString()
        tv_batting_team_1.text = team1Name?.toUpperCase()
        val team2Name : String? = sharedPreferences.getString("team_2_name" , "").toString()
        tv_batting_team_2.text = team2Name?.toUpperCase()
    }

    private fun chooseBatsmen(x : Int){
        val intent = Intent(this , ChooseBatsmenActivity::class.java)
        intent.putExtra("team_no" , x)
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("first_batting", x)
        editor.commit()
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}