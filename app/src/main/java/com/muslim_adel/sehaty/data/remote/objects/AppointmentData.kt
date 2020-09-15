package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

class AppointmentData (
    @SerializedName("id")
    var id: Long,
    @SerializedName("user_id")
    var user_id: Long,
    @SerializedName("doctor_id")
    var doctor_id: Long,
    @SerializedName("booking_date")
    var booking_date: String,
    @SerializedName("status_id")
    var status_id: Int,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String
)