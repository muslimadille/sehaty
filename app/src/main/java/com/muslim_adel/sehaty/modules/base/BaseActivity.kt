package com.muslim_adel.sehaty.modules.base

import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import com.muslim_adel.sehaty.utiles.ComplexPreferences
import com.muslim_adel.sehaty.utiles.Q
import java.util.*

open class BaseActivity : AppCompatActivity() {
    var preferences: ComplexPreferences? = null

    companion object {
        public var dLocale: Locale? = null

    }

    init {
        preferences = ComplexPreferences.getComplexPreferences(this, Q.PREF_FILE, Q.MODE_PRIVATE)
        updateConfig(this)
    }

    fun updateConfig(wrapper: ContextThemeWrapper) {
        if(dLocale==Locale("") ) // Do nothing if dLocale is null
            return

        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }
}