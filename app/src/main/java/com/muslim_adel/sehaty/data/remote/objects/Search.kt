package com.muslim_adel.sehaty.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Search (
    @SerializedName("search")
    var search: List<Doctor>
)

