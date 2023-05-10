package com.example.polimarche.data_container.spring

data class DataSpring(
    val code: Int,
    val codification: String,
    val end: String,
    val height: Double,
    val arb_stiffness: String,
    val arb_position: String,
    var expansion: Boolean = false
)
