package com.example.cricketscoringapp.fragments

import android.app.AlertDialog

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ChooseBowlerAdapter
import com.example.cricketscoringapp.models.BatsmanModel
import com.example.cricketscoringapp.models.BowlerModel
import com.example.cricketscoringapp.models.OverModel
import com.example.cricketscoringapp.models.PlayerModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.choose_bowler_dialog.*

import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.view.*
import kotlin.math.floor


class ScoreFragment : Fragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeScore()
        getBatsmanOnStrike()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScore()

        btn_dot.setOnClickListener {
            addRuns(0)
        }

        btn_one.setOnClickListener {
            addRuns(1)
        }

        btn_two.setOnClickListener {
            addRuns(2)
        }

        btn_three.setOnClickListener {
            addRuns(3)
        }
        btn_four.setOnClickListener {
            addRuns(4)
        }
        btn_six.setOnClickListener {
            addRuns(6)
        }

        btn_wide.setOnClickListener {
            wide()
        }



    }


    private fun setupScore(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val firstBatting = sharedPreferences.getInt("first_batting", 1)


        val teamName = if(firstBatting==1 ){
            sharedPreferences.getString("team_1_name", "").toString()
        } else{
            sharedPreferences.getString("team_2_name", "").toString()
        }
        val score = sharedPreferences.getInt("score", 0)
        val balls = sharedPreferences.getInt("balls", 0)
        val wickets = sharedPreferences.getInt("wickets", 0)
        val crr = sharedPreferences.getString("crr", "0.00")?.toDouble()
        val overs = balls/6
        val remBalls = balls%6
        val innings = sharedPreferences.getInt("innings", 0)
        tv_score_team_name.text = teamName.toUpperCase()
        tv_runs_wickets.text = "${score}-${wickets}"
        tv_overs.text = "(${overs}.${remBalls})"
        if(innings == 1) {
            ll_score_crr.visibility = View.INVISIBLE
            tv_req.text = "CRR"
            tv_score_req.text = "$crr"
            tv_target.visibility = View.GONE
        }



        setupBatsmen()
        setupBowler()

    }

    private fun setupBatsmen(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        val batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        tv_score_batsman1_name.text = batsman1.name
        tv_score_batsman1_runs.text = batsman1.runs.toString()
        tv_score_batsman1_balls.text = batsman1.ballsFaced.toString()
        tv_score_batsman1_fours.text = batsman1.fours.toString()
        tv_score_batsman1_sixes.text = batsman1.sixes.toString()
        tv_score_batsman1_strike_rate.text = batsman1.strikeRate.toString()

        val batsman2JSON = sharedPreferences.getString("batsman2","")
        val batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        tv_score_batsman2_name.text = batsman2.name
        tv_score_batsman2_runs.text = batsman2.runs.toString()
        tv_score_batsman2_balls.text = batsman2.ballsFaced.toString()
        tv_score_batsman2_fours.text = batsman2.fours.toString()
        tv_score_batsman2_sixes.text = batsman2.sixes.toString()
        tv_score_batsman2_strike_rate.text = batsman2.strikeRate.toString()

    }



    private fun setupBowler(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        tv_score_bowler_name.text = bowler.name
        val overs = bowler.balls/6
        val balls = bowler.balls%6
        tv_score_bowler_overs.text = "$overs.$balls"
        tv_score_bowler_maiden_overs.text = bowler.maidens.toString()
        tv_score_bowler_runs.text = bowler.runsconceded.toString()
        tv_score_bowler_wickets.text = bowler.wickets.toString()
        tv_score_bowler_economy_rate.text = bowler.eco.toString()

    }

    private fun initializeScore(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("balls", 0)
        editor.putInt("score", 0)
        editor.putInt("wickets" , 0)
        editor.putString("crr", "0.00")
        editor.putInt("innings" , 1)
        editor.commit()
    }

    private fun getBatsmanOnStrike(){

        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        val batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        val batsman2JSON = sharedPreferences.getString("batsman2","")
        val batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)


        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.select_batsman_on_strike_dialog,null)
        mDialogView.player_1.text = batsman1.name
        mDialogView.player_2.text = batsman2.name

        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mAlertDialog.player_1.setOnClickListener {
            playerStrike(1)
            mAlertDialog.dismiss()
        }

        mAlertDialog.player_2.setOnClickListener {
            playerStrike(2)
            mAlertDialog.dismiss()
        }


    }
    //function to set strike of batsman
    private fun playerStrike(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)

            val batsmanJSON = sharedPreferences.getString("batsman$x", "")
            val batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
            batsman.strike = true
            batsman.name = batsman.name + "*"
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            val batsmanGson = Gson().toJson(batsman)
            editor.putString("batsman$x" , batsmanGson)
            editor.putInt("batsman_on_strike", x)
            editor.commit()
            setupScore()




    }



    private fun addRuns(x : Int){
        Log.i("add runs ", "add runs $x")
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var score = sharedPreferences.getInt("score", 0)
        score += x
        var balls  = sharedPreferences.getInt("balls", 0) + 1
        val overs : Int = balls/6
        val remBalls : Int = balls%6
        val fraction : Double = remBalls.toDouble()/6.00
        val totalOvers : Double = overs.toDouble() + fraction
        val newCrr : Double= score.toDouble() / totalOvers
        val solution = floor(newCrr * 100) / 100
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("crr", "$solution")
        editor.putInt("balls", balls)
        editor.putInt("score", score)
        editor.commit()

        updateBatsmen(x)
        updateBowler(x)
        if(remBalls == 1){
            addNewOver("$x", x,overs)
            nextOver()
        }
        else {
            updateOversList("$x" ,x)
        }




//        addRunsScorecardList(score, balls , )



        setupScore()
    }

    private fun updateOversList(ball : String, runs : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var list  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        val size = list.size
        var over = list[size-1]
        over.balls.add(ball)
        over.runs = over.runs + runs

        list[size-1] = over

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        overListJson = Gson().toJson(list)
        editor.putString("over_list", overListJson)
        editor.commit()


    }

    private fun addNewOver(ball : String, runs : Int, overs: Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        val list  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        var over = OverModel(overs+1,0, ArrayList<String>())
        over.runs = over.runs + runs
        over.balls.add(ball)
        list.add(over)


        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        overListJson = Gson().toJson(list)
        editor.putString("over_list", overListJson)
        editor.commit()


    }

    // Update Batsman on add runs
    private fun updateBatsmen(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsman.ballsFaced++
        batsman.runs += x
        if(x==4)
            batsman.fours++
        else if(x==6)
            batsman.sixes++

        batsman.strikeRate = batsman.runs.toDouble()/batsman.ballsFaced.toDouble()
        batsman.strikeRate *= 100
        batsman.strikeRate = floor(batsman.strikeRate * 100) / 100


        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val batsmanGson = Gson().toJson(batsman)
        editor.putString("batsman$batsmanNumber" , batsmanGson)
        editor.commit()

        if(x%2 != 0)
            strikeChange()

    }
    //update bowler on add runs
    private fun updateBowler(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        bowler.runsconceded += x
        bowler.balls++
        var overs = (bowler.balls/6).toDouble()
        var balls = (bowler.balls%6).toDouble()
        balls /= 6
        overs += balls
        bowler.eco = bowler.runsconceded.toDouble()/overs
        bowler.eco = floor(bowler.eco * 100) / 100
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)
        editor.commit()


    }

    private fun strikeChange(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsman.name = batsman.name?.dropLast(1)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val batsmanGson = Gson().toJson(batsman)
        editor.putString("batsman$batsmanNumber" , batsmanGson)

        batsmanNumber = if(batsmanNumber ==1){
            2
        } else{
            1
        }
        val batsmanNewJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsmanNew : BatsmanModel = Gson().fromJson(batsmanNewJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsmanNew.name = batsmanNew.name + "*"
        val batsmanNewGson = Gson().toJson(batsmanNew)
        editor.putString("batsman$batsmanNumber" , batsmanNewGson)
        editor.putInt("batsman_on_strike", batsmanNumber)
        editor.commit()

    }

    private fun nextOver(){

        val sharedPreferences : SharedPreferences= this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val firstBatting = sharedPreferences.getInt("first_batting", 0)
        val team = if(firstBatting== 1){
            2
        }else{
            1
        }
        val bowlerListJSON = sharedPreferences.getString("team_$team", "")

        var bowlerList : ArrayList<PlayerModel> = Gson().fromJson(bowlerListJSON, object: TypeToken<ArrayList<PlayerModel>>(){}.type)


        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.choose_bowler_dialog,null)

        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        rv_choose_next_bowler.layoutManager = LinearLayoutManager(this.activity)


        val chooseBowlerAdapter = ChooseBowlerAdapter(this.activity!! ,bowlerList )

        rv_choose_next_bowler.adapter = chooseBowlerAdapter

        mAlertDialog.btn_submit_bowler.setOnClickListener {
            
        }

    }


    private fun wide(){

    }






}