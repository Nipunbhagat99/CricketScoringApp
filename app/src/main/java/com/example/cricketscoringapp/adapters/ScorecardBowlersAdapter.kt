package com.example.cricketscoringapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.BatsmanModel
import com.example.cricketscoringapp.models.BowlerModel
import kotlinx.android.synthetic.main.scorecard_bowler_view.view.*

class ScorecardBowlersAdapter (private val context : Context, private val list : ArrayList<BowlerModel>): RecyclerView.Adapter<ScorecardBowlersAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scorecard_bowler_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {
            holder.itemView.tv_scorecard_bowler_name.text = model.name
            val overs = model.balls/6
            val balls = model.balls%6
            holder.itemView.tv_scorecard_bowler_overs.text = "$overs.$balls"
            holder.itemView.tv_scorecard_bowler_maiden_overs.text = model.maidens.toString()
            holder.itemView.tv_scorecard_bowler_runs.text = model.runsconceded.toString()
            holder.itemView.tv_scorecard_bowler_wickets.text = model.wickets.toString()
            holder.itemView.tv_scorecard_bowler_economy_rate.text = model.eco.toString()

        }}


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){


    }
}