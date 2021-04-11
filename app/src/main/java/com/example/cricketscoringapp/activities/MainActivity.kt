package com.example.cricketscoringapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscoringapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_team_1.setOnClickListener {
            val intent = Intent(this , TeamOneActivity::class.java)
            startActivity(intent)
        }
    }



}