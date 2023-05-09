package com.example.polimarche.Users.All.Menu.PracticeSession

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.Data.DataPracticeSession
import java.time.LocalDate
import java.time.LocalTime

object PracticeSessionRepository {

    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        MutableLiveData(
            mutableListOf(
                DataPracticeSession("Endurance", LocalDate.of(2023, 8, 21),
                LocalTime.of(10, 30, 0), LocalTime.of(12, 30, 0),
                "Monza", "Sunny", "Dry", 32.7, 1.01,
                25.6),
                DataPracticeSession("Acceleration", LocalDate.of(2023, 9, 21),
                LocalTime.of(10, 30, 0), LocalTime.of(12, 30, 0),
                "Imola", "Sunny", "Dry", 32.7, 1.01,
                25.6),
                DataPracticeSession("Skidpad", LocalDate.of(2023, 10, 21),
                LocalTime.of(10, 30, 0), LocalTime.of(12, 30, 0),
                "Monza", "Sunny", "Dry", 32.7, 1.01,
                25.6),
                DataPracticeSession("Autocross", LocalDate.of(2023, 6, 21),
                LocalTime.of(10, 30, 0), LocalTime.of(12, 30, 0),
                "Imola", "Sunny", "Dry", 32.7, 1.01,
                25.6),

            )
        )
    val listPracticeSession get() = _listPracticeSessions

    fun addNewPracticeSession(newSession: DataPracticeSession){
        _listPracticeSessions.value =
            _listPracticeSessions.value?.plus(newSession) as MutableList<DataPracticeSession>?
    }
}