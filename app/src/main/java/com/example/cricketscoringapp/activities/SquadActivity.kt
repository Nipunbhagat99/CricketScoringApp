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
import kotlinx.android.synthetic.main.activity_squad.*
import kotlinx.android.synthetic.main.activity_team_one.*

class SquadActivity : AppCompatActivity() {

    private var list = ArrayList<PlayerModel>()
    private var teamNo = 0
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)
        teamNo = intent.getIntExtra("team_no",1)
        loadData()
        setSupportActionBar(tb_squad)
        supportActionBar?.apply {

            val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , MODE_PRIVATE)
            val teamName : String? = sharedPreferences.getString("team_${teamNo}_name" , "").toString()
            if (teamName != null) {
                title = teamName.uppercase()
            }

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }
        tb_squad.setNavigationIcon(R.drawable.back_button_image)
        tb_squad.setNavigationOnClickListener {
            onBackPressed()
        }

        setUpPlayerRV()
    }


    private fun setUpPlayerRV(){
        rv_squad.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , list)

        rv_squad.adapter = playerAdapter


    }

    private  fun loadData(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)

        val json = sharedPreferences.getString("team_$teamNo" , emptyList<PlayerModel>().toString())
        list = Gson().fromJson(json, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        if(list == null ){
            list = ArrayList<PlayerModel>()
        }

    }
}