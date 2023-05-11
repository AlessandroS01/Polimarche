package com.example.polimarche.data_container.balance

import androidx.lifecycle.MutableLiveData

object BalanceRepository {

    private val _balanceList: MutableLiveData<MutableList<DataBalance>> =
        MutableLiveData(
            mutableListOf(
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                DataBalance(1, "Front", 44.0, 56.0),
                DataBalance(2, "Front", 44.0, 56.0),
                DataBalance(9, "Back", 44.0, 56.0),
                DataBalance(10, "Back", 44.0, 56.0)
            )
        )
    val balanceList get() = _balanceList
}