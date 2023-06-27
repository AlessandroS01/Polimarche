package com.example.polimarche.data_container.team_members_workshop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.spring.SpringRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TeamRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchDataFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> = MutableLiveData()
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> = MutableLiveData()
    val listDepartments get() = _listDepartments


    suspend fun fetchDataFromFirestore() {

        val teamMembersCollection = db.collection("team_members")

        val teamMembersSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            teamMembersCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val membersList = mutableListOf<DataTeamMember>() // Initialize with an empty list

        for (document in teamMembersSnapshot) {

            val matriculation = document.getString("matriculation").toString().toLong()
            val firstName = document.getString("firstName")!!
            val lastName = document.getString("lastName")!!
            val dateOfBirth = document.getString("dateOfBirth")!!
            val email = document.getString("email")!!
            val cellNumber = document.getString("cellNumber")!!
            val workshopArea = document.getString("workshopArea")!!
            val member = DataTeamMember(
                matriculation,
                firstName,
                lastName,
                dateOfBirth,
                email,
                cellNumber,
                workshopArea
            )

            membersList.add(member)
        }
        withContext(Dispatchers.Main) {
            _listMembers.value = membersList // Use postValue to update MutableLiveData on the main thread
        }

        val departmentsCollection = db.collection("departments")

        val departmentsSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            departmentsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val departmentList = mutableListOf<DataWorkshopArea>() // Initialize with an empty list

        for (document in departmentsSnapshot) {
            val departmentName = document.getString("department")!!
            val departmentHead = document.getString("departmentHead").toString().toLong()
            val department = DataWorkshopArea(departmentName, departmentHead)

            departmentList.add(department)


        }
        withContext(Dispatchers.Main) {
            _listDepartments.value = departmentList // Use postValue to update MutableLiveData on the main thread
        }


    }
}
