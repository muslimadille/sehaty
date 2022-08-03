package com.muslim_adel.enaya.data.remote.objects

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("id")
    var id: Long,
    @SerializedName("featured")
    var featured: String,
    @SerializedName("firstName_en")
    var firstName_en: String,
    @SerializedName("firstName_ar")
    var firstName_ar: String,
    @SerializedName("lastName_en")
    var lastName_en: String,
    @SerializedName("lastName_ar")
    var lastName_ar: String,
    @SerializedName("phonenumber")
    var phonenumber: Long,
    @SerializedName("gender_id")
    var gender_id: Int,
    @SerializedName("speciality_id")
    var speciality_id: Long,
    @SerializedName("prefixTitle_id")
    var prefixTitle_id: Long,
    @SerializedName("profissionalDetails_id")
    var profissionalDetails_id: Long,
    @SerializedName("profissionalTitle_en")
    var profissionalTitle_en: String,
    @SerializedName("profissionalTitle_ar")
    var profissionalTitle_ar: String,
    @SerializedName("aboutDoctor_ar")
    var aboutDoctor_ar: String,
    @SerializedName("aboutDoctor_en")
    var aboutDoctor_en: String,
    @SerializedName("price")
    var price: Long,
    @SerializedName("address_en")
    var address_en: String,
    @SerializedName("address_ar")
    var address_ar: String,
    @SerializedName("landmark_en")
    var landmark_en: String,
    @SerializedName("landmark_ar")
    var landmark_ar: String,
    @SerializedName("waiting_time")
    var waiting_time: String,
    @SerializedName("rating")
    var rating: Float,
    @SerializedName("visitor_num")
    var visitor_num: Int,
    @SerializedName("num_of_day")
    var num_of_day: Int,
    @SerializedName("area_id")
    var area_id: Long,
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lng")
    var lng: Double

)