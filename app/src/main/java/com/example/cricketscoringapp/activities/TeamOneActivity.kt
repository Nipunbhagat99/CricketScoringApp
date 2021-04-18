package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_team_one.*
import kotlinx.android.synthetic.main.new_player_dialog.*
import kotlinx.android.synthetic.main.new_player_dialog.view.*

class TeamOneActivity : AppCompatActivity() {

    private val list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_one)

        setUpPlayerRV()

        btn_new_player.setOnClickListener {
            addNewPlayerToTeam()
        }
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

    private fun addNewPlayerToTeam(){

        val mDialogView = LayoutInflater
                .from(this)
                .inflate(R.layout.new_player_dialog,null)

        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("ADD A NEW PLAYER")

        val mAlertDialog = mBuilder.show()

        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogView.btn_add_player.setOnClickListener {
            mAlertDialog.dismiss()

            val name = mAlertDialog.et_player_name.text.toString()
            val roleList = arrayOf("Batsman", "All-rounder" , "Bowler")
            val checkedId = rg_role.checkedRadioButtonId

            val role  = roleList[checkedId]

            val newPlayer = PlayerModel(name , role)

            list.add(newPlayer)

            


        }



    }
}