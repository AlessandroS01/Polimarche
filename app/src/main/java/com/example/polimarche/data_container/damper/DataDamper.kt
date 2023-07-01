package com.example.polimarche.data_container.damper

data class DataDamper(
    val code: Int,
    val end: String,
    val hsr: Double,
    val hsc: Double,
    val lsr: Double,
    val lsc: Double,
    var expansion: Boolean = false
) {
    // Costruttore senza argomenti
    constructor() : this(0, "", 0.0, 0.0, 0.0, 0.0, false)
}
