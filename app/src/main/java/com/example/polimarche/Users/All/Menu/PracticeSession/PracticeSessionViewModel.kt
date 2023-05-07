package com.example.polimarche.Users.All.Menu.PracticeSession

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.DataPracticeSession
import java.time.LocalDate
import java.time.LocalTime

class PracticeSessionViewModel: ViewModel() {

    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        PracticeSessionRepository.listPracticeSession
    val listPracticeSession get() = _listPracticeSessions


}