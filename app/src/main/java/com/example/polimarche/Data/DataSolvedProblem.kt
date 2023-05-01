package com.example.polimarche.Data

data class DataSolvedProblem(
    val problemCode: Int,
    val setupCode: Int,
    val description: String,

    var expansion: Boolean = false
)
