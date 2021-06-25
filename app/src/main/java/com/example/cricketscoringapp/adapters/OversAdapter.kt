package com.example.cricketscoringapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.OverModel
import com.example.cricketscoringapp.models.PlayerModel
import kotlinx.android.synthetic.main.activity_squad.*
import kotlinx.android.synthetic.main.fragment_overs.*
import kotlinx.android.synthetic.main.over_view.view.*
import kotlinx.android.synthetic.main.player_team_view.view.*

class OversAdapter(private val context: Context, private val list: ArrayList<OverModel>): RecyclerView.Adapter<OversAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {




        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.over_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {

            if(model.balls != null){
            holder.itemView.rv_balls.layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.HORIZONTAL,false)

            val ballAdapter = BallsAdapter(holder.itemView.context,model?.balls )

            holder.itemView.rv_balls.adapter = ballAdapter
                }

            holder.itemView.tv_over_number.text = "Over ${model.number}"
            holder.itemView.tv_over_runs.text = "${model.runs} runs"

        }}


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}