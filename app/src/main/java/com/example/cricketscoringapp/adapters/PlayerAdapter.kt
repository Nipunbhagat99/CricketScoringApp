package com.example.cricketscoringapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.PlayerModel
import kotlinx.android.synthetic.main.player_team_view.view.*

class PlayerAdapter(private val context : Context,private val list : ArrayList<PlayerModel>): RecyclerView.Adapter<PlayerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.player_team_view,
                        parent,
                        false
                )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {
        holder.itemView.tv_player_name.text = model.name
        holder.itemView.tv_player_role.text = model.role
    }}


    override fun getItemCount(): Int {
        return list.size
    }



   class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}