package com.example.polimarche.data_container.problem.occurring_problem

data class DataOccurringProblem(
    val problemCode: Int,
    val setupCode: Int,
    val description: String,

    var expansion: Boolean = false
)
