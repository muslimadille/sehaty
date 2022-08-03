package com.muslim_adel.enaya.modules.base

import android.app.Application
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.utiles.ComplexPreferences
import java.util.*

class App : Application() {
    var preferences: ComplexPreferences? = null

    override fun onCreate() {
        super.onCreate()

        var change = ""
        preferences = ComplexPreferences.getComplexPreferences(this, Q.PREF_FILE, Q.MODE_PRIVATE)
        val language = preferences!!.getString("language", "en")
        if (language =="Arabic") {
            change="ar"
        } else if (language=="English" ) {
            change = "en"
        }else {
            change =""
        }

        BaseActivity.dLocale = Locale(change) //set any locale you want here
    }
}