package com.example.cricketscoringapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.ScorecardAdapter
import com.example.cricketscoringapp.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_scorecard.*


class ScorecardFragment : Fragment(), ScorecardAdapter.OnItemClickedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScorecardList()

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scorecard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOversRV()

    }

    override fun onResume() {
        super.onResume()
        rv_scorecard.adapter?.notifyDataSetChanged()
    }


    private fun setupOversRV(){

        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val scorecardListJson = sharedPreferences.getString("scorecard_list", emptyList<ScorecardModel>().toString())
        val list  : ArrayList<ScorecardModel> = Gson().fromJson(scorecardListJson, object: TypeToken<ArrayList<ScorecardModel>>(){}.type)

        rv_scorecard.layoutManager = LinearLayoutManager(this.activity)

        val scorecardAdapter = this.activity?.let { ScorecardAdapter(it, list, this) }

        rv_scorecard.adapter = scorecardAdapter


    }

    private fun setupScorecardList(){
        val list = ArrayList<ScorecardModel>()
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val batsman1JSON = sharedPreferences.getString("batsman1", "")
        val batsman1 : BatsmanModel = Gson().fromJson(batsman1JSON, object: TypeToken<BatsmanModel>(){}.type)
        val batsman2JSON = sharedPreferences.getString("batsman2","")
        val batsman2 : BatsmanModel = Gson().fromJson(batsman2JSON, object: TypeToken<BatsmanModel>(){}.type)
        val batsmanList = ArrayList<BatsmanModel>()
        batsmanList.add(batsman1)
        batsmanList.add(batsman2)

        val bowlerJSON = sharedPreferences.getString("bowler", "")
        val bowler : BowlerModel = Gson().fromJson(bowlerJSON, object: TypeToken<BowlerModel>(){}.type)
        val bowlerList = ArrayList<BowlerModel>()
        bowlerList.add(bowler)
        val firstBatting = sharedPreferences.getInt("first_batting", 1)
        val teamName = sharedPreferences.getString("team_${firstBatting}_name", "")
        val scorecardModel = ScorecardModel(teamName,0,0,0,batsmanList,bowlerList,true,0,1,0)

        list.add(scorecardModel)

        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val json = Gson().toJson(list)
        editor.putString("scorecard_list", json)
        editor.commit()


    }

    override fun onItemClicked(items: ArrayList<ScorecardModel>, position: Int) {
        super.onItemClicked(items, position)
        items[position].expanded = !items[position].expanded
        TransitionManager.beginDelayedTransition(rv_scorecard)
        rv_scorecard.adapter?.notifyDataSetChanged()

    }

    fun refresh(){
        setupOversRV()
    }




}