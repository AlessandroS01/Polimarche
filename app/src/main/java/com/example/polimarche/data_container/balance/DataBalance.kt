package com.example.polimarche.data_container.balance


data class DataBalance(

    val code: Int,
    val end: String,
    val brake: Double,
    val weight: Double,

    var expansion: Boolean = false
)
