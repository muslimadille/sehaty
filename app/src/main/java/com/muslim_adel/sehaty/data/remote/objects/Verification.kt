package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Verification (
    @SerializedName("status")
    var status: Int,
    @SerializedName("phonenumber")
    var phonenumber: String,
    @SerializedName("title_ar")
    var title_ar: String,
    @SerializedName("price")
    var price: Long,
    @SerializedName("featured")
    var featured: String,
    @SerializedName("pharmacy_id")
    var pharmacy_id: String,
    @SerializedName("pharmacy")
    var pharmacy: Pharmacy

)