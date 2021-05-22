package com.example.cricketscoringapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.OversAdapter
import com.example.cricketscoringapp.models.OverModel
import kotlinx.android.synthetic.main.activity_squad.rv_squad
import kotlinx.android.synthetic.main.fragment_overs.*

class OversFragment : Fragment() {

    private var list = ArrayList<OverModel>()
    private var balls = ArrayList<String>()




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
        balls.add("1")
        balls.add("2")
        balls.add("6")
        balls.add("4")
        balls.add("W")
        balls.add("W")
        var over = OverModel(1,13, balls)
        list.add(over)
        over = OverModel(2,13, balls)
        list.add(over)
        over = OverModel(3,13, balls)
        list.add(over)
        over = OverModel(4,13, balls)
        list.add(over)
        over = OverModel(5,13, balls)
        list.add(over)
    }

    private fun setupOversRV(){

        rv_overs.layoutManager = LinearLayoutManager(this.activity)

        val overAdapter = this.activity?.let { OversAdapter(it, list) }

        rv_overs.adapter = overAdapter


    }


}