package com.example.cricketscoringapp.models

class ScorecardModel(
    val teamName: String?,
    var runs: Int,
    var wickets: Int,
    var balls: Int,
    var batsmenList: ArrayList<BatsmanModel>,
    var bowlerList: ArrayList<BowlerModel>,
    var expanded: Boolean
    )