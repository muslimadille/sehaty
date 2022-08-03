package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName
import com.muslim_adel.enaya.data.remote.objects.User

data class SocialLoginRespose (
    @SerializedName("user")
    var user: User,
    @SerializedName("token")
    var token: String,
)