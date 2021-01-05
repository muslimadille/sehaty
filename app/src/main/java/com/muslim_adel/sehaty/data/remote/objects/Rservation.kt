package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Rservation (
    @SerializedName("booking")
    var booking: AppointmentData
)
