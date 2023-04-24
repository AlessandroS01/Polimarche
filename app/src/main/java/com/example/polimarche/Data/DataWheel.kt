package com.example.polimarche.Data

data class DataWheel (
    val code: Int,
    val position: String,
    val codification: String,
    val pressure: String,
    val camber: String,
    val toe: String,
    var expansion: Boolean = false
)