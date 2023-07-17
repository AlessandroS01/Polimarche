package com.example.polimarche.data_container.practice_session



import PracticeSessionRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PracticeSessionViewModel: ViewModel() {

    private val practiceSessionRepository: PracticeSessionRepository = PracticeSessionRepository()

    init {//blocco di inizializzazione
        viewModelScope.launch {
            practiceSessionRepository.fetchSessionFromFirestore()
        }
    }
    fun initialize(){
        viewModelScope.launch {
            practiceSessionRepository.fetchSessionFromFirestore()
        }
    }

    //_listPracticeSessions è una variabile privata di tipo MutableLiveData che contiene
    // una lista mutable di oggetti DataTrack.
    // Viene inizializzata con il valore di practiceSessionRepository.listPracticeSession
    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        practiceSessionRepository.listPracticeSession
    val listPracticeSession get() = _listPracticeSessions
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.

    fun addNewPracticeSession(newSession: DataPracticeSession){
        practiceSessionRepository.addNewPracticeSession(newSession)
    }

}