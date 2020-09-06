package com.muslim_adel.sehaty.data.remote.apiServices

import android.text.Editable
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.utiles.Q
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST(Q.LOGIN_API)
    @FormUrlEncoded
    fun login(@Field("email")email:String, @Field("password")password:String): Call<LoginResponce>

    @POST(Q.REGISTER_API)
    @FormUrlEncoded
    fun userregister(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("phonenumber") phonenumber:Long,
        @Field("birthday") birthday: String,
        @Field("gender_id") gender_id:Int): Call<BaseResponce<LoginData>>
    @GET(Q.SPECIALTY_LIST_API)
    fun fitchSpecialitiesList():Call<BaseResponce<List<Specialties>>>
    @GET(Q.REAGONS_LIST_API)
    fun fitchReagonsList():Call<BaseResponce<List<Reagons>>>
    @GET(Q.DOCTORS_LIST_API)
    fun fitchDoctorsList(@Query("specialty_id")specialty_id:Int,@Query("area_id")area_id:Int):Call<BaseResponce<Search>>
    @GET(Q.DOCTORS_LIST_API)
    fun fitchDoctorsList(@Query("name")name:String):Call<BaseResponce<Search>>
    @GET
    fun fitchDoctorDatesList(@Url url:String):Call<BaseResponce<Dates>>
}