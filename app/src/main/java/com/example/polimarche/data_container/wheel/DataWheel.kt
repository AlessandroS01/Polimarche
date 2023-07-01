package com.example.polimarche.data_container.wheel

data class DataWheel(
    val code: Int = 0,
    val position: String = "",
    val codification: String = "",
    val pressure: String = "",
    val camber: String = "",
    val toe: String = "",
    var expansion: Boolean = false
) {
    // Costruttore senza argomenti per consetire a firebase di deserializzare gli oggetti
    constructor() : this(0, "", "", "", "", "", false)
}
