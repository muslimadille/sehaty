package com.muslim_adel.sehaty.utiles

import java.util.*

object Q {
    /*************** Locales  ***********/
    /**..................api......................................***/
    const val BASE_URL = "https://obank.itcomunity.com/api/"
    const val LOGIN_API = "login"
    const val REGISTER_API = "register"
    const val SPECIALTY_LIST_API = "specialties"
    const val REAGONS_LIST_API = "areas"
    const val DOCTORS_LIST_API = "search"
    const val DOCTORS_DATES_API = "dates"
    const val DOCTORS_RATES_API = "ratings"
    const val DOCTOR_BY_ID_API="doctor/show"

    const val BOOKING_API = "reservation"
    const val GET_BOOKING_API="user/booking"
    const val BOOKING_CANCEL_API="user/booking/cancle"

    const val AVATAR_PATH="https://www.obank.itcomunity.com/"
    /*****************************************************************/


    const val MODE_PRIVATE = 0
    const val PREF_FILE = "sehaty_pref"
    const val SELECTED_LOCALE_PREF = "sehaty_selected_locale"

    const val LOCALE_AR_INDEX = 0
    const val LOCALE_EN_INDEX = 1
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

}