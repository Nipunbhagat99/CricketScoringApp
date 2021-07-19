package com.example.cricketscoringapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.adapters.PlayerAdapter
import com.example.cricketscoringapp.adapters.ResultsAdapter
import com.example.cricketscoringapp.database.DatabaseHandler
import com.example.cricketscoringapp.models.ResultModel
import com.example.cricketscoringapp.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.activity_team_one.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        setupRecyclerView()

    }


    private fun setupRecyclerView(){
        val dbHandler = DatabaseHandler(this)
        val getResultList : ArrayList<ResultModel> = dbHandler.getResultList()

        if(getResultList.size >0){

            rv_results.visibility  = View.VISIBLE
            tv_no_records_available.visibility= View.GONE
            val resultAdapter = ResultsAdapter(this, getResultList)

            val linearLayoutManager = LinearLayoutManager(this)
            rv_results.layoutManager = linearLayoutManager
            rv_results.adapter = resultAdapter


            val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = rv_results.adapter as ResultsAdapter
                    adapter.removeAt(viewHolder.adapterPosition)
                    setupRecyclerView()


                }
            }

            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
            deleteItemTouchHelper.attachToRecyclerView(rv_results)

        }else{
            rv_results.visibility  = View.GONE
            tv_no_records_available.visibility= View.VISIBLE
        }
    }
}