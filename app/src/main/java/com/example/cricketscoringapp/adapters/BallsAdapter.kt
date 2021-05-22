package com.example.cricketscoringapp.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.OverModel
import kotlinx.android.synthetic.main.ball_view.view.*

class BallsAdapter(private val context : Context, private val list : ArrayList<String>): RecyclerView.Adapter<BallsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.ball_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (holder is ViewHolder) {
            holder.itemView.tvball.text = model
            when(model){
                "W" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#f05b5b"))
                "4" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00d0ff"))
                "6" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffa3f3"))
            }

        }}


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}