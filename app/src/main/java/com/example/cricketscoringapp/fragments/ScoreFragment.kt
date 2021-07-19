package com.example.cricketscoringapp.fragments
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.example.cricketscoringapp.activities.ChooseBatsmenActivity
import com.example.cricketscoringapp.activities.ChooseBowlerActivity
import com.example.cricketscoringapp.adapters.ChooseBowlerAdapter
import com.example.cricketscoringapp.adapters.ChoosePlayerAdapter
import com.example.cricketscoringapp.database.DatabaseHandler
import com.example.cricketscoringapp.models.*
import com.example.cricketscoringapp.utils.RefreshInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.choose_bowler_dialog.*
import kotlinx.android.synthetic.main.choose_next_batsman.*
import kotlinx.android.synthetic.main.choose_next_batsman.view.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.innings_change_dialog.*
import kotlinx.android.synthetic.main.innings_change_dialog.view.*
import kotlinx.android.synthetic.main.result_view.view.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.*
import kotlinx.android.synthetic.main.select_batsman_on_strike_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.floor


class ScoreFragment : Fragment(){
    private lateinit var ri : RefreshInterface
    private var wide = 0
    private var byes = 0
    private var legByes = 0
    private var noBall = 0
    private var bowled = 0
    private var lbw = 0
    private var catch = 0
    private var runout = 0
    private var wicket =0
    private var stump = 0
    private var players : Int =0
    private  var ballsTotal : Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeScore()
        getBatsmanOnStrike()

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val overs = sharedPreferences.getInt("overs", 1)
        val innings = sharedPreferences.getInt("innings" , 1)
        val firstBatting = sharedPreferences.getInt("first_batting", 1)
        ballsTotal = overs*6

        var teamno = if(innings ==1){
            if(firstBatting ==1){
                1
            } else
                2
        } else{
            if(firstBatting==1){
                2
            } else
                1
        }

        val teamJson =  sharedPreferences.getString("team_$teamno" , "")
        val team : ArrayList<PlayerModel> =  Gson().fromJson(teamJson, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        players = team.size

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
                else if(noBall==1){
                    addWideRuns(0)
                }
                else if(catch==1 || runout==1){
                    wicketAddRuns(0)
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
            else if(noBall==1){
                addWideRuns(1)
            }
            else if(legByes==1){
                addByesRuns(1)
            }
            else if(catch==1 || runout==1){
                wicketAddRuns(1)
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
            else if(noBall==1){
                addWideRuns(2)
            }
            else if(legByes==1){
                addByesRuns(2)
            }
            else if(catch==1 || runout==1){
                wicketAddRuns(2)
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
            else if(noBall==1){
                addWideRuns(3)
            }
            else if(legByes==1){
                addByesRuns(3)
            }
            else if(catch==1 || runout==1){
                wicketAddRuns(3)
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
            else if(noBall==1){
                addWideRuns(4)
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
            else if(noBall==1){
                addWideRuns(6)
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

        btn_no_ball.setOnClickListener {
            noBall()
        }

        btn_bowled.setOnClickListener {
            bowled =1
            wicket()

        }

        btn_lbw.setOnClickListener {
            lbw=1
            wicket()

        }

        btn_catch.setOnClickListener {
            catch=1
            wicket=1
            wicketRun()

        }

        btn_run_out.setOnClickListener {
            runout=1
            wicket=1
            wicketRun()

        }

        btn_stump.setOnClickListener {
            stump=1
            catch()

        }



    }


    private fun setupScore(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val firstBatting = sharedPreferences.getInt("first_batting", 1)
        val innings = sharedPreferences.getInt("innings",0)
        var teamName =" "
        if(innings==1) {
            teamName = if (firstBatting == 1) {
                sharedPreferences.getString("team_1_name", "").toString()
            } else {
                sharedPreferences.getString("team_2_name", "").toString()
            }
        }
        else{
            teamName = if (firstBatting == 1) {
                sharedPreferences.getString("team_2_name", "").toString()
            } else {
                sharedPreferences.getString("team_1_name", "").toString()
            }
        }
        var crr = 0.00
        val score = sharedPreferences.getInt("score", 0)
        val balls = sharedPreferences.getInt("balls", 0)
        val wickets = sharedPreferences.getInt("wickets", 0)
        val nCrr = sharedPreferences.getString("crr", "0.00")
        if(nCrr!="Inf"){
            crr= nCrr?.toDouble()!!
        }
        val overs = balls/6
        val remBalls = balls%6

        tv_score_team_name.text = teamName.toUpperCase(Locale.getDefault())
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
        else{
            val target = sharedPreferences.getInt("target", 0)
            val overs = sharedPreferences.getInt("overs" ,0)
            val ballsRemaining  = overs*6 - balls
            tv_req.text = "RRR"
            tv_crr.text = "CRR"
            if(nCrr =="Inf"){
                tv_score_crr.text = nCrr
            }
            else{
                tv_score_crr.text = "$crr"
            }
            val overCalc : Double = ballsRemaining.toDouble()/6.00
            var rrr : Double = (target-score).toDouble()/overCalc
            rrr = floor(rrr * 100) / 100
            tv_score_req.text = rrr.toString()
            tv_target.text = "$teamName needs ${target-score} in $ballsRemaining"

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
        tv_score_batsman1_strike_rate.text = batsman1.strikeRate

        val batsman2JSON = sharedPreferences.getString("batsman2","")
        val batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        tv_score_batsman2_name.text = batsman2.name
        tv_score_batsman2_runs.text = batsman2.runs.toString()
        tv_score_batsman2_balls.text = batsman2.ballsFaced.toString()
        tv_score_batsman2_fours.text = batsman2.fours.toString()
        tv_score_batsman2_sixes.text = batsman2.sixes.toString()
        tv_score_batsman2_strike_rate.text = batsman2.strikeRate

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
        editor.commit()

    }

    private fun  getBatsmanOnStrike(){

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
        if(runout==1){
            mDialogView.select_batsman_heading.text = "Choose batsman who got run out"
        }


        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)




        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mAlertDialog.player_1.setOnClickListener {

            if(runout==1)
                runOut(1)
            else
                playerStrike(1)

            mAlertDialog.dismiss()
        }

        mAlertDialog.player_2.setOnClickListener {
            if(runout==1)
                runOut(2)
            else
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

        if(wicket==1){
            updatestrikeScorecard()
            wicket=0
        }



    }



    private fun addRuns(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var score = sharedPreferences.getInt("score", 0)
        var target = sharedPreferences.getInt("target", 0)
        score += x
        var balls  = sharedPreferences.getInt("balls", 0) + 1
        val overs : Int = balls/6
        val remBalls : Int = balls%6

        val fraction : Double = remBalls.toDouble()/6.00
        val totalOvers : Double = overs.toDouble() + fraction
        val newCrr : Double= score.toDouble() / totalOvers
        val solution = floor(newCrr * 100) / 100
        val innings = sharedPreferences.getInt("innings" ,0)
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
       else if(balls == ballsTotal && innings ==1){
            updateOversList("$x", x)
            removeStrike()
            inningsOver()
        }
        else if(balls == ballsTotal && innings ==2){
            updateOversList("$x", x)
            removeStrike()
            if(score>=target)
                matchOver(3)
            else if(score == target-1){
                matchOver(4)
            }
            else
                matchOver(1)
        }
        else if(score>= target && innings ==2){
            updateOversList("$x", x)
            removeStrike()
            matchOver(2)
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


    private fun removeStrike(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsman.name = batsman.name?.dropLast(1)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val batsmanGson = Gson().toJson(batsman)
        editor.putString("batsman$batsmanNumber" , batsmanGson)
        editor.commit()
        setupScore()

        updatestrikeScorecard()

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
        var strikeRate = batsman.runs.toDouble()/batsman.ballsFaced.toDouble()
        strikeRate *=100
        strikeRate = floor(strikeRate * 100) / 100
        batsman.strikeRate = strikeRate.toString()


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

        updatestrikeScorecard()


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
                strikeChange()
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

        //update batsman
        if(noBall==1){
            val batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
            val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
            var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
            batsman.runs += x

            if(balls==0){
                batsman.strikeRate = "Inf"
            }
            else {
                var strikeRate = batsman.runs.toDouble() / batsman.ballsFaced.toDouble()
                strikeRate *= 100
                strikeRate = floor(strikeRate * 100) / 100
                batsman.strikeRate = strikeRate.toString()
            }

            val batsmanGson = Gson().toJson(batsman)
            editor.putString("batsman$batsmanNumber" , batsmanGson)
            editor.commit()
        }

        if(x%2 != 0)
            strikeChange()

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

        val bowlerIndex = list[innings].bowlerIndex
        list[innings].bowlerList[bowlerIndex] = bowler
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)

        //update overs
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        var ball = ""
        if(balls==0){
            var over = OverModel(1,0, ArrayList<String>())
            if(wide==1){
                ball = "Wd"
            }
            else if(noBall==1){
                ball = "NB"
            }
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
            if(wide==1){
                ball = "Wd"
            }
            else if(noBall==1){
                ball = "NB"
            }
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
        val target = sharedPreferences.getInt("target" , 0)

        setupScore()

        if(score>=target && innings ==2){
            matchOver(2)
        }
        wide =0
        noBall=0

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


        //update batsman
        val batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsman.ballsFaced++
        var strikeRate = batsman.runs.toDouble()/batsman.ballsFaced.toDouble()
        strikeRate *=100
        strikeRate = floor(strikeRate * 100) / 100
        batsman.strikeRate = strikeRate.toString()

        val batsmanGson = Gson().toJson(batsman)
        editor.putString("batsman$batsmanNumber" , batsmanGson)
        editor.commit()
        if(x%2 != 0)
            strikeChange()



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

        if(balls == ballsTotal){
            removeStrike()
            inningsOver()
        }
        else if(balls%6==0){
            addNewOver(oversList.size)
            nextOver()
        }

        ri = activity as RefreshInterface
        ri.refreshAdapter()

        setupScore()
        val target = sharedPreferences.getInt("target",0)

        if(score>= target && innings ==2)
            matchOver(2)
        else if(balls == ballsTotal && innings ==2){
            if(score>=target)
                matchOver(3)
            else if(score == target-1){
                matchOver(4)
            }
            else
                matchOver(1)
        }

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

    private fun noBall(){
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
        noBall=1
    }



    private fun wicket(playerName : String  = "", playerNo : Int =1){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val remainingBatsmen = sharedPreferences.getString("remainingBatsmen", emptyList<PlayerModel>().toString())
        val firstBatting = sharedPreferences.getInt("first_batting", 1)
        val innings = sharedPreferences.getInt("innings", 0)
        val wickets = sharedPreferences.getInt("wickets" ,0)

        var balls = if(catch ==1 || runout ==1)
            sharedPreferences.getInt("balls", 1)
        else
            sharedPreferences.getInt("balls", 1) +1
        val teamName = sharedPreferences.getString("team_${firstBatting}_name" , "lol").toString()
        val list : ArrayList<PlayerModel> = Gson().fromJson(remainingBatsmen, object: TypeToken<ArrayList<PlayerModel>>(){}.type)


        if((balls == ballsTotal || list.size==0) &&  innings==1){

            if(catch==1 || runout==1){
                inningsChangeWicket(playerName , playerNo)
            }
            else if(stump ==1){
                inningsChangeWicket(playerName)
            }
            else{
                inningsChangeWicket()
            }
        }
        else if((balls == ballsTotal || list.size==0) &&  innings==2){
            if(stump ==1 || bowled ==1 || lbw ==1){
                sharedPreferences.edit().putInt("balls" , balls).commit()

            }
            sharedPreferences.edit().putInt("wickets" , wickets+1).commit()
                matchOver(1)
        }
        else{
            val mDialogView = LayoutInflater
                .from(this.activity)
                .inflate(R.layout.choose_next_batsman,null)

            mDialogView.rv_choose_next_batsman.layoutManager = LinearLayoutManager(this.activity)

            val chooseNextBatsmenAdapter = ChoosePlayerAdapter(this.activity!!,list)

            mDialogView.rv_choose_next_batsman.adapter = chooseNextBatsmenAdapter

            val mBuilder = AlertDialog.Builder(this.activity)
                .setView(mDialogView)
                .setCancelable(false)


            val mAlertDialog = mBuilder.show()

            mAlertDialog.btn_submit_player.setOnClickListener {


                if(catch==1 || runout==1){
                    val remainingBatsmenNew = chooseNextBatsmenAdapter.goNext()

                    if(remainingBatsmenNew.size == list.size){
                        Toast.makeText(this.activity,"Please select a player", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val batsmanName= chooseNextBatsmenAdapter.player
                        val newBatsmanModel = BatsmanModel(batsmanName, teamName , 0 , 0,"0.0", 0, 0, "Not Out" ,true )
                        updateCatchOrRunout(newBatsmanModel, remainingBatsmenNew, playerName,playerNo )
                        mAlertDialog.dismiss()
                    }
                }
                else if(stump==1){
                    val remainingBatsmenNew = chooseNextBatsmenAdapter.goNext()

                    if(remainingBatsmenNew.size == list.size){
                        Toast.makeText(this.activity,"Please select a player", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val batsmanName= chooseNextBatsmenAdapter.player
                        val newBatsmanModel = BatsmanModel(batsmanName, teamName , 0 , 0,"0.0", 0, 0, "Not Out" ,true )
                        updateBowled(newBatsmanModel ,remainingBatsmenNew , playerName)
                        mAlertDialog.dismiss()
                    }
                }
                else{
                    val remainingBatsmenNew = chooseNextBatsmenAdapter.goNext()

                    if(remainingBatsmenNew.size == list.size){
                        Toast.makeText(this.activity,"Please select a player", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val batsmanName= chooseNextBatsmenAdapter.player
                        val newBatsmanModel = BatsmanModel(batsmanName, teamName , 0 , 0,"0.0", 0, 0, "Not Out" ,true )
                        updateBowled(newBatsmanModel ,remainingBatsmenNew)
                        mAlertDialog.dismiss()
                    }
                }


            }
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }



    }

    private fun updateBowled(batsmanModel: BatsmanModel , remainingBatsmen : ArrayList<PlayerModel>, playerName: String = ""){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var balls  = sharedPreferences.getInt("balls", 0)
        balls++
        var wickets  = sharedPreferences.getInt("wickets", 0)
        wickets++


        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        //update bowler
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        bowler.wickets++
        bowler.balls++
        var overs = (bowler.balls/6).toDouble()
        var ballsBowler = (bowler.balls%6).toDouble()
        ballsBowler /= 6
        overs += ballsBowler
        var eco = bowler.runsconceded.toDouble()/overs
        eco = floor(eco * 100) / 100
        bowler.eco = eco.toString()
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)
        editor.putInt("balls" , balls)
        editor.putInt("wickets" , wickets)

            //update remaining batsmen
        val json = Gson().toJson(remainingBatsmen)
        editor.putString("remainingBatsmen" , json)

        //update batsman
        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)


        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsmanOld : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsmanOld.name = batsmanOld.name?.dropLast(1)
        batsmanOld.ballsFaced++
        if(bowled==1){
            batsmanOld.wicket = "b ${bowler.name}"
            bowled =0
        }
        else if(lbw==1){
            batsmanOld.wicket = "lbw ${bowler.name}"
            lbw =0
        }
        else if(stump == 1){
            batsmanOld.wicket = "st $playerName b ${bowler.name}"
            stump =0
        }

        if(batsmanOld.runs ==0){
            batsmanOld.strikeRate == "0.00"
        }
        else{
            var strikeRate = batsmanOld.runs.toDouble()/batsmanOld.ballsFaced.toDouble()
            strikeRate *=100
            strikeRate = floor(strikeRate * 100) / 100
            batsmanOld.strikeRate = strikeRate.toString()
        }



        batsmanModel.name = batsmanModel.name + "*"

        val batsmanNewGson = Gson().toJson(batsmanModel)
        editor.putString("batsman$batsmanNumber" , batsmanNewGson)

        //update overs
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        if(balls==1){
            val ballsArray = ArrayList<String>()
            ballsArray.add("W")
            oversList.add(OverModel(1,0,ballsArray))
        }
        else{
            oversList[oversList.size-1].balls.add("W")
        }


        overListJson = Gson().toJson(oversList)
        editor.putString("over_list", overListJson)


        //update scorecard
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].wickets++
        list[innings].balls++

        list[innings].batsmenList.add(batsmanModel)

        if(batsmanNumber==1){
            Log.e("LMAO", "1")
            list[innings].batsmenList[list[innings].batsman1Index] = batsmanOld
            list[innings].batsman1Index = list[innings].batsmenList.size -1
        }
        else if(batsmanNumber==2){
            Log.e("LMAO", "2")
            list[innings].batsmenList[list[innings].batsman2Index] = batsmanOld
            list[innings].batsman2Index = list[innings].batsmenList.size -1
        }

        list[innings].bowlerList[list[innings].bowlerIndex] = bowler

        val json1 = Gson().toJson(list)
        editor.putString("scorecard_list", json1)

        editor.commit()


        setupScore()

        if(balls%6==0){
            addNewOver(oversList.size)
            nextOver()
        }

        ri = activity as RefreshInterface
        ri.refreshAdapter()


    }


    private fun wicketRun(){
        disableButton(btn_four)
        disableButton(btn_six)
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
    }


    private fun wicketAddRuns(x : Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val score = sharedPreferences.getInt("score", 0)+ x
        val balls = sharedPreferences.getInt("balls", 0)+1
        val wickets = sharedPreferences.getInt("wickets", 0)+1
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
        editor.putInt("wickets", wickets)

        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)


        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsmanOld : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsmanOld.runs += x
        batsmanOld.name = batsmanOld.name?.dropLast(1)

        batsmanOld.ballsFaced++
        if(batsmanOld.runs ==0){
            batsmanOld.strikeRate == "0.00"
        }
        else{
            var strikeRate = batsmanOld.runs.toDouble()/batsmanOld.ballsFaced.toDouble()
            strikeRate *=100
            strikeRate = floor(strikeRate * 100) / 100
            batsmanOld.strikeRate = strikeRate.toString()
        }
        editor.putString("batsman$batsmanNumber",Gson().toJson(batsmanOld))

        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        if(catch ==1){
            bowler.wickets++
        }
        bowler.runsconceded +=x
        bowler.balls++
        var oversBowler = (bowler.balls/6).toDouble()
        var ballsBowler = (bowler.balls%6).toDouble()
        ballsBowler /= 6
        oversBowler += ballsBowler
        var eco = bowler.runsconceded.toDouble()/oversBowler
        eco = floor(eco * 100) / 100
        bowler.eco = eco.toString()
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)


        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        if(balls==1){
            val ballsArray = ArrayList<String>()
            ballsArray.add("W")
            oversList.add(OverModel(1,0,ballsArray))
        }
        else{
            oversList[oversList.size-1].balls.add("W")
        }

        overListJson = Gson().toJson(oversList)
        editor.putString("over_list", overListJson)




        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1

        list[innings].balls = balls
        list[innings].wickets = wickets
        list[innings].runs = score

        val json1 = Gson().toJson(list)
        editor.putString("scorecard_list", json1)


        editor.commit()

        setupScore()

        if(runout ==1 ){
            getBatsmanOnStrike()
        }
        else if(catch ==1){
            catch()
        }



    }




    private fun runOut(x: Int){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val innings= sharedPreferences.getInt("innings", 1)
        var teamNo = sharedPreferences.getInt("first_batting", 1)
        if(innings ==1){
            teamNo = if(teamNo ==2)
                1
            else
                2
        }
        val team = sharedPreferences.getString("team_${teamNo}" , "lol").toString()
        val list : ArrayList<PlayerModel> = Gson().fromJson(team, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.choose_next_batsman,null)




        mDialogView.rv_choose_next_batsman.layoutManager = LinearLayoutManager(this.activity)

        val choosePlayerAdapter = ChoosePlayerAdapter(this.activity!!,list)

        mDialogView.rv_choose_next_batsman.adapter = choosePlayerAdapter
        mDialogView.choose_player_dialog_heading.text = "Choose player who run out"

        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()

        mAlertDialog.btn_submit_player.setOnClickListener {

            val playerName = choosePlayerAdapter.getPlayer()
            if(playerName == ""){
                Toast.makeText(this.activity,"Please choose a player",Toast.LENGTH_SHORT ).show()
            }
            else{
                wicket(playerName, x)
                mAlertDialog.dismiss()
            }

        }
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }






    private fun catch(){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val innings= sharedPreferences.getInt("innings", 1)
        var teamNo = sharedPreferences.getInt("first_batting", 1)
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)

        if(innings ==1){
            teamNo = if(teamNo ==2)
                1
            else
                2
        }
        val team = sharedPreferences.getString("team_${teamNo}" , "lol").toString()
        val list : ArrayList<PlayerModel> = Gson().fromJson(team, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.choose_next_batsman,null)

        mDialogView.rv_choose_next_batsman.layoutManager = LinearLayoutManager(this.activity)

        val choosePlayerAdapter = ChoosePlayerAdapter(this.activity!!,list)

        mDialogView.rv_choose_next_batsman.adapter = choosePlayerAdapter
        if(catch ==1){
            mDialogView.choose_player_dialog_heading.text = "Choose player who caught the ball"
        }
        else{
            mDialogView.choose_player_dialog_heading.text = "Choose wicket-keeper"
        }


        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()

        mAlertDialog.btn_submit_player.setOnClickListener {

            val playerName = choosePlayerAdapter.getPlayer()
            if(playerName == ""){
                Toast.makeText(this.activity,"Please choose a player",Toast.LENGTH_SHORT ).show()
            }
            else if(playerName == bowler.name && stump==1){
                Toast.makeText(this.activity,"Please choose a different player",Toast.LENGTH_SHORT ).show()
            }
            else{
                wicket(playerName)
                mAlertDialog.dismiss()
            }

        }
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }



    private fun updateCatchOrRunout(batsmanModel: BatsmanModel , remainingBatsmen : ArrayList<PlayerModel>,playerName : String ,playerNo : Int =1){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)
        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        val balls = sharedPreferences.getInt("balls" ,0)
        var batsman : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)

        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)

        var wicketString = ""
        if(catch ==1){
            if(playerName == bowler.name){
                batsman.wicket = "c&b $playerName"
                wicketString = batsman.wicket
            }
            else{
                batsman.wicket = "c $playerName b ${bowler.name}"
                wicketString = batsman.wicket
            }

        }
        else if(runout==1){
            batsman.wicket = "run out ($playerName)"
            wicketString = batsman.wicket
        }

        val editor : SharedPreferences.Editor = sharedPreferences.edit()


        //update scorecard
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1

        list[innings].batsmenList.add(batsmanModel)


        if(runout==1){
            if(playerNo==1){
                list[innings].batsmenList[list[innings].batsman1Index].wicket = wicketString
                list[innings].batsman1Index = list[innings].batsmenList.size -1
            }
            else if(playerNo==2){
                list[innings].batsmenList[list[innings].batsman2Index].wicket = wicketString
                list[innings].batsman2Index = list[innings].batsmenList.size -1
            }
        }

        if(catch==1){
            if(batsmanNumber==1){
                list[innings].batsmenList[list[innings].batsman1Index] = batsman
                list[innings].batsman1Index = list[innings].batsmenList.size -1
            }
            else if(batsmanNumber==2){
                list[innings].batsmenList[list[innings].batsman2Index] = batsman
                list[innings].batsman2Index = list[innings].batsmenList.size -1
            }
        }


        list[innings].bowlerList[list[innings].bowlerIndex] = bowler

        editor.putString("scorecard_list" , Gson().toJson(list))
        editor.putString("remainingBatsmen", Gson().toJson(remainingBatsmen))

        if(catch==1){
            editor.putString("batsman$batsmanNumber", Gson().toJson(batsmanModel))
        }
        else if(runout==1){
            editor.putString("batsman$playerNo", Gson().toJson(batsmanModel))
        }


        editor.commit()

        catch =0
        runout=0
        getBatsmanOnStrike()

        if(balls%6==0){
            addNewOver(balls/6)
            nextOver()
        }





        setupScore()


        enableButton(btn_four)
        enableButton(btn_six)
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


    private fun updatestrikeScorecard(){
        val sharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1

        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        var batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        var index = list[innings].batsman1Index
        list[innings].batsmenList[index] = batsman1

        val batsman2JSON = sharedPreferences.getString("batsman2", "")
        var batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        val index2 = list[innings].batsman2Index
        list[innings].batsmenList[index2] = batsman2

        val editor = sharedPreferences.edit()

        editor.putString("scorecard_list" , Gson().toJson(list))
        editor.commit()

        ri = activity as RefreshInterface
        ri.refreshAdapter()

    }


    private fun inningsChangeWicket(playerName: String = "" , playerNo : Int = 0){
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var balls  = sharedPreferences.getInt("balls", 0)
        balls++
        var wickets  = sharedPreferences.getInt("wickets", 0)
        wickets++


        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        //update bowler
        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        if(runout!=1)
            bowler.wickets++
        bowler.balls++
        var overs = (bowler.balls/6).toDouble()
        var ballsBowler = (bowler.balls%6).toDouble()
        ballsBowler /= 6
        overs += ballsBowler
        var eco = bowler.runsconceded.toDouble()/overs
        eco = floor(eco * 100) / 100
        bowler.eco = eco.toString()
        val bowlerGson = Gson().toJson(bowler)
        editor.putString("bowler" , bowlerGson)
        editor.putInt("balls" , balls)
        editor.putInt("wickets" , wickets)


        //update batsman
        var batsmanNumber = sharedPreferences.getInt("batsman_on_strike", 1)


        val batsmanJSON = sharedPreferences.getString("batsman$batsmanNumber", "")
        var batsmanOld : BatsmanModel = Gson().fromJson(batsmanJSON, object: TypeToken<BatsmanModel>(){}.type)
        batsmanOld.name = batsmanOld.name?.dropLast(1)
        batsmanOld.ballsFaced++
        if(bowled==1){
            batsmanOld.wicket = "b ${bowler.name}"
            bowled =0
        }
        else if(lbw==1){
            batsmanOld.wicket = "lbw ${bowler.name}"
            Log.e("sdf", "LBW")
            lbw =0
        }
        else if(stump == 1){
            batsmanOld.wicket = "st $playerName b ${bowler.name}"
            stump =0
        }
        else if(catch==1){
            batsmanOld.wicket = "c $playerName b ${bowler.name}"
            catch =0
        }
        else if(runout ==1){
            batsmanOld.wicket = "run out ($playerName)"
            runout =0
        }

        if(batsmanOld.runs ==0){
            batsmanOld.strikeRate == "0.00"
        }
        else{
            var strikeRate = batsmanOld.runs.toDouble()/batsmanOld.ballsFaced.toDouble()
            strikeRate *=100
            strikeRate = floor(strikeRate * 100) / 100
            batsmanOld.strikeRate = strikeRate.toString()
        }




        //update overs
        var overListJson = sharedPreferences.getString("over_list", emptyList<OverModel>().toString())
        var oversList  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<OverModel>>(){}.type)
        if(balls==1){
            val ballsArray = ArrayList<String>()
            ballsArray.add("W")
            oversList.add(OverModel(1,0,ballsArray))
        }
        else{
            oversList[oversList.size-1].balls.add("W")
        }


        overListJson = Gson().toJson(oversList)
        editor.putString("over_list", overListJson)


        //update scorecard
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)
        val innings = sharedPreferences.getInt("innings" , 0) -1
        list[innings].wickets++
        list[innings].balls++


        if(runout==1){
            if(playerNo==1){
                list[innings].batsmenList[list[innings].batsman1Index]= batsmanOld
                list[innings].batsman1Index = list[innings].batsmenList.size -1
            }
            else if(playerNo==2){
                list[innings].batsmenList[list[innings].batsman2Index]= batsmanOld
                list[innings].batsman2Index = list[innings].batsmenList.size -1
            }
        }
        else{
            if(batsmanNumber==1){
                Log.e("LMAO", "1")
                list[innings].batsmenList[list[innings].batsman1Index] = batsmanOld
                list[innings].batsman1Index = list[innings].batsmenList.size -1
            }
            else if(batsmanNumber==2){
                Log.e("LMAO", "2")
                list[innings].batsmenList[list[innings].batsman2Index] = batsmanOld
                list[innings].batsman2Index = list[innings].batsmenList.size -1
            }
        }


        list[innings].bowlerList[list[innings].bowlerIndex] = bowler

        val json1 = Gson().toJson(list)
        editor.putString("scorecard_list", json1)

        editor.commit()


        setupScore()



        ri = activity as RefreshInterface
        ri.refreshAdapter()


        inningsOver()
    }




    private fun inningsOver(){
        val sharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        var teamNo = sharedPreferences.getInt("first_batting",0)
        val balls= sharedPreferences.getInt("balls" , 0)
        val wickets = sharedPreferences.getInt("wickets" , 0)
        teamNo = if(teamNo==1){
            2
        }
        else {
            1
        }
        val teamName = sharedPreferences.getString("team_${teamNo}_name", "")
        val score = sharedPreferences.getInt("score" ,0)
        val overs = sharedPreferences.getInt("overs", 0)
        sharedPreferences.edit().putInt("target", score+1).commit()
        sharedPreferences.edit().putInt("team1Balls", balls).commit()
        sharedPreferences.edit().putInt("team1Wickets", wickets).commit()




        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.innings_change_dialog,null)

        mDialogView.innings_dialog_tv.text = "$teamName needs ${score+1} runs in ${overs*6} balls."


        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()

        mAlertDialog.btn_submit_innings.setOnClickListener {
            val intent = Intent(this.activity , ChooseBatsmenActivity::class.java)
            intent.putExtra("team_no" , teamNo)
            startActivity(intent)
            this.activity!!.finish()

        }
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun matchOver(x: Int){
        val sharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val team1 = sharedPreferences.getInt("first_batting", 0)
        val team2 = if(team1==1){
            2
        }
        else {
            1
        }

        val teamJson = sharedPreferences.getString("team_2" , "")
        val team : ArrayList<PlayerModel> = Gson().fromJson(teamJson, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        val teamSize = team.size
        val team1Name = sharedPreferences.getString("team_${team1}_name" , "")
        val team1Score = sharedPreferences.getInt("target", 0) -1
        val team1Balls = sharedPreferences.getInt("team1Balls", 0)
        val team1Wickets = sharedPreferences.getInt("team1Wickets", 0)
        val team2Name = sharedPreferences.getString("team_${team2}_name" , "")
        val team2Score = sharedPreferences.getInt("score" , 0)
        val team2Balls = sharedPreferences.getInt("balls" ,0)
        val team2Wickets = sharedPreferences.getInt("wickets" , 0)

        val mDialogView = LayoutInflater
            .from(this.activity)
            .inflate(R.layout.innings_change_dialog,null)

        mDialogView.tv_match_over.text = "Match Over"
        var matchString = ""

        when(x){
            1 -> {
                matchString = "$team1Name won by ${team1Score-team2Score} runs"
            }
            2 -> {
                matchString = "$team2Name won by ${teamSize - team2Wickets-1} wickets"
            }

            3 -> {
                matchString = "$team2Name won by ${teamSize - team2Wickets-1} wickets"
            }

             4 -> {
                 matchString = "Match Tied"
            }
        }

        mDialogView.innings_dialog_tv.text = matchString

        val result = ResultModel(0 ,team1Name!! , team1Score , team1Balls , team1Wickets, team2Name!!, team2Score, team2Balls, team2Wickets , matchString)

        val dbHandler = DatabaseHandler(this.activity!!)
        dbHandler.addResult(result)

        val mBuilder = AlertDialog.Builder(this.activity)
            .setView(mDialogView)
            .setCancelable(false)


        val mAlertDialog = mBuilder.show()

        mAlertDialog.btn_submit_innings.setOnClickListener {

            this.activity!!.finish()

        }
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }












}