package com.example.polimarche.Managers.M_Adapters

data class DataSprings(
    val code: Int,
    val codification: String,
    val end: String,
    val height: Double,
    val arb_stiffness: String,
    val arb_position: String,
    var expansion: Boolean = false
)
