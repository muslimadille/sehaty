package com.muslim_adel.sehaty.modules.contactUs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.AboutUsData
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.data.remote.objects.Search
import com.muslim_adel.sehaty.modules.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutUsActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        favDoctorsObserver()
    }
    private fun favDoctorsObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchAboutUs()
            .enqueue(object : Callback<BaseResponce<List<AboutUsData>>> {
                override fun onFailure(call: Call<BaseResponce<List<AboutUsData>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<AboutUsData>>>,
                    response: Response<BaseResponce<List<AboutUsData>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                val language = preferences!!.getString("language", "en")
                                if (language == "Arabic") {
                                    abut_us_txt.text =  HtmlCompat.fromHtml(it[0].aboutUs_en, 0)
                                } else {
                                    abut_us_txt.text = it[0].aboutUs_en
                                }
                            }
                        } else {
                            Toast.makeText(this@AboutUsActivity, "faid", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@AboutUsActivity, "faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
}