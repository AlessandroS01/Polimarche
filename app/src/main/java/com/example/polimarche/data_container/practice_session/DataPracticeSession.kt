package com.example.polimarche.data_container.practice_session

import java.time.LocalDate
import java.time.LocalTime

data class DataPracticeSession(
    val eventType: String,
    val date: LocalDate,
    val startingTime: LocalTime,
    val endingTime: LocalTime,
    val trackName: String,
    val weather: String,
    val trackCondition: String,
    val trackTemperature: Double,
    val ambientPressure: Double,
    val airTemperature: Double,

    var expansion: Boolean = false
)
