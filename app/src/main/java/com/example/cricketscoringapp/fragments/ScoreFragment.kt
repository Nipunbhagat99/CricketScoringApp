package com.example.cricketscoringapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ChooseBowlerAdapter
import com.example.cricketscoringapp.models.*
import com.example.cricketscoringapp.utils.RefreshInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.choose_bowler_dialog.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.fragment_scorecard.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.view.*
import kotlin.math.floor


class ScoreFragment : Fragment(){
    private lateinit var ri : RefreshInterface
    private var wide = 0
    private var byes = 0
    private var legByes = 0
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
                if(wide==1){
                    addWideRuns(0)
                }
                else{
                    addRuns(0)
                }

        }

        btn_one.setOnClickListener {
            if(wide==1){
                addWideRuns(1)
            }
            else if(byes==1){
                addByesRuns(1)
            }
            else if(legByes==1){
                addByesRuns(1)
            }
            else{
                addRuns(1)
            }
        }

        btn_two.setOnClickListener {
            if(wide==1){
                addWideRuns(2)
            }
            else if(byes==1){
                addByesRuns(2)
            }
            else if(legByes==1){
                addByesRuns(2)
            }
            else{
                addRuns(2)
            }
        }

        btn_three.setOnClickListener {
            if(wide==1){
                addWideRuns(3)
            }
            else if(byes==1){
                addByesRuns(3)
            }
            else if(legByes==1){
                addByesRuns(3)
            }
            else{
                addRuns(3)
            }
        }
        btn_four.setOnClickListener {
            if(wide==1){
                addWideRuns(4)
            }
            else if(byes==1){
                addByesRuns(4)
            }
            else if(legByes==1){
                addByesRuns(4)
            }
            else{
                addRuns(4)
            }
        }
        btn_six.setOnClickListener {
            if(wide==1){
                addWideRuns(6)
            }
            else if(byes==1){
                addByesRuns(6)
            }
            else if(legByes==1){
                addByesRuns(6)
            }
            else{
                addRuns(6)
            }
        }

        btn_wide.setOnClickListener {
            wide()
        }

        btn_byes.setOnClickListener {
            byes()
        }

        btn_leg_byes.setOnClickListener {
            legByes()
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
        var crr = 0.00
        val score = sharedPreferences.getInt("score", 0)
        val balls = sharedPreferences.getInt("balls", 0)
        val wickets = sharedPreferences.getInt("wickets", 0)
        var nCrr = sharedPreferences.getString("crr", "0.00")
        if(nCrr!="Inf"){
            crr= nCrr?.toDouble()!!
        }
        val overs = balls/6
        val remBalls = balls%6
        val innings = sharedPreferences.getInt("innings", 0)
        tv_score_team_name.text = teamName.toUpperCase()
        tv_runs_wickets.text = "${score}-${wickets}"
        tv_overs.text = "(${overs}.${remBalls})"
        if(innings == 1) {
            ll_score_crr.visibility = View.INVISIBLE
            tv_req.text = "CRR"
            if(nCrr =="Inf"){
                tv_score_req.text = nCrr
            }
            else{
                tv_score_req.text = "$crr"
            }

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
        updateScorecardList(score,balls)
        if(balls==1 && score-x==0){
            addNewOver(overs)
            updateOversList("$x", x)
        }
        else{
            if(remBalls==0){
                updateOversList("$x",x)
                addNewOver(overs)
                nextOver()
            }
            else{
                updateOversList("$x",x)
            }
        }
        setupScore()
    }

    private fun updateOversList(ball : String, runs : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var list  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        var size = list.size
        size--
        list[size].balls.add(ball)
        list[size].runs += runs


        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        overListJson = Gson().toJson(list)
        editor.putString("over_list", overListJson)
        editor.commit()


    }

    private fun addNewOver(overs: Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        val list  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        var over = OverModel(overs+1,0, ArrayList<String>())
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
        var eco = bowler.runsconceded.toDouble()/overs
        eco = floor(eco * 100) / 100
        bowler.eco = eco.toString()
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
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
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

        mAlertDialog.rv_choose_next_bowler.layoutManager = LinearLayoutManager(this.activity)


        val chooseBowlerAdapter = ChooseBowlerAdapter(this.activity!! ,bowlerList )

        mAlertDialog.rv_choose_next_bowler.adapter = chooseBowlerAdapter

        mAlertDialog.btn_submit_bowler.setOnClickListener {
             val bowlerChoice = chooseBowlerAdapter.goNext()
            if(bowlerChoice == bowler.name){

                Toast.makeText(this.activity, "Please choose a different bowler", Toast.LENGTH_SHORT).show()
            }
            else{
                val bowlerHashJSON = sharedPreferences.getString("bowler_hash", "")
                val bowlerHash : HashMap<String, Int> = Gson().fromJson(bowlerHashJSON, object: TypeToken<HashMap<String,Int>>(){}.type)
                if(bowlerHash[bowlerChoice] != null){
                    val newBowler = getBowlerFromScorecard(bowlerHash[bowlerChoice]!!)
                    val editor = sharedPreferences.edit()
                    val json = Gson().toJson(newBowler)
                    editor.putString("bowler",json)
                    editor.commit()
                }
                else{
                    val firstBatting = sharedPreferences.getInt("first_batting", 0)
                    val teamNo = if(firstBatting==1){
                        2
                    }
                    else{
                        1
                    }
                    val teamName = sharedPreferences.getString("team_${teamNo}_name", "")
                    bowlerHash[bowlerChoice] = bowlerHash.size
                    val newBowler = BowlerModel(bowlerChoice,teamName,0,0,"0.00",0,0 )
                    addNewBowlerToScorecard(newBowler)
                    val editor = sharedPreferences.edit()
                    val json = Gson().toJson(newBowler)
                    editor.putString("bowler",json)
                    editor.commit()

                }
                setupScore()
                mAlertDialog.dismiss()
            }


        }

    }




    private fun getBowlerFromScorecard(x : Int): BowlerModel{
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].bowlerIndex = x
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)
        editor.commit()
        ri = activity as RefreshInterface
        ri.refreshAdapter()
        return list[innings].bowlerList[x]


    }


    private fun addNewBowlerToScorecard(newBowler : BowlerModel){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].bowlerList.add(newBowler)
        list[innings].bowlerIndex = list[innings].bowlerList.size -1
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)
        editor.commit()
        ri = activity as RefreshInterface
        ri.refreshAdapter()
    }

    private fun updateScorecardList(score: Int ,  balls : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].runs = score
        list[innings].balls = balls

        //updating batsman 1
        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        var batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        var index = list[innings].batsman1Index
        list[innings].batsmenList[index] = batsman1

        //updating batsman 2
        index = list[innings].batsman2Index
        val batsman2JSON = sharedPreferences.getString("batsman2", "")
        var batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        val index2 = list[innings].batsman2Index
        list[innings].batsmenList[index2] = batsman2

        //updating bowler
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        val bowlerIndex = list[innings].bowlerIndex
        list[innings].bowlerList[bowlerIndex] = bowler

        val editor = sharedPreferences.edit()
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)
        editor.commit()
        ri = activity as RefreshInterface
        ri.refreshAdapter()

    }


    private fun wide(){
        disableButton(btn_no_ball)
        disableButton(btn_wide)
        disableButton(btn_leg_byes)
        disableButton(btn_byes)
        disableButton(btn_overthrow)
        disableButton(btn_bowled)
        disableButton(btn_catch)
        disableButton(btn_lbw)
        disableButton(btn_run_out)
        disableButton(btn_stump)
        wide=1

    }

    private fun disableButton(b : Button){
        b.isEnabled = false
        b.isClickable = false
        b.setBackgroundColor(Color.parseColor("#6e6e6e"))
    }

    private fun enableButton(b : Button){
        b.isEnabled = true
        b.isClickable = true
        b.setBackgroundColor(Color.parseColor("#123456"))
    }


    private fun addWideRuns(x: Int){

        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)

        //update score & crr
        var score = sharedPreferences.getInt("score", 0)
        score += x
        score++
        var balls  = sharedPreferences.getInt("balls", 0)

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        if(balls==0){
            val newCrr = "Inf"
            editor.putString("crr", newCrr)
        }
        else{
            val overs : Int = balls/6
            val remBalls : Int = balls%6

            val fraction : Double = remBalls.toDouble()/6.00
            val totalOvers : Double = overs.toDouble() + fraction
            val newCrr : Double= score.toDouble() / totalOvers
            val solution = floor(newCrr * 100) / 100
            editor.putString("crr", "$solution")
        }

        editor.putInt("score", score)


        //update bowler
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        bowler.runsconceded += x
        bowler.runsconceded++
        var oversBowler = (bowler.balls/6).toDouble()
        var ballsBowler = (bowler.balls%6).toDouble()
        ballsBowler /= 6
        oversBowler += ballsBowler
        if(balls ==0){
            bowler.eco = "Inf"
        }
        else{
            var eco = bowler.runsconceded.toDouble()/oversBowler
            eco = floor(eco * 100) / 100
            bowler.eco = eco.toString()

        }
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)

        //update scorecard
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].runs = score
        val bowlerIndex = list[innings].bowlerIndex
        list[innings].bowlerList[bowlerIndex] = bowler
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)

        //update overs
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)

        if(balls==0){
            var over = OverModel(1,0, ArrayList<String>())
            var ball = "Wd"
            if(x!=0){
                ball += "+$x"
            }
            over.balls.add(ball)
            over.runs += x+1
            oversList.add(over)


        }
        else{
             var size = oversList.size
            size--
            var ball = "Wd"
            if(x!=0){
                ball += "+$x"
            }
            oversList[size].balls.add(ball)
            oversList[size].runs += x+1

        }

        overListJson = Gson().toJson(oversList)
        editor.putString("over_list", overListJson)
        editor.commit()

        ri = activity as RefreshInterface
        ri.refreshAdapter()

        setupScore()


        wide =0

        enableButton(btn_no_ball)
        enableButton(btn_wide)
        enableButton(btn_leg_byes)
        enableButton(btn_byes)
        enableButton(btn_overthrow)
        enableButton(btn_bowled)
        enableButton(btn_catch)
        enableButton(btn_lbw)
        enableButton(btn_run_out)
        enableButton(btn_stump)
    }

    private fun byes(){
        disableButton(btn_no_ball)
        disableButton(btn_wide)
        disableButton(btn_leg_byes)
        disableButton(btn_byes)
        disableButton(btn_overthrow)
        disableButton(btn_bowled)
        disableButton(btn_catch)
        disableButton(btn_lbw)
        disableButton(btn_run_out)
        disableButton(btn_stump)
        disableButton(btn_dot)
        byes=1
    }

    private fun legByes(){
        disableButton(btn_no_ball)
        disableButton(btn_wide)
        disableButton(btn_leg_byes)
        disableButton(btn_byes)
        disableButton(btn_overthrow)
        disableButton(btn_bowled)
        disableButton(btn_catch)
        disableButton(btn_lbw)
        disableButton(btn_run_out)
        disableButton(btn_stump)
        disableButton(btn_dot)
        legByes=1
    }

    private fun addByesRuns(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)

        //update score & crr
        var score = sharedPreferences.getInt("score", 0)
        score += x
        var balls  = sharedPreferences.getInt("balls", 0)
        balls++

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        if(balls==0){
            val newCrr = "Inf"
            editor.putString("crr", newCrr)
        }
        else{
            val overs : Int = balls/6
            val remBalls : Int = balls%6

            val fraction : Double = remBalls.toDouble()/6.00
            val totalOvers : Double = overs.toDouble() + fraction
            val newCrr : Double= score.toDouble() / totalOvers
            val solution = floor(newCrr * 100) / 100
            editor.putString("crr", "$solution")
        }

        editor.putInt("score", score)
        editor.putInt("balls", balls)


        //update bowler
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        bowler.runsconceded += x
        bowler.balls++
        var oversBowler = (bowler.balls/6).toDouble()
        var ballsBowler = (bowler.balls%6).toDouble()
        ballsBowler /= 6
        oversBowler += ballsBowler
        if(balls ==0){
            bowler.eco = "Inf"
        }
        else{
            var eco = bowler.runsconceded.toDouble()/oversBowler
            eco = floor(eco * 100) / 100
            bowler.eco = eco.toString()

        }
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)

        //update scorecard
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].runs = score
        list[innings].balls++
        val bowlerIndex = list[innings].bowlerIndex
        list[innings].bowlerList[bowlerIndex] = bowler
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)

        //update overs
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        var ball = ""
        if(oversList[0].balls.isEmpty()){
            var over = OverModel(1,0, ArrayList<String>())
            if(byes==1){
                ball = "B"
            }
            else if(legByes==1){
                ball = "LB"
            }

            ball += "$x"

            over.balls.add(ball)
            over.runs += x
            oversList.add(over)


        }
        else{
            var size = oversList.size
            size--

            if(byes==1){
                ball = "B"
            }
            else if(legByes==1){
                ball = "LB"
            }


            ball += "$x"

            oversList[size].balls.add(ball)
            oversList[size].runs += x

        }

        overListJson = Gson().toJson(oversList)
        editor.putString("over_list", overListJson)
        editor.commit()

        ri = activity as RefreshInterface
        ri.refreshAdapter()

        setupScore()
        legByes=0
        byes =0
        enableButton(btn_dot)
        enableButton(btn_no_ball)
        enableButton(btn_wide)
        enableButton(btn_leg_byes)
        enableButton(btn_byes)
        enableButton(btn_overthrow)
        enableButton(btn_bowled)
        enableButton(btn_catch)
        enableButton(btn_lbw)
        enableButton(btn_run_out)
        enableButton(btn_stump)
    }
    
}