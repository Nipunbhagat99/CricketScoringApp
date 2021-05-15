package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscoringapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.match_start_bottom_sheet.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_team_1.setOnClickListener {
            val intent = Intent(this , TeamOneActivity::class.java)
            startActivity(intent)
        }

        btn_match.setOnClickListener {

            showBottomSheetDialog()

        }

        btn_team_2.setOnClickListener {
            val intent = Intent(this , TeamTwoActivity::class.java)
            startActivity(intent)
        }

        btn_history.setOnClickListener {  }

    }

    private fun showBottomSheetDialog(){
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.match_start_bottom_sheet,null)
        dialog.setContentView(view)
        dialog.btn_bottom_sheet_start.setOnClickListener {
            val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            val overs = dialog.et_overs.text.toString().toInt()
            editor.putInt("overs",overs)
            editor.commit()
            val intent = Intent(this , FirstBattingActivity::class.java)
            startActivity(intent)

        }
        dialog.show()




    }



}
