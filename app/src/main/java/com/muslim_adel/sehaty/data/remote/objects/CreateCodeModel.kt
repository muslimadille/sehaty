package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName
import com.sehakhanah.patientapp.data.remote.objects.AppointmentData
import com.sehakhanah.patientapp.data.remote.objects.User

data class CreateCodeModel (
    @SerializedName("status")
    var status: Long,
    @SerializedName("user")
    var user: User,

    )