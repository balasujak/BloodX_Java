package com.example.BloodXClient.model.DataModel

import java.time.LocalDate

data class BloodRequest(
    var patientname: String,
    var bloodgroup: String,
    var age: Int,
    var units: Int,
    var reason: String,
    var gender: String,
    var phone: Long,
    var location: String,
    var requiredbefore: LocalDate,
    val id: Long = 0L
)
