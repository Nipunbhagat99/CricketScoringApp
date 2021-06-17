package com.example.cricketscoringapp.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.PlayerModel
import com.example.cricketscoringapp.models.ScorecardModel
import kotlinx.android.synthetic.main.over_view.view.*
import kotlinx.android.synthetic.main.scorecard_view.view.*

class ScorecardAdapter (private val context : Context, private val list : ArrayList<ScorecardModel>, var clickListener : OnItemClickedListener): RecyclerView.Adapter<ScorecardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.scorecard_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.initialize(list , clickListener)



    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun initialize(items : ArrayList<ScorecardModel>, action : OnItemClickedListener){
                val item = items[adapterPosition]
                itemView.rv_scorecard_main_batsmen.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)

                val batsmenAdapter = ScorecardBatsmenAdapter(itemView.context, item.batsmenList)

                itemView.rv_scorecard_main_batsmen.adapter = batsmenAdapter

                itemView.rv_scorecard_main_bowlers.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)

                val bowlersAdapter = ScorecardBowlersAdapter(itemView.context, item.bowlerList)

                itemView.rv_scorecard_main_bowlers.adapter = bowlersAdapter

                itemView.tv_scorecard_team_name.text = item.teamName
                val overs = item.balls / 6
                val balls = item.balls % 6
                itemView.tv_scorecard_team_score.text =
                    "${item.runs}-${item.wickets}  (${overs}.${balls})"
                if (item.expanded) {
                    itemView.ll_scorecard_main.visibility = View.VISIBLE
                    itemView.ll_scorecard_heading.background = Color.parseColor("#123456").toDrawable()
                    itemView.iv_scorecard_arrow.setImageResource(R.drawable.up_arrow_white)
                    itemView.tv_scorecard_team_name.setTextColor(Color.parseColor("#ffffff"))
                    itemView.tv_scorecard_team_score.setTextColor(Color.parseColor("#ffffff"))

                } else {
                    itemView.ll_scorecard_main.visibility = View.GONE
                    itemView.ll_scorecard_heading.background = Color.parseColor("#ffffff").toDrawable()
                    itemView.tv_scorecard_team_name.setTextColor(Color.parseColor("#000000"))
                    itemView.iv_scorecard_arrow.setImageResource(R.drawable.down_arrow_grey)
                    itemView.tv_scorecard_team_score.setTextColor(Color.parseColor("#000000"))
                }

                itemView.ll_scorecard_heading.setOnClickListener {
                    action.onItemClicked(items , adapterPosition)
                }
            }


    }

    interface OnItemClickedListener {
        fun onItemClicked(items : ArrayList<ScorecardModel>, position : Int){

        }
    }
}





