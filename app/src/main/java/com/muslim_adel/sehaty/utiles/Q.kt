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



    const val AVATAR_PATH="https://www.obank.itcomunity.com/"
    /*****************************************************************/

    val LOCALE_AR = Locale("ar")
    val LOCALE_EN = Locale.US!!
    var SELECTED_LOCALE =""




    const val MODE_PRIVATE = 0
    const val PREF_FILE = "sehaty_pref"
    const val SELECTED_LOCALE_PREF = "sehaty_selected_locale"

    var SELECTED_LOCALE_INDEX = 0
    const val LOCALE_AR_INDEX = 0
    const val LOCALE_EN_INDEX = 1
    var FIRST_TIME = true
    var IS_FIRST_TIME = "first_time"

    const val FIRST_TIME_PREF = "ADW_first_time"



}