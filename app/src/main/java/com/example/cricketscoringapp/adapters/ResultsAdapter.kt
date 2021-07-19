package com.example.cricketscoringapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.database.DatabaseHandler
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.models.ResultModel
import kotlinx.android.synthetic.main.player_team_view.view.*
import kotlinx.android.synthetic.main.result_view.view.*

class ResultsAdapter(private val context : Context, private val list : ArrayList<ResultModel>): RecyclerView.Adapter<ResultsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.result_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {
            holder.itemView.tv_result_string.text = model.winning_string
            holder.itemView.tv_team_1_name.text = model.team_1_name
            holder.itemView.tv_team_2_name.text = model.team_2_name
            holder.itemView.tv_team_1_score.text = "${model.team_1_score}-${model.team_1_wickets}"
            holder.itemView.tv_team_2_score.text = "${model.team_2_score}-${model.team_2_wickets}"
            holder.itemView.tv_team_1_balls.text = "(${model.team_1_balls/6}.${model.team_1_balls%6})"
            holder.itemView.tv_team_2_balls.text = "(${model.team_2_balls/6}.${model.team_2_balls%6})"
        }}


    override fun getItemCount(): Int {
        return list.size
    }


    fun removeAt(position : Int){
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteResult(list[position])

        if(isDeleted >0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }

    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}