package com.muslim_adel.sehaty.modules.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.introSlider.IntroWizardActivity
import com.muslim_adel.sehaty.modules.register.LoginActivity
import com.muslim_adel.sehaty.utiles.Q
import java.util.*

class SplashActivity : BaseActivity() {
    private var change=""
    private var isLogin=false
    var isFristTime=true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        isFristTime=preferences!!.getBoolean(Q.IS_FIRST_TIME, Q.FIRST_TIME)
        if (isFristTime) {
            preferences!!.putString("language", "Arabic")
            preferences!!.commit()

        }
        setLocalization()
        handelSpalash()

    }
    private fun setLocalization(){
        val language = preferences!!.getString("language", "en")
        if (language =="Arabic") {
            change="ar"
        } else if (language=="English" ) {
            change = "en"
        }else {
            change =""
        }
        dLocale = Locale(change) //set any locale you want here

    }
    private fun handelSpalash(){

        Handler().postDelayed({
            if (isFristTime) {
                preferences!!.putString("language", "Arabic")
                preferences!!.commit()
                val intent = Intent(this@SplashActivity, IntroWizardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}