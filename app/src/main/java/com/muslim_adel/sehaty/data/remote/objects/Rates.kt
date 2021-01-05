package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("id")
    var id: Long,
    @SerializedName("rate")
    var rate: Int,
    @SerializedName("comment")
    var comment: String,
    @SerializedName("person_name")
    var person_name: String,
    @SerializedName("date")
    var date: String
)