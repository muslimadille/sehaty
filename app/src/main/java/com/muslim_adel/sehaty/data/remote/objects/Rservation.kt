package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Rservation (
    @SerializedName("booking")
    var booking: AppointmentData
)
