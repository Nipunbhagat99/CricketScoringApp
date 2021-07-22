package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ViewPagerAdapter
import com.example.cricketscoringapp.fragments.OversFragment
import com.example.cricketscoringapp.fragments.ScoreFragment
import com.example.cricketscoringapp.fragments.ScorecardFragment
import com.example.cricketscoringapp.fragments.SquadsFragment
import com.example.cricketscoringapp.models.OverModel
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.utils.RefreshInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match.*
import kotlinx.android.synthetic.main.activity_team_two.*
import kotlinx.android.synthetic.main.exit_alert_dialog.view.*
import kotlinx.android.synthetic.main.new_player_dialog.*
import kotlinx.android.synthetic.main.new_player_dialog.view.*


class MatchActivity : AppCompatActivity(), RefreshInterface{

    private val squadsFragment = SquadsFragment()
    private val scoreFragment = ScoreFragment()
    private val scorecardFragment = ScorecardFragment()
    private val oversFragment = OversFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)




        setupOversList()


        setupTabs()
        setupVersus()

    }


    override fun onBackPressed() {
        val mDialogView = LayoutInflater
            .from(this)
            .inflate(R.layout.exit_alert_dialog,null)

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)


        val mAlertDialog = mBuilder.show()

        mDialogView.btn_exit_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogView.btn_exit.setOnClickListener {
            this.finish()
            mAlertDialog.dismiss()
        }
    }



    private fun setupTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(squadsFragment, "Squads")
        adapter.addFragment(scoreFragment, "Score")
        adapter.addFragment(scorecardFragment, "Scorecard")
        adapter.addFragment(oversFragment, "Overs")
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
        val innings = sharedPreferences.getInt("innings", 0)
        if(innings ==0){
            val list = ArrayList<OverModel>()
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            val overListJson = Gson().toJson(list)
            editor.putString("over_list", overListJson)
            editor.putString("scorecard_list", "")
            editor.commit()
        }

        val editor = sharedPreferences.edit()
        editor.putInt("innings", innings+1)
        editor.commit()

    }

    override fun refreshAdapter() {
        scorecardFragment.refresh()
    }


}