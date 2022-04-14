package com.muslim_adel.sehaty.utiles

import com.sehakhanah.patientapp.data.remote.objects.CountryModel
import java.util.ArrayList

object Q {
    /*************** Locales  ***********/
    /**..................api......................................***/
    const val BASE_URL = "https://laravelapi.sehakhanah.com/api/"
    const val BASE_URL2 = "https://laravelapi.sehakhanah.com/"

    const val LOGIN_API = "login"
    const val REGISTER_API = "register"
    const val VERIFICATION_API = "send-message"

    const val SPECIALTY_LIST_API = "specialties"
    const val REAGONS_LIST_API = "areas"
    const val DOCTORS_LIST_API = "search"
    const val DOCTORS_DATES_API = "dates"
    const val DOCTORS_RATES_API = "ratings"
    const val DOCTOR_BY_ID_API="doctor/show"
    const val BOOKING_API = "reservation"
    const val GET_BOOKING_API="user/booking"
    const val GET_LAB_BOOKINGS_API="user/laboratory/booking"
    const val GET_OFFERS_BOOKINGS_API="user/offer/booking"




    const val BOOKING_CANCEL_API="user/booking/cancle"
    const val ABOUT_US_API="admin/aboutUs"
    const val OFFER_SLIDER_IMAGES_API="offer/slideshow"
    const val OFFER_CATEGORIES_API="offer/categories"
    const val OFFERS_MOST_REQUEST_API="offer/mostrequest"
    const val GET_OFFER_BY_ID_API="offers"
    const val GET_OFFER_DATES_API="offer/dates"
    const val OFFER_BOOKING_API = "offer/reservation"
    const val LAB_BOOKING_API = "laboratory/reservation"
    const val MAIN_CATEGORY_OFFERS_API = "offer-category"
    const val SUB_CATEGORY_OFFERS_API = "offer-subcategory"
    const val PARMACY_OFFERS_API = "pharmacy/offers"
    const val ALL_LABS_API = "laboratories"
    const val LABS_SEARCH_API = "laboratory/search"
    const val GET_LAB_BY_ID_API="laboratory/show"
    const val UPDATE_PROFILE_API="user/update"
    const val SEND_CODE_API="verify-user"
    const val GET_PHARM_BY_ID_API="pharmacy/offer/show"
    const val GET_TOKEN_API="oauth/token"
    const val SOCIAL_LOGIN_API="social"
    const val AVATAR_PATH="https://www.obank.itcomunity.com/"
    const val GET_CODE="password/create"
    const val VERIFY_CODE="password/verify"
    const val VERIFY_TOKEN="password/find/"
    const val PASSWORD_RESET="password/reset"
    const val PROBLEMS_LIST="problems"
    const val CONTACT_US="sendemail/contactus"
    const val COUNTRIES_API = "country"
    const val CONTACT_US_Data = "admin/contactUs"








    /*****************************************************************/


    const val MODE_PRIVATE = 0
    const val PREF_FILE = "sehaty_pref"
    const val SELECTED_LOCALE_PREF = "sehaty_selected_locale"

    const val LOCALE_AR_INDEX = 0
    const val LOCALE_EN_INDEX = 1
    var CONTACT_US_PHONE:Long=0

    var FIRST_TIME = true
    var IS_FIRST_TIME = "first_time"

    var IS_LOGIN="is_login"
    var USER_NAME="USER_NAME"
    var USER_PHONE="USER_PHONE"
    var USER_EMAIL="USER_EMAIL"
    var USER_ID="user_id"
    var USER_GENDER="gender_id"
    var USER_BIRTH="USER_BIRTH"


    const val FIRST_TIME_PREF = "ADW_first_time"
    var CURRENT_LANG=""
    //CONTRY VALUES
    var PHONE_KEY="+964"
    var CURNCY_NAME_AR="دينار"
    var CURNCY_NAME_EN="IQD"
    var countriesList= ArrayList<CountryModel>()
    lateinit var selectedCountry:CountryModel

}