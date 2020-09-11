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
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Date
import com.muslim_adel.sehaty.data.remote.objects.Dates
import com.muslim_adel.sehaty.data.remote.objects.Times
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.doctorProfile.DatesAdapter
import com.muslim_adel.sehaty.utiles.Q
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

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var timesList: MutableList<Times> = ArrayList()
    private var dateId = -1
    private var doctorDatesListAddapter: DatesAdabter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dates)
        getIntentValues()
        dateId = intent.getIntExtra("date_id", -1)
        initRVAdapter()
        doctorDateObserver()
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
                                            datename=date.day_ar+" "+date.date
                                            day_name.text=date.day_ar+" "+date.date
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

    }

}