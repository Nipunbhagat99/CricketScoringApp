package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ViewPagerAdapter
import com.example.cricketscoringapp.fragments.OversFragment
import com.example.cricketscoringapp.fragments.ScoreFragment
import com.example.cricketscoringapp.fragments.ScorecardFragment
import com.example.cricketscoringapp.fragments.SquadsFragment
import com.example.cricketscoringapp.models.OverModel
import com.example.cricketscoringapp.models.PlayerModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        setupOversList()

        setupTabs()
        setupVersus()
    }

    private fun setupTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SquadsFragment(), "Squads")
        adapter.addFragment(ScoreFragment(), "Score")
        adapter.addFragment(ScorecardFragment(), "Scorecard")
        adapter.addFragment(OversFragment(), "Overs")
        vp_match.adapter = adapter
        tabs.setupWithViewPager(vp_match)

    }

    private fun setupVersus(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team1 = sharedPreferences.getString("team_1_name", "")
        val team2 = sharedPreferences.getString("team_2_name", "")
        tv_bar.text = "${team1?.toUpperCase()} v ${team2?.toUpperCase()}"
    }

    private fun setupOversList(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val list = ArrayList<OverModel>()
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val overListJson = Gson().toJson(list)
        editor.putString("over_list", overListJson)
        editor.commit()
    }
}