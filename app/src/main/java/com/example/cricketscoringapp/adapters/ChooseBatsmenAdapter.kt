package com.example.cricketscoringapp.adapters

import android.content.Context

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R

import com.example.cricketscoringapp.models.PlayerModel
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.batsman_checkbox_view.view.*


class ChooseBatsmenAdapter(private val context : Context,private val list : ArrayList<PlayerModel>): RecyclerView.Adapter<ChooseBatsmenAdapter.ViewHolder>(){

    var checkBoxStateArray = SparseBooleanArray(list.size-1)
    var count = 0
    lateinit var batsman1 : String
    lateinit var batsman2 : String
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

        holder.checkbox.isEnabled = count<2 || checkBoxStateArray[position]
        if (holder is ViewHolder) {
            holder.itemView.tv_batsman_name.text = model.name

        }}


    override fun getItemCount(): Int {
        return list.size
    }

    fun goNext(): ArrayList<PlayerModel>{
        val size = list.size-1
        for(i in 0..size){
            if(!checkBoxStateArray.get(i,false)){
                checkBoxStateArray.put(i,false)
            }
        }

        if(count!=2){
            return ArrayList<PlayerModel>()
        }
        var x = checkBoxStateArray.size()
        x--
        var y =0
        for( i in 0..x){
            if(checkBoxStateArray.get(i,false)){
                if(y==0){
                    batsman1 = list[i].name.toString()
                    list.removeAt(i)
                    y++
                    }
                else{
                    batsman2 = list[i-1].name.toString()
                    list.removeAt(i-1)
                }

                    }

        }
        return list
    }




   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
       var checkbox: MaterialCheckBox = itemView.cb_batsman

        init {

            checkbox.setOnClickListener {
                if(!checkBoxStateArray.get(adapterPosition,false)){
                    checkBoxStateArray.put(adapterPosition,true)
                    count++
                    checkbox.isChecked = true
                    notifyDataSetChanged()
                }
                else{

                    checkBoxStateArray.put(adapterPosition,false)
                    checkbox.isChecked = false
                    count--
                    notifyDataSetChanged()
                }
            }
        }

    }

}