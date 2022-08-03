package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class VisitorsNumber (
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("data")
    var data: Int,
    @SerializedName("message")
    var message: String,
)