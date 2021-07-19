package com.example.cricketscoringapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.cricketscoringapp.models.ResultModel

class DatabaseHandler(context: Context)  : SQLiteOpenHelper(context,  DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "ResultsDatabase"
        private const val DATABASE_VERSION  = 2
        private const val TABLE_RESULTS = "Results"

        //Column names
        private const val KEY_ID = "_id"
        private const val KEY_TEAM_1_NAME = "Team_1_Name"
        private const val KEY_TEAM_1_SCORE = "Team_1_Score"
        private const val KEY_TEAM_1_BALLS = "Team_1_Balls"
        private const val KEY_TEAM_1_WICKETS = "Team_1_Wickets"

        private const val KEY_TEAM_2_NAME = "Team_2_Name"
        private const val KEY_TEAM_2_SCORE = "Team_2_Score"
        private const val KEY_TEAM_2_BALLS = "Team_2_Balls"
        private const val KEY_TEAM_2_WICKETS = "Team_2_Wickets"
        private const val KEY_WINNING_STRING = "Winning_String"

    }



    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val createResultTable = ("CREATE TABLE " + TABLE_RESULTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEAM_1_NAME + " TEXT,"
                + KEY_TEAM_1_SCORE + " INTEGER,"
                + KEY_TEAM_1_BALLS + " INTEGER,"
                + KEY_TEAM_1_WICKETS + " INTEGER,"
                + KEY_TEAM_2_NAME + " TEXT,"
                + KEY_TEAM_2_SCORE + " INTEGER,"
                + KEY_TEAM_2_BALLS + " INTEGER,"
                + KEY_TEAM_2_WICKETS + " INTEGER,"
                + KEY_WINNING_STRING + " TEXT)")
        db?.execSQL(createResultTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_RESULTS")
        onCreate(db)
    }

    fun addResult(resultModel : ResultModel){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TEAM_1_NAME , resultModel.team_1_name)
        contentValues.put(KEY_TEAM_1_BALLS , resultModel.team_1_balls)
        contentValues.put(KEY_TEAM_1_SCORE , resultModel.team_1_score)
        contentValues.put(KEY_TEAM_1_WICKETS , resultModel.team_1_wickets)
        contentValues.put(KEY_TEAM_2_NAME , resultModel.team_2_name)
        contentValues.put(KEY_TEAM_2_BALLS , resultModel.team_2_balls)
        contentValues.put(KEY_TEAM_2_SCORE , resultModel.team_2_score)
        contentValues.put(KEY_TEAM_2_WICKETS , resultModel.team_2_wickets)
        contentValues.put(KEY_WINNING_STRING, resultModel.winning_string)

        // Inserting Row
        val result = db.insert(TABLE_RESULTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection

    }



    fun deleteResult(resultModel: ResultModel) : Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_RESULTS, KEY_ID +"=" + resultModel.id, null)
        db.close()
        return success
    }



    fun getResultList() : ArrayList<ResultModel>{
        val resultList =  ArrayList<ResultModel>()
        val selectQuery = "SELECT * FROM $TABLE_RESULTS"

        val db = this.readableDatabase
        try{
            val cursor : Cursor = db.rawQuery(selectQuery,null )
            if(cursor.moveToNext()){
                do{
                    val place = ResultModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TEAM_1_NAME)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_1_SCORE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_1_BALLS)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_1_WICKETS)),
                        cursor.getString(cursor.getColumnIndex(KEY_TEAM_2_NAME)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_2_SCORE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_2_BALLS)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TEAM_2_WICKETS)),
                        cursor.getString(cursor.getColumnIndex(KEY_WINNING_STRING))
                    )
                    resultList.add(place)

                }while (cursor.moveToNext())
                cursor.close()
            }

        }catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        Log.e("LMAO" ,"list size  ${resultList.size}")
        return resultList

    }


}
