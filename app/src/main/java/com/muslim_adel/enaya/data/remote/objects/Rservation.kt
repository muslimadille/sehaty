package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Rservation (
    @SerializedName("booking")
    var booking: AppointmentData
)