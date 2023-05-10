package com.example.polimarche.data_container.setup

import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.wheel.DataWheel


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
