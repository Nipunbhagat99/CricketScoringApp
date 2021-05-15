package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_team_two.*
import kotlinx.android.synthetic.main.new_player_dialog.*
import kotlinx.android.synthetic.main.new_player_dialog.view.*

class TeamTwoActivity : AppCompatActivity() {
    private var list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_two)

        loadData()

        setUpPlayerRV()

        t2_btn_new_player.setOnClickListener {
            addNewPlayerToTeam()
        }

        t2_btn_submit.setOnClickListener {
            saveData()

        }
    }

    private fun setUpPlayerRV(){
        t2_rv_add_players.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , list)

        t2_rv_add_players.adapter = playerAdapter


        val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = t2_rv_add_players.adapter as PlayerAdapter
                adapter.removeAt(viewHolder.adapterPosition)



            }
        }

        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(t2_rv_add_players)
    }

    private fun saveData(){

        val team2Name = et_2_team_name.text.toString()
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("team_2_name", team2Name)
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("team_2" , json)
        editor.commit()
        Toast.makeText(this, "Team 2 has been added" , Toast.LENGTH_SHORT).show()
        finish()
    }

    private  fun loadData(){
        val empty :String= ""
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team2Name : String? = sharedPreferences.getString("team_2_name" , "").toString()
        if(team2Name == null){
            et_2_team_name.setText(empty)
        }
        else {
            et_2_team_name.setText(team2Name)
        }

        val json = sharedPreferences.getString("team_2" , emptyList<PlayerModel>().toString())
        list = Gson().fromJson(json, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        if(list == null ){
            list = ArrayList<PlayerModel>()
        }

    }



    private fun addNewPlayerToTeam(){

        val mDialogView = LayoutInflater
                .from(this@TeamTwoActivity)
                .inflate(R.layout.new_player_dialog,null)

        val mBuilder = AlertDialog.Builder(this@TeamTwoActivity)
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
                else if (mAlertDialog.radio_wk.isChecked) {
                    role = "WICKET-KEEPER"
                }
                mAlertDialog.dismiss()

                val newPlayer = PlayerModel(name, role,false)

                list.add(newPlayer)
                t2_rv_add_players.adapter!!.notifyDataSetChanged()


            }

        }



    }
}