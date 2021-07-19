package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ChooseBatsmenAdapter
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.models.BatsmanModel
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.utils.SwipeToDeleteCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_choose_batsmen.*
import kotlinx.android.synthetic.main.activity_team_one.*

class ChooseBatsmenActivity : AppCompatActivity() {

    var teamNo =0
    private var list = ArrayList<PlayerModel>()
    private var teamName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_batsmen)

        teamNo = intent.getIntExtra("team_no",0)

        getList(teamNo)

        rv_choose_batsmen.layoutManager = LinearLayoutManager(this)

        val chooseBatsmenAdapter = ChooseBatsmenAdapter(this ,list )

        rv_choose_batsmen.adapter = chooseBatsmenAdapter



        btn_next_choose_bowlers.setOnClickListener {

            val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
            val battingTeam = sharedPreferences.getString("team_$teamNo", emptyList<PlayerModel>().toString())
            val list1 : ArrayList<PlayerModel> = Gson().fromJson(battingTeam, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
            val remainingBatsmen : ArrayList<PlayerModel> = chooseBatsmenAdapter.goNext()

            Log.i("LMAO" , "${list1.size - remainingBatsmen.size}")

            if(list1.size - remainingBatsmen.size == 2){
                val sharedPreferences : SharedPreferences= getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                val gson = Gson()
                val json = gson.toJson(remainingBatsmen)
                editor.putString("remainingBatsmen" , json)
                val batsman1Name = chooseBatsmenAdapter.batsman1
                val batsman2Name = chooseBatsmenAdapter.batsman2
                var batsman1 = BatsmanModel(batsman1Name, teamName , 0 , 0,"0.0", 0, 0, "Not Out" ,true )
                val batsman2 = BatsmanModel(batsman2Name, teamName , 0 , 0,"0.0", 0, 0, "Not Out" ,true )
                val batsman1Gson = gson.toJson(batsman1)
                val batsman2Gson = gson.toJson(batsman2)
                editor.putString("batsman1" , batsman1Gson)
                editor.putString("batsman2" , batsman2Gson)
                editor.commit()
                val intent = Intent(this , ChooseBowlerActivity::class.java)
                if(teamNo==1){
                teamNo=2
            }
            else if(teamNo==2){
                teamNo=1
            }

            intent.putExtra("team_no" , teamNo)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()}
            else{
                Toast.makeText(this, "Please Select 2 players" , Toast.LENGTH_SHORT).show()
            }
        }



    }



    private fun getList(x : Int){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val battingTeam = sharedPreferences.getString("team_$x", emptyList<PlayerModel>().toString())
        list = Gson().fromJson(battingTeam, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
        rv_choose_batsmen.adapter?.notifyDataSetChanged()
        teamName = sharedPreferences.getString("team_${x}_name" , "lol").toString()


    }
}