package com.example.cricketscoringapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.models.PlayerModel
import kotlinx.android.synthetic.main.activity_team_one.*

class TeamOneActivity : AppCompatActivity() {

    private val list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_one)

        setUpPlayerRV()
    }

    private fun setUpPlayerRV(){
        rv_add_players.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , getPlayerList())

        rv_add_players.adapter = playerAdapter
    }

    private fun getPlayerList(): ArrayList<PlayerModel>{


        var player = PlayerModel(
                "Nipun",
                    "Batsman"
        )
        list.add(player)

        player = PlayerModel("Chhibber" , "Allrounder")
        list.add(player)
        player = PlayerModel("Chhibber" , "Allrounder")
        list.add(player)
        player = PlayerModel("Chhibber" , "Allrounder")
        list.add(player)

        return list

    }
}