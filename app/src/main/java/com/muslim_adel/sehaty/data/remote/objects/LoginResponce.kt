package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class LoginResponce (
    @SerializedName("success")
    var success: Boolean,

   /* @SerializedName("message")
    var message: Message,*/

    @SerializedName("data")
    var data: LoginData
)