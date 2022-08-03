package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName
import com.muslim_adel.enaya.data.remote.objects.AppointmentData
import com.muslim_adel.enaya.data.remote.objects.User

data class CreateCodeModel (
    @SerializedName("status")
    var status: Long,
    @SerializedName("user")
    var user: User,

    )