package com.sehakhanah.patientapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class LabsSearch (
    @SerializedName("search")
    var search: List<Laboratory>
)