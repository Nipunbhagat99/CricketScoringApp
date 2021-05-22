package com.example.cricketscoringapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.OversAdapter
import com.example.cricketscoringapp.adapters.ScorecardAdapter
import com.example.cricketscoringapp.models.*
import kotlinx.android.synthetic.main.fragment_overs.*
import kotlinx.android.synthetic.main.fragment_scorecard.*


class ScorecardFragment : Fragment(), ScorecardAdapter.OnItemClickedListener {

    private var list = ArrayList<ScorecardModel>()
    private var batsmenList = ArrayList<BatsmanModel>()
    private var bowlersList = ArrayList<BowlerModel>()


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


    private fun setupOversRV(){

        rv_scorecard.layoutManager = LinearLayoutManager(this.activity)

        val scorecardAdapter = this.activity?.let { ScorecardAdapter(it, list, this) }

        rv_scorecard.adapter = scorecardAdapter


    }

    private fun setupScorecardList(){
        val rahul = BatsmanModel("Rahul ", "Lmao" ,120,134,95.43, 7,7,"c Kohli b Kumar")
        batsmenList.add(rahul)
        batsmenList.add(rahul)
        batsmenList.add(rahul)
        batsmenList.add(rahul)
        val nalin = BowlerModel("Nalin" , "LOL" , 56 , 3 ,5.60, 10 , 1)
        bowlersList.add(nalin)
        bowlersList.add(nalin)
        bowlersList.add(nalin)
        bowlersList.add(nalin)
        var scorecard = ScorecardModel("Lmao" , 230 , 4 , 250 , batsmenList, bowlersList , true)
        var scorecard1 = ScorecardModel("Lmao" , 230 , 4 , 250 , batsmenList, bowlersList , false)
        var scorecard2 = ScorecardModel("Lmao" , 230 , 4 , 250 , batsmenList, bowlersList , false)
        var scorecard3 = ScorecardModel("Lmao" , 230 , 4 , 250 , batsmenList, bowlersList , false)
        list.add(scorecard)
        list.add(scorecard1)
        list.add(scorecard2)
        list.add(scorecard3)


    }

    override fun onItemClicked(items: ArrayList<ScorecardModel>, position: Int) {
        super.onItemClicked(items, position)
        items[position].expanded = !items[position].expanded
        rv_scorecard.adapter?.notifyDataSetChanged()

    }


}