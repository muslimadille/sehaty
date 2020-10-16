package com.muslim_adel.sehaty.modules.doctors.dates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.doctorProfile.DatesAdapter
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_dates.*
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
    var streetName_ar = ""
    var streetName_en = ""
    var datename = ""
    var key =0
    var dateVal=""



    var apartmentNum_ar = ""
    var apartmentNum_en = ""
    var landmark_ar = ""
    var landmark_en = ""
    var buildingNum_ar = ""
    var role = ""
    var buildingNum_en = ""
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

                                            timesList.addAll(date.times)
                                            doctorDatesListAddapter!!.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    Toast.makeText(
                                        this@DatesActivity,
                                        "data empty",
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
                                            timesList.addAll(date.times)
                                            doctorDatesListAddapter!!.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    Toast.makeText(
                                        this@DatesActivity,
                                        "data empty",
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
                                        timesList.addAll(date.times)
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
        streetName_ar= intent.getStringExtra("streetName_ar")!!
        streetName_en=intent.getStringExtra("streetName_en")!!
        date_id=intent.getLongExtra("date_id", -1)

         apartmentNum_ar = intent.getStringExtra("apartmentNum_ar")!!
         apartmentNum_en =intent.getStringExtra("apartmentNum_en")!!
         landmark_ar = intent.getStringExtra("landmark_ar")!!
         landmark_en = intent.getStringExtra("landmark_en")!!
         buildingNum_ar = intent.getStringExtra("buildingNum_ar")!!
         role =intent.getStringExtra("role")!!
         buildingNum_en =intent.getStringExtra("buildingNum_en")!!


    }


}