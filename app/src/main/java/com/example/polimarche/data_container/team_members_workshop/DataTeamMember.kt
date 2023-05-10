package com.example.polimarche.data_container.team_members_workshop

data class DataTeamMember(
    val matriculationNumber: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val cellNumber: String,
    val workshopArea: String
)