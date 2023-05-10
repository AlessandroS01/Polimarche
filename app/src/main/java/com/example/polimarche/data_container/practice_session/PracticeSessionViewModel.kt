package com.example.polimarche.data_container.practice_session

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.example.polimarche.data_container.practice_session.PracticeSessionRepository

class PracticeSessionViewModel: ViewModel() {

    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        PracticeSessionRepository.listPracticeSession
    val listPracticeSession get() = _listPracticeSessions

    fun addNewPracticeSession(newSession: DataPracticeSession){
        PracticeSessionRepository.addNewPracticeSession(newSession)
    }

}