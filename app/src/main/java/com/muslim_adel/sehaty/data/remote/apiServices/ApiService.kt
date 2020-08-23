package com.muslim_adel.sehaty.data.remote.apiServices

import android.text.Editable
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.LoginResponce
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.data.remote.objects.Specialties
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
        @Field("phonenumber") phonenumber: String,
        @Field("birthday") birthday: String,
        @Field("gender") gender:String): Call<LoginResponce>
    @GET(Q.SPECIALTY_LIST_API)
    fun fitchSpecialitiesList():Call<BaseResponce<List<Specialties>>>
    @GET(Q.REAGONS_LIST_API)
    fun fitchReagonsList():Call<BaseResponce<List<Reagons>>>
}