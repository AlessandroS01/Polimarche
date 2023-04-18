package com.example.polimarche.Data

data class DataDampers(
    val code: Int,
    val end: String,
    val hsr: Double,
    val hsc: Double,
    val lsr: Double,
    val lsc: Double,
    var expansion: Boolean = false
)