package com.example.cricketscoringapp.models

class BatsmanModel(
        var name : String?,
        val team : String?,
        var runs : Int,
        var ballsFaced : Int,
        var strikeRate : Double,
        var fours : Int,
        var sixes: Int,
        var wicket : String,
        var notOut : Boolean

){
       var strike :Boolean = false
}