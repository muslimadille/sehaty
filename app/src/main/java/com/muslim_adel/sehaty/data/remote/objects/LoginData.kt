package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("user")
    var user: User,
    @SerializedName("status")
    var status: Int,
    @SerializedName("token")
    var token: String )

