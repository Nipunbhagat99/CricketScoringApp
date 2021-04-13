package com.example.cricketscoringapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.utils.SwipeToDeleteCallback
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


        val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_add_players.adapter as PlayerAdapter
                adapter.removeAt(viewHolder.adapterPosition)



            }
        }

        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rv_add_players)
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