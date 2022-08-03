package com.muslim_adel.enaya.modules.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.CountryModel
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import com.muslim_adel.enaya.modules.introSlider.IntroWizardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SplashActivity : BaseActivity() {
    private var change=""
    private var isLogin=false
    var isFristTime=true
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        isLogin=preferences!!.getBoolean(Q.IS_LOGIN,false)
        isFristTime=preferences!!.getBoolean(Q.IS_FIRST_TIME, Q.FIRST_TIME)
        getCountries()
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

    fun getCountries(){
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this)
            .getAllCountriesList()
            .enqueue(object : Callback<BaseResponce<List<CountryModel>>> {
                override fun onFailure(call: Call<BaseResponce<List<CountryModel>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<CountryModel>>>,
                    response: Response<BaseResponce<List<CountryModel>>>
                ) {
                    val myResponse = response.body()
                    if (myResponse!!.success) {
                        Q.countriesList.clear()
                        Q.countriesList.addAll(myResponse.data!!)
                        Q.countriesList.forEach{
                            if (preferences!!.getString("language","")=="English"){
                            }else{
                            }

                        }
                        Q.countriesList.clear()
                        Q.countriesList.addAll(myResponse.data!!)
                        Q.selectedCountry=Q.countriesList[0]
                        preferences!!.putInteger("COUNTRY_ID",Q.selectedCountry.id!!)
                        preferences!!.commit()
                    } else {

                    }

                }


            })
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