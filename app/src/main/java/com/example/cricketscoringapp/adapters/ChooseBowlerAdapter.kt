package com.example.cricketscoringapp.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.PlayerModel
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.batsman_checkbox_view.view.*

class ChooseBowlerAdapter(private val context : Context,private val list : ArrayList<PlayerModel>): RecyclerView.Adapter<ChooseBowlerAdapter.ViewHolder>(){

    var checkBoxStateArray = SparseBooleanArray()

    var count = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        for( i in 0..list.size){
            checkBoxStateArray.put(i,false)
        }
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

        holder.checkbox.isEnabled = !(!checkBoxStateArray.get(position,false) && count==1)


        if (holder is ViewHolder) {
            holder.itemView.tv_batsman_name.text = model.name


        }}


    override fun getItemCount(): Int {
        return list.size
    }

    fun goNext(): String{
        var name = ""
        Log.i("count" , "$checkBoxStateArray + count $count")

        if(count!=1){
            return name
        }
        var x = checkBoxStateArray.size()
        x--
        for( i in 0..x){
            if(checkBoxStateArray.get(i,false)){
                Log.i("count" , "$i i")
                name = list[i].name.toString()
                Log.i("count" , "$name name")
                    break
            }

        }



        return name
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var checkbox: MaterialCheckBox = itemView.cb_batsman

        init {

            checkbox.setOnClickListener {
                if(!checkBoxStateArray.get(adapterPosition,false)){
                    checkBoxStateArray.put(adapterPosition,true)
                    count++
                    notifyDataSetChanged()
                }
                else{
                    checkBoxStateArray.put(adapterPosition,false)
                    count--
                    notifyDataSetChanged()
                }
            }
        }

    }

}