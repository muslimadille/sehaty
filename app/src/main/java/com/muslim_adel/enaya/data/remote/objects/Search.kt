package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Search (
    @SerializedName("search")
    var search: List<Doctor>
)

