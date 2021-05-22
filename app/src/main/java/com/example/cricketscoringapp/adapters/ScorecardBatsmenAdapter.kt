package com.example.cricketscoringapp.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.BatsmanModel
import kotlinx.android.synthetic.main.scorecard_batsman_view.view.*


class ScorecardBatsmenAdapter (private val context : Context, private val list : ArrayList<BatsmanModel>): RecyclerView.Adapter<ScorecardBatsmenAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scorecard_batsman_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {
            holder.itemView.tv_scorecard_batsman_name.text = model.name
            holder.itemView.tv_scorecard_batsman_runs.text = model.runs.toString()
            holder.itemView.tv_scorecard_batsman_balls.text = model.ballsFaced.toString()
            holder.itemView.tv_scorecard_batsman_fours.text = model.fours.toString()
            holder.itemView.tv_scorecard_batsman_sixes.text = model.sixes.toString()
            holder.itemView.tv_scorecard_batsman_strike_rate.text = model.strikeRate.toString()
            if(model.wicket == "batting"){
                holder.itemView.tv_scorecard_wicket_status.text = "batting"
                holder.itemView.tv_scorecard_wicket_status.typeface = Typeface.DEFAULT_BOLD
            }
            else{
                holder.itemView.tv_scorecard_wicket_status.text = model.wicket
                holder.itemView.tv_scorecard_wicket_status.typeface = Typeface.DEFAULT
            }

        }}


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){


    }
}