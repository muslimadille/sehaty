package com.muslim_adel.sehaty.modules.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.introSlider.IntroWizardActivity
import java.util.*

class SplashActivity : BaseActivity() {
    private var change=""
    private var isLogin=false
    var isFristTime=true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        isLogin=preferences!!.getBoolean(Q.IS_LOGIN,false)
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
            Q.CURRENT_LANG="ar"
        } else if (language=="English" ) {
            change = "en"
            Q.CURRENT_LANG="en"
        }else if(language=="Kurdish") {
            change ="ur"
            Q.CURRENT_LANG="ur"
        }
        dLocale = Locale(change) //set any locale you want here

    }
    private fun handelSpalash(){

        Handler().postDelayed({
            if (true) {
                if(isLogin){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    preferences!!.putString("language", "Arabic")
                    preferences!!.commit()
                    val intent = Intent(this@SplashActivity, IntroWizardActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}