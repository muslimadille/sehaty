package com.muslim_adel.sehaty.modules.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.LoginData
import com.muslim_adel.sehaty.data.remote.objects.LoginRequest
import com.muslim_adel.sehaty.data.remote.objects.LoginResponce
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.introSlider.IntroWizardActivity
import com.muslim_adel.sehaty.modules.register.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        preferences!!.putString("language","Arabic")
        preferences!!.commit()
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)




    }
}