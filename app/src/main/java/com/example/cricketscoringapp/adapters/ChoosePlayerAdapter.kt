package com.example.cricketscoringapp.adapters

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.PlayerModel
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.batsman_checkbox_view.view.*
import kotlinx.android.synthetic.main.player_choose_view.view.*

class ChoosePlayerAdapter(private val context : Context, private val list : ArrayList<PlayerModel>): RecyclerView.Adapter<ChoosePlayerAdapter.ViewHolder>(){

    var checkBoxStateArray = SparseBooleanArray(list.size-1)
    lateinit var player : String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.batsman_checkbox_view,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.checkbox.isChecked = checkBoxStateArray[position]

        if (holder is ChoosePlayerAdapter.ViewHolder) {
            holder.itemView.tv_batsman_name.text = model.name

        }}



    fun goNext(): ArrayList<PlayerModel>{

        val newList = ArrayList<PlayerModel>(list)

        var x = checkBoxStateArray.size()
        x--
        var y =0
        for( i in 0..x){
            if(checkBoxStateArray.get(i,false)){
                if(y==0){
                    player = newList[i].name.toString()
                    newList.removeAt(i)
                    y++
                }

            }

        }
        return newList
    }




    @JvmName("getPlayer1")
    fun getPlayer(): String {
        var x = checkBoxStateArray.size()
        x--
        for( i in 0..x){
            if(checkBoxStateArray.get(i,false)){
                return list[i].name.toString()

            }

        }
        return ""
    }






    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var checkbox: MaterialCheckBox = itemView.cb_batsman


        init {

            checkbox.setOnClickListener {
                if(!checkBoxStateArray.get(adapterPosition,false)){
                    val size = list.size-1
                    for(i in 0..size){
                            checkBoxStateArray.put(i,false)
                    }
                    checkBoxStateArray.put(adapterPosition,true)
                    checkbox.isChecked = true
                    notifyDataSetChanged()
                }
                else{
                    checkBoxStateArray.put(adapterPosition,false)
                    checkbox.isChecked = false
                    notifyDataSetChanged()
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}