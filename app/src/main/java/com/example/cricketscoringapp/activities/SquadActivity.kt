package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.utils.SwipeToDeleteCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_team_one.*

class SquadActivity : AppCompatActivity() {

    private var list = ArrayList<PlayerModel>()
    private var teamNo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)
        teamNo = intent.getIntExtra("team_no",1)
        loadData()

        setUpPlayerRV()
    }


    private fun setUpPlayerRV(){
        t1_rv_add_players.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , list)

        t1_rv_add_players.adapter = playerAdapter


    }

    private  fun loadData(){
        val empty :String= ""
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team1Name : String? = sharedPreferences.getString("team_1_name" , "").toString()
        if(team1Name == null){
            et_1_team_name.setText(empty)
        }
        else {
            et_1_team_name.setText(team1Name)
        }

        val json = sharedPreferences.getString("team_1" , emptyList<PlayerModel>().toString())
        list = Gson().fromJson(json, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        if(list == null ){
            list = ArrayList<PlayerModel>()
        }

    }
}