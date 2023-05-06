package com.example.polimarche.Users.All.Menu.Setup

import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.*

class SetupViewModel: ViewModel() {

    private var _setupListData = mutableListOf<DataSetup>()
    val setupList: MutableList<DataSetup>
        get()= _setupListData

    init {
        _setupListData.add(
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
            ))
        _setupListData.add(
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
            )
        )
        _setupListData.add(
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
                mutableListOf("")
            )
        )
        _setupListData.add(
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
    }

    fun getSetupCodes(): MutableList<Int>{
        val listSetupCodes = mutableListOf<Int>()
        _setupListData.forEach {
            listSetupCodes.add(it.code)
        }
        return listSetupCodes
    }

    fun deleteSetup(position: Int){
        _setupListData.removeAt(position)
        // TODO FARE IN MODO TALE DA ELIMINARE LA RIGA DAL DB E RICHIAMARE IN SEGUITO LA VISUALIZZAZIONE DEI VARI SETUP PRESENTI

    }

}