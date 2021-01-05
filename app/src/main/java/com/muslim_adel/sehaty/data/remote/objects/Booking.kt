package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Booking (
    @SerializedName("booking")
    var booking: AppointmentData
)

