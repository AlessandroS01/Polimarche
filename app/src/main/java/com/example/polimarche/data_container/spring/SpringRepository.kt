package com.example.polimarche.data_container.spring

import androidx.lifecycle.MutableLiveData

object SpringRepository {

    private val _listSpring: MutableLiveData<MutableList<DataSpring>> =
        MutableLiveData(
            mutableListOf(
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataSpring(1, "A", "Front", 1.21, "12.4", "Center"),
                DataSpring(2, "B", "Front", 1.21, "12.4", "Center"),
                DataSpring(8, "H", "End", 1.21, "12.4", "Center"),
                DataSpring(9, "I", "End", 1.21, "12.4", "Center"),
            )
        )
    val listSpring get() = _listSpring
}