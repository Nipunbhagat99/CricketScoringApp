package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ChooseBatsmenAdapter
import com.example.cricketscoringapp.adapters.ChooseBowlerAdapter
import com.example.cricketscoringapp.models.BowlerModel
import com.example.cricketscoringapp.models.PlayerModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_choose_batsmen.*
import kotlinx.android.synthetic.main.activity_choose_bowler.*
import kotlinx.android.synthetic.main.exit_alert_dialog.view.*

class ChooseBowlerActivity : AppCompatActivity() {
    var teamNo =0
    private var teamName = ""
    private var list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_bowler)
        teamNo = intent.getIntExtra("team_no",0)
        getList(teamNo)
        rv_choose_bowler.layoutManager = LinearLayoutManager(this)

        val chooseBowlerAdapter = ChooseBowlerAdapter(this ,list )

        btn_next_start_match.setOnClickListener {

            val sharedPreferences : SharedPreferences= getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val bowlerName  = chooseBowlerAdapter.goNext()
            if(bowlerName == ""){
                Toast.makeText(this,"Please select a bowler", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val bowler = BowlerModel(bowlerName,teamName,0,0,"0.0",0,0 )
                Log.i("bowler", "bowler $bowlerName")
                val bowlerHash = HashMap<String ,Int>()
                bowlerHash[bowlerName] = 0
                val json = gson.toJson(bowler)
                editor.putString("bowler" , json)
                val bowlerHashJson = gson.toJson(bowlerHash)
                editor.putString("bowler_hash" , bowlerHashJson)
                editor.commit()
                val intent = Intent(this , MatchActivity::class.java)
                startActivity(intent)
                finish()
            }


        }

        rv_choose_bowler.adapter = chooseBowlerAdapter
    }

    private fun getList(x : Int){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val bowlingTeam = sharedPreferences.getString("team_$x", emptyList<PlayerModel>().toString())
        list = Gson().fromJson(bowlingTeam, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        rv_choose_bowler.adapter?.notifyDataSetChanged()
        teamName = sharedPreferences.getString("team_${x}_name" , "lol").toString()

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
}