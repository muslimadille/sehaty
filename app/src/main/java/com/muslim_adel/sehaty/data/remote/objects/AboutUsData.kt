package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class AboutUsData (
    @SerializedName("aboutUs_en")
    var aboutUs_en: String,
    @SerializedName("aboutUs_ar")
    var aboutUs_ar: String
)