package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class GenerateToken (
    @SerializedName("token_type")
    var token_type: String,
    @SerializedName("expires_in")
    var expires_in: Long,
    @SerializedName("access_token")
    var access_token: String,

)