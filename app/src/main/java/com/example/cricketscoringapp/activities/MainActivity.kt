package com.example.cricketscoringapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscoringapp.R
import com.example.cricketscoringapp.models.PlayerModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

            val sharedPreferences : SharedPreferences= getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)
            val team1 = sharedPreferences.getString("team_1" , "")
            val team2 = sharedPreferences.getString("team_2" , "")
            val team1Array : ArrayList<PlayerModel>  = Gson().fromJson(team1, object: TypeToken<ArrayList<PlayerModel>>(){}.type)
            val team2Array : ArrayList<PlayerModel>  = Gson().fromJson(team2, object: TypeToken<ArrayList<PlayerModel>>(){}.type)



            if(team1Array.size<2 ){
                Toast.makeText(this, "Please add more players to team 1", Toast.LENGTH_SHORT).show()
            }
            else if(team2Array.size<2){
                Toast.makeText(this, "Please add more players to team 2", Toast.LENGTH_SHORT).show()
            }
            else {

                showBottomSheetDialog()
            }
        }

        btn_team_2.setOnClickListener {
            val intent = Intent(this , TeamTwoActivity::class.java)
            startActivity(intent)
        }

        btn_results.setOnClickListener {
            val intent = Intent(this , ResultsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showBottomSheetDialog(){
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.match_start_bottom_sheet,null)
        dialog.setContentView(view)
        dialog.btn_bottom_sheet_start.setOnClickListener {
            if(dialog.et_overs.text.isNullOrBlank()){
                Toast.makeText(this , "Please choose overs between 1-99", Toast.LENGTH_SHORT).show()
            }
            else if(dialog.et_overs.text.toString() == "00"){
                Toast.makeText(this , "Please choose overs between 1-99", Toast.LENGTH_SHORT).show()
            }
            else if(dialog.et_overs.text.toString() == "0"){
                Toast.makeText(this , "Please choose overs between 1-99", Toast.LENGTH_SHORT).show()
            }
            else {
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                val overs = dialog.et_overs.text.toString().toInt()
                editor.putInt("overs", overs)
                editor.putInt("innings", 0)
                editor.commit()
                val intent = Intent(this, FirstBattingActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
        }
        dialog.show()




    }



}
