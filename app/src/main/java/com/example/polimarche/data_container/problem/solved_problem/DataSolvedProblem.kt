package com.example.polimarche.data_container.problem.solved_problem

data class DataSolvedProblem(
    val problemCode: Int,
    val setupCode: Int,
    val description: String,

    var expansion: Boolean = false
)
