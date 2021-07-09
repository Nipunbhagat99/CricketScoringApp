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
            var wide = Regex("Wd")
            var wicket = Regex("W")
            var byes = Regex("B")

            when(model){
                "W" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e33636"))
                "4" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#14b7db"))
                "6" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#cf61c0"))
                "1" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#26ab62"))
                "2" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#26ab62"))
                "3" -> holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#26ab62"))
            }
            if(wide.containsMatchIn(model)){
                holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e8c846"))
            }
            else if(wicket.containsMatchIn(model)){
                holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e33636"))
            }
            else if(byes.containsMatchIn(model)){
                holder.itemView.tvball.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e8c846"))
            }

        }}


    override fun getItemCount(): Int {
        return list.size
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}