package com.example.polimarche.data_container.practice_session


import PracticeSessionRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PracticeSessionViewModel: ViewModel() {

    private val practiceSessionRepository: PracticeSessionRepository = PracticeSessionRepository

    init {
        viewModelScope.launch {
            practiceSessionRepository.fetchSessionFromFirestore()
        }
    }

    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        practiceSessionRepository.listPracticeSession
    val listPracticeSession get() = _listPracticeSessions

    fun addNewPracticeSession(newSession: DataPracticeSession){
        practiceSessionRepository.addNewPracticeSession(newSession)
    }

}