package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
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

        t1_btn_new_player.setOnClickListener {
            addNewPlayerToTeam()
        }
    }

    private fun setUpPlayerRV(){
        t1_rv_add_players.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , getPlayerList())

        t1_rv_add_players.adapter = playerAdapter


        val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = t1_rv_add_players.adapter as PlayerAdapter
                adapter.removeAt(viewHolder.adapterPosition)



            }
        }

        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(t1_rv_add_players)
    }

    private fun getPlayerList(): ArrayList<PlayerModel>{


        var player = PlayerModel(
                "Nipun",
                    "BATSMAN"
        )
        list.add(player)

        player = PlayerModel("Chhibber" , "ALL-ROUNDER")
        list.add(player)
        player = PlayerModel("Chhibber" , "ALL-ROUNDER")
        list.add(player)
        player = PlayerModel("Chhibber" , "ALL-ROUNDER")
        list.add(player)

        return list

    }

    private fun addNewPlayerToTeam(){

        val mDialogView = LayoutInflater
                .from(this@TeamOneActivity)
                .inflate(R.layout.new_player_dialog,null)

        val mBuilder = AlertDialog.Builder(this@TeamOneActivity)
                .setView(mDialogView)
                .setTitle("ADD A NEW PLAYER")




        val mAlertDialog = mBuilder.show()

        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mDialogView.btn_add_player.setOnClickListener {

            if (mAlertDialog.et_player_name.text.isNullOrEmpty() || mAlertDialog.et_player_name.text.trim().isEmpty()) {
                Toast.makeText(this, "Please enter a name.." , Toast.LENGTH_SHORT).show()
            } else {

            var role = ""
            val name = mAlertDialog.et_player_name.text.toString()
            if (mAlertDialog.radio_batsman.isChecked) {
                role = "BATSMAN"
            } else if (mAlertDialog.radio_all_rounder.isChecked) {
                role = "ALL-ROUNDER"
            } else if (mAlertDialog.radio_bowler.isChecked) {
                role = "BOWLER"
            }
            mAlertDialog.dismiss()

            val newPlayer = PlayerModel(name, role)

            list.add(newPlayer)
            t1_rv_add_players.adapter!!.notifyDataSetChanged()


        }

        }



    }
}