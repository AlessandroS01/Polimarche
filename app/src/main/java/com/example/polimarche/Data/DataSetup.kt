package com.example.polimarche.Data


data class DataSetup(
    val code: Int,
    val frontRightWheel: DataWheel,
    val frontLeftWheel: DataWheel,
    val rearRightWheel: DataWheel,
    val rearLeftWheel: DataWheel,
    val frontDamper: DataDamper,
    val backDamper: DataDamper,
    val frontSpring: DataSpring,
    val backSpring: DataSpring,
    val frontBalance: DataBalance,
    val backBalance: DataBalance,

    val preferredEvent: String,

    val frontWingHole: String,
    var notes: MutableList<String>
)
