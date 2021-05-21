package com.sehakhanah.patientapp.data.remote.apiServices

import com.muslim_adel.sehaty.data.remote.objects.CreateCodeModel
import com.muslim_adel.sehaty.data.remote.objects.GenerateToken
import com.muslim_adel.sehaty.data.remote.objects.SocialLoginRespose
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.data.remote.objects.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST(Q.LOGIN_API)
    fun login(@Query("email")email:String, @Query("password")password:String): Call<LoginResponce>

    @POST(Q.REGISTER_API)
    @FormUrlEncoded
    fun userregister(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("phonenumber") phonenumber:String,
        @Field("birthday") birthday: String,
        @Field("gender_id") gender_id:String): Call<BaseResponce<LoginData>>

    @POST(Q.VERIFICATION_API)
    @FormUrlEncoded
    fun userVerification(
        @Field("phonenumber") phonenumber:String,
        @Field("user_type") user_type:String): Call<BaseResponce<Verification>>

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
    @POST(Q.BOOKING_API)
    fun sendBook(@Query("name") name:String,
                 @Query("email") email:String,
                 @Query("phonenumber") phonenumber: String,
                 @Query("doctor_id") doctor_id:Int,
                 @Query("checkbox") checkbox:Int,
                 @Query("booking_date") booking_date:String): Call<BaseResponce<Booking>>

    @POST(Q.OFFER_BOOKING_API)
    fun sendOfferBook(@Query("name") name:String,
                 @Query("email") email:String,
                 @Query("phonenumber") phonenumber: String,
                 @Query("offer_id") offer_id:Int,
                 @Query("checkbox") checkbox:Int,
                 @Query("booking_date") booking_date:String): Call<BaseResponce<Booking>>
    @POST(Q.LAB_BOOKING_API)
    fun sendLabBook(@Query("name") name:String,
                      @Query("email") email:String,
                      @Query("phonenumber") phonenumber: String,
                      @Query("laboratory_id") laboratory_id:Int,
                      @Query("checkbox") checkbox:Int,
                      @Query("booking_date") booking_date:String): Call<BaseResponce<Booking>>
    @GET
    fun fitchDoctorRatesList(@Url url:String):Call<BaseResponce<List<Rates>>>
    @GET
    fun fitchDoctorById(@Url url:String):Call<BaseResponce<Doctor>>
    @GET
    fun fitchUserById(@Url url:String):Call<BaseResponce<User>>

    @GET(Q.GET_BOOKING_API)
    fun fitchBookingList():Call<BaseResponce<Appointment>>
    @GET(Q.GET_LAB_BOOKINGS_API)
    fun fitchLabBookingList():Call<BaseResponce<Appointment>>
    @GET(Q.GET_OFFERS_BOOKINGS_API)
    fun fitchOffersBookingList():Call<BaseResponce<Appointment>>
    @GET
    fun bookingCancel(@Url url:String):Call<BaseResponce<AppointmentData>>
    @GET(Q.DOCTORS_LIST_API)
    fun fitchAllDoctorsList():Call<BaseResponce<Search>>
    @GET(Q.ABOUT_US_API)
    fun fitchAboutUs():Call<BaseResponce<List<AboutUsData>>>
    @GET(Q.OFFER_SLIDER_IMAGES_API)
    fun fitchOffersSLiderImages():Call<BaseResponce<List<OfferSlider>>>
    @GET(Q.OFFER_CATEGORIES_API)
    fun fitchOffersSGategories():Call<BaseResponce<List<OffersCategory>>>

    @GET(Q.OFFERS_MOST_REQUEST_API)
    fun fitchOffersMostRequest():Call<BaseResponce<List<Offer>>>

    @GET
    fun fitchOfferById(@Url url:String):Call<BaseResponce<Offer>>

    @GET
    fun fitchOfferrDatesList(@Url url:String):Call<BaseResponce<Dates>>
    @GET
    fun fitchMainCategoryOffersList(@Url url:String):Call<BaseResponce<List<Offer>>>

    @GET(Q.PARMACY_OFFERS_API)
    fun fitchPharmacyOffers(@Query("area_id")area_id:Int):Call<BaseResponce<List<PharmacyOffer>>>
    @GET(Q.PARMACY_OFFERS_API)
    fun fitchAllPharmacyOffers():Call<BaseResponce<List<PharmacyOffer>>>
    @GET(Q.PARMACY_OFFERS_API)
    fun fitchPharmacyOffersByName(@Query("name")name:String):Call<BaseResponce<List<PharmacyOffer>>>

    @GET(Q.ALL_LABS_API)
    fun fitchAllLabsList():Call<BaseResponce<LabsSearch>>
    @GET(Q.LABS_SEARCH_API)
    fun fitchLabsByNameList(@Query("name")name:String):Call<BaseResponce<LabsSearch>>
    @GET(Q.LABS_SEARCH_API)
    fun fitchLabsByRegionList(@Query("area_id")area_id:Int):Call<BaseResponce<LabsSearch>>
    @GET
    fun fitchLabById(@Url url:String):Call<BaseResponce<Laboratory>>
    @GET
    fun fitchPharmacyById(@Url url:String):Call<BaseResponce<PharmacyOffer>>
    @POST(Q.UPDATE_PROFILE_API)
    fun updateProfile(@Query("name") name:String,
                      @Query("email") email:String,
                      @Query("phonenumber") phonenumber: String,
                      @Query("gender_id") gender_id:String,
                      @Query("birthday") birthday:String): Call<BaseResponce<User>>
    @POST(Q.SEND_CODE_API)
    @FormUrlEncoded
    fun sendVerificationNum(@Field("phonenumber") phonenumber:String,
                      @Field("user_type") user_type:String,
                      @Field("code") code:String): Call<BaseResponce<Verification>>

    @POST(Q.GET_TOKEN_API)
    @FormUrlEncoded
    fun generateToken(
        @Field("grant_type") grant_type:String,
        @Field("client_id") client_id:String,
        @Field("client_secret") client_secret:String,
        @Field("scope") scope:String): Call<GenerateToken>
    @POST(Q.SOCIAL_LOGIN_API)
    @FormUrlEncoded
    fun socialLogin(
        @Field("accessToken") email:String,
        @Field("provider") provider:String): Call<BaseResponce<SocialLoginRespose>>

    @POST(Q.GET_CODE)
    @FormUrlEncoded
    fun createCode(@Field("phonenumber") phonenumber:String,
                   @Field("user_type") user_type:String): Call<BaseResponce<CreateCodeModel>>
    @POST(Q.VERIFY_CODE)
    fun verifyCode(@Query("phonenumber") phonenumber:String,
                   @Query("user_type") user_type:String,
                   @Query("code") code:String): Call<LoginResponce>
    @GET
    fun verifyToken(@Url url: String): Call<BaseResponce<SocialLoginRespose>>
    @POST(Q.PASSWORD_RESET)
    @FormUrlEncoded
    fun passwordReset(@Field("phonenumber") phonenumber:String,
                      @Field("user_type") user_type:String,
                      @Field("token") token:String,
                      @Field("password") password:String,
                      @Field("password_confirmation") password_confirmation:String,): Call<BaseResponce<CreateCodeModel>>
    @GET(Q.PROBLEMS_LIST)
    fun getProblemsList():Call<BaseResponce<Dates>>

    @POST(Q.CONTACT_US)
    @FormUrlEncoded
    fun sendContactUs(@Field("name") name:String,
                   @Field("phone") phone:String,
                  @Field("email") email:String,
                  @Field("comments") comments:String): Call<BaseResponce<Any>>

}