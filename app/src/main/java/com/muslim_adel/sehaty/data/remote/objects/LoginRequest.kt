package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("password")
    var password: String,

    @SerializedName("email")
    var email: String
)
