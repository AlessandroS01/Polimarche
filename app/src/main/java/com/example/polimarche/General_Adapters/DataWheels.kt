package com.example.polimarche.General_Adapters

data class DataWheels (
    val code: Int,
    val position: String,
    val codification: String,
    val pressure: String,
    val camber: String,
    val toe: String,
    var expansion: Boolean = false
)