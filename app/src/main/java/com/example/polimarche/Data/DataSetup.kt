package com.example.polimarche.Data


data class DataSetup(
    val code: Int,
    val frontRightWheel: DataWheels,
    val frontLeftWheel: DataWheels,
    val rearRightWheel: DataWheels,
    val rearLeftWheel: DataWheels,
    val frontDamper: DataDampers,
    val backDamper: DataDampers,
    val frontSpring: DataSprings,
    val backSpring: DataSprings,
    val frontBalance: DataBalance,
    val backBalance: DataBalance,

    val preferredEvent: String,

    val frontWingHole: String,
    val notes: String
)
