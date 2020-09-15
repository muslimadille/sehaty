package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Appointment (
    @SerializedName("booking")
    var booking: ArrayList<AppointmentData>
)
