package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class ProblimExampleModel(
    @SerializedName("id")
    var id: Long,
    @SerializedName("name_ar")
    var name_ar: String,
    @SerializedName("name_en")
    var name_en: String,

)