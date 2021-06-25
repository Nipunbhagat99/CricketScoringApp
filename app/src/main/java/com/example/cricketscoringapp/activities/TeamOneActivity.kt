package com.example.cricketscoringapp.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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
import java.lang.reflect.Type

class TeamOneActivity : AppCompatActivity() {

    private var list = ArrayList<PlayerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_one)

        loadData()

        setUpPlayerRV()

        t1_btn_new_player.setOnClickListener {
            addNewPlayerToTeam()
        }

        t1_btn_submit.setOnClickListener {
            saveData()

        }
    }

    private fun setUpPlayerRV(){
        t1_rv_add_players.layoutManager = LinearLayoutManager(this)

        val playerAdapter = PlayerAdapter(this , list)

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


    private fun saveData(){

        val team1Name = et_1_team_name.text.toString()
        val sharedPreferences : SharedPreferences= getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("team_1_name", team1Name.toUpperCase())
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("team_1" , json)
        editor.commit()
        Toast.makeText(this, "Team 1 has been added" , Toast.LENGTH_SHORT).show()
        finish()
    }

    private  fun loadData(){
        val empty :String= ""
        val sharedPreferences : SharedPreferences= getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
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

    private fun addNewPlayerToTeam(){

        val mDialogView = LayoutInflater
                .from(this@TeamOneActivity)
                .inflate(R.layout.new_player_dialog,null)

        val mBuilder = AlertDialog.Builder(this@TeamOneActivity)
                .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
            t1_rv_add_players.adapter!!.notifyDataSetChanged()


        }

        }



    }
}