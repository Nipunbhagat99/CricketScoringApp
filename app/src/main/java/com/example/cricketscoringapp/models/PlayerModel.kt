package com.example.cricketscoringapp.models

class PlayerModel(
        val name : String?,
        val role : String?,
        var captain : Boolean,
        var oversBowled : Int = 0,
)