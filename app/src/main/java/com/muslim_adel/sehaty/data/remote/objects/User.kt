package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String,
    @SerializedName("phonenumber")
    var phonenumber: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("gender_id")
    var gender_id: Int,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String,
    @SerializedName("code")
    var code: Long


)