package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class LoginResponce (
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("message")
    var message:Any,


    @SerializedName("data")
    var data: LoginData
)