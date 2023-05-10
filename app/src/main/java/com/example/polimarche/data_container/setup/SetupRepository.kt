package com.example.polimarche.data_container.setup

import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.wheel.DataWheel

object SetupRepository {


    private val _setupListData: MutableList<DataSetup> =
        mutableListOf(
            DataSetup(
                1,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            ),
            DataSetup(
                2,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            ),
            DataSetup(
                3,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("Ciao", "a", "tutti")
            ),
            DataSetup(
                4,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            )

        )
    val setupList: MutableList<DataSetup>
        get()= _setupListData


    fun removeSetup(setup: DataSetup){
        _setupListData.remove(setup)
    }

}