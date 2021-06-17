package com.example.cricketscoringapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.OversAdapter
import com.example.cricketscoringapp.models.OverModel
import com.example.cricketscoringapp.models.PlayerModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_squad.rv_squad
import kotlinx.android.synthetic.main.fragment_overs.*

class OversFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOverList()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOversRV()
    }


    private fun setupOverList(){

        var over = OverModel(1,0, ArrayList<String>())
        var list = ArrayList<OverModel>()
        list.add(over)
        list[0]
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val overListJson = gson.toJson(list)
        editor.putString("over_list", overListJson)


    }

    private fun setupOversRV(){

        rv_overs.layoutManager = LinearLayoutManager(this.activity)
        val sharedPreferences : SharedPreferences = this.activity!!.getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
        val overListJson = sharedPreferences.getString("over_list", emptyList<PlayerModel>().toString())
        val list  : ArrayList<OverModel> = Gson().fromJson(overListJson, object: TypeToken<ArrayList<PlayerModel>>(){}.type)

        val overAdapter = this.activity?.let { OversAdapter(it, list) }

        rv_overs.adapter = overAdapter

        if(list.size>0){
            tv_overs_no_balls.visibility = View.GONE
        }

    }


}