package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName
import com.sehakhanah.patientapp.data.remote.objects.User

data class SocialLoginRespose (
    @SerializedName("user")
    var user: User,
    @SerializedName("token")
    var token: String,
)