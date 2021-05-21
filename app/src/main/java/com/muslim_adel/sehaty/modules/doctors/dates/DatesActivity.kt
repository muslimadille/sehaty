package com.sehakhanah.patientapp.modules.doctors.dates

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_dates.*
import kotlinx.android.synthetic.main.activity_dates.bottomNavigationView
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatesActivity : BaseActivity() {
    var firstName_ar = ""
    var firstName_en = ""
    var lastName_ar = ""
    var lastName_en = ""
    var featured = ""
    var doctor_id = -1L
    var date_id = -1L
    var phonenumber = -1L
    var price = -1L
    var profissionalTitle_ar = ""
    var profissionalTitle_en = ""
    var ratingnum = 0F
    var address_ar = ""
    var address_en = ""
    var datename = ""
    var key =0
    var dateVal=""
    var landmark_ar = ""
    var landmark_en = ""
    var id=0L

    var lab_id =0L
    var service_id =0L



    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var timesList: MutableList<Times> = ArrayList()
    var dateId = -1
    private var doctorDatesListAddapter: DatesAdabter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dates)
        dateId = intent.getIntExtra("date_id", -1)
        key = intent.getIntExtra("key", 0)
        initBottomNavigation()
        if(key==1){
            offerDateObserver()
            getIntentValues()

        }else if(key==2){
            lab_id=intent.getLongExtra("lab_id",0L)
            service_id=intent.getLongExtra("service_id",0L)
            labsDateObserver()
        }
        else{
            getIntentValues()
            doctorDateObserver()
        }

        initRVAdapter()

    }

    private fun doctorDateObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchDoctorDatesList(Q.DOCTORS_DATES_API + "/${doctor_id}")
            .enqueue(object : Callback<BaseResponce<Dates>> {
                override fun onFailure(call: Call<BaseResponce<Dates>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(
                    call: Call<BaseResponce<Dates>>,
                    response: Response<BaseResponce<Dates>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.dates.let {
                                if (it.isNotEmpty()) {
                                    it.forEach {date:Date->
                                        if(date.id==dateId){
                                            if (preferences!!.getString("language","")=="Arabic"){
                                                datename=date.day_ar+" "+date.date
                                                day_name.text=date.day_ar+" "+date.date
                                            }else{
                                                datename=date.day_en+" "+date.date
                                                day_name.text=date.day_en+" "+date.date
                                            }

                                            date.times.forEach {time:Times->
                                                if(time.status=="1"){
                                                    timesList.add(time)
                                                }
                                            }
                                            doctorDatesListAddapter!!.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    Toast.makeText(
                                        this@DatesActivity,
                                        "",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }
                        } else {
                            Toast.makeText(this@DatesActivity, "faild", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@DatesActivity, "faild", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
    private fun offerDateObserver() {
         id =intent.getLongExtra("offer_id",-1)
        val url = Q.GET_OFFER_DATES_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchOfferrDatesList(url)
            .enqueue(object : Callback<BaseResponce<Dates>> {
                override fun onFailure(call: Call<BaseResponce<Dates>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(
                    call: Call<BaseResponce<Dates>>,
                    response: Response<BaseResponce<Dates>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.dates.let {
                                if (it.isNotEmpty()) {
                                    it.forEach {date:Date->
                                        if(date.id==dateId){
                                            if (preferences!!.getString("language","")=="Arabic"){
                                                datename=date.day_ar+" "+date.date
                                                day_name.text=date.day_ar+" "+date.date
                                            }else{
                                                datename=date.day_en+" "+date.date
                                                day_name.text=date.day_en+" "+date.date
                                            }

                                            date.times.forEach {time:Times->
                                                if(time.status=="1"){
                                                    timesList.add(time)
                                                }

                                            }

                                            doctorDatesListAddapter!!.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    Toast.makeText(
                                        this@DatesActivity,
                                        "",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }
                        } else {
                            Toast.makeText(this@DatesActivity, "faild", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@DatesActivity, "faild", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
    private fun labsDateObserver() {
        val id =intent.getLongExtra("lab_id",-1)
        val url = Q.GET_LAB_BY_ID_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchLabById(url)
            .enqueue(object : Callback<BaseResponce<Laboratory>> {
                override fun onFailure(call: Call<BaseResponce<Laboratory>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Laboratory>>,
                    response: Response<BaseResponce<Laboratory>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                it.dates.forEach {date: Date ->
                                    if(date.id==dateId){
                                        if (preferences!!.getString("language","")=="Arabic"){
                                            datename=date.day_ar+" "+date.date
                                            day_name.text=date.day_ar+" "+date.date
                                        }else{
                                            datename=date.day_en+" "+date.date
                                            day_name.text=date.day_en+" "+date.date
                                        }

                                        dateVal=date.date
                                        date.times.forEach {time:Times->
                                            if(time.status=="1"){
                                                timesList.add(time)
                                            }

                                        }

                                        doctorDatesListAddapter!!.notifyDataSetChanged()
                                    }

                                }
                            }
                        } else {
                            Toast.makeText(this@DatesActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@DatesActivity, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }

    private fun initRVAdapter() {
        doctorDatesListAddapter = DatesAdabter(this, timesList)
        val layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        dates_list.layoutManager = layoutManager
        dates_list.adapter = doctorDatesListAddapter
    }
    private fun getIntentValues() {
        firstName_ar = intent.getStringExtra("firstName_ar")!!
        firstName_en = intent.getStringExtra("firstName_en")!!
        lastName_ar = intent.getStringExtra("lastName_ar")!!
        lastName_en = intent.getStringExtra("lastName_en")!!
        featured=intent.getStringExtra("featured")!!
        doctor_id=intent.getLongExtra("doctor_id", -1)
        phonenumber=intent.getLongExtra("phonenumber", -1)
        price=intent.getLongExtra("price", -1)
        profissionalTitle_ar=intent.getStringExtra("profissionalTitle_ar")!!
        profissionalTitle_en=intent.getStringExtra("profissionalTitle_en")!!
        ratingnum=intent.getFloatExtra("rating", 0F)
        address_ar= intent.getStringExtra("address_ar")!!
        address_en=intent.getStringExtra("address_en")!!
        date_id=intent.getLongExtra("date_id", -1)

         landmark_ar = intent.getStringExtra("landmark_ar")!!
         landmark_en = intent.getStringExtra("landmark_en")!!


    }
    private fun initBottomNavigation(){

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",0)
                    startActivity(intent)
                }
                R.id.navigation_offers -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",1)
                    startActivity(intent)
                }
                R.id.navigation_appointment -> {
                    intent= Intent(this, MainActivity::class.java)
                    intent.putExtra("navK",2)
                    startActivity(intent)
                }
                R.id.navigation_extras->{
                    intent= Intent(this, MainActivity::class.java)
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