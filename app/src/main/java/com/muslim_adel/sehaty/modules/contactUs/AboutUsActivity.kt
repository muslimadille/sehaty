package com.sehakhanah.patientapp.modules.contactUs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.AboutUsData
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Doctor
import com.sehakhanah.patientapp.data.remote.objects.Search
import com.sehakhanah.patientapp.modules.appointments.AppointmentsFragment
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.home.fragments.ExstarsFragment
import com.sehakhanah.patientapp.modules.home.fragments.HomeFragment
import com.sehakhanah.patientapp.modules.home.fragments.OffersFragment
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_about_us.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
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
        initBottomNavigation()
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
    private fun initBottomNavigation(){

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",0)
                    startActivity(intent)
                }
                R.id.navigation_offers -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",1)
                    startActivity(intent)
                }
                R.id.navigation_appointment -> {
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",2)
                    startActivity(intent)
                }
                R.id.navigation_extras->{
                    intent= Intent(this,MainActivity::class.java)
                    intent.putExtra("navK",3)
                    startActivity(intent)
                }
            }
            false
        }
        bottomNavigationView.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

}