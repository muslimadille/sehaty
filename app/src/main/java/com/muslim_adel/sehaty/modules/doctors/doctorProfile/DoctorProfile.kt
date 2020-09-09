package com.muslim_adel.sehaty.modules.doctors.doctorProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.doctorsList.DoctorsListAdapter
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_doctors_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorProfile : BaseActivity() {
     var firstName_ar = ""
     var firstName_en = ""
     var lastName_ar = ""
     var lastName_en = ""
     var aboutDoctor_ar = ""
     var aboutDoctor_en = ""
     var apartmentNum_ar = ""
     var apartmentNum_en = ""
     var area_id = -1L
     var buildingNum_ar = ""
     var featured = ""
     var gender_id = -1
     var id = -1L
     var landmark_ar = ""
     var landmark_en = ""
     var phonenumber = -1L
     var prefixTitle_id = -1L
     var price = -1L
     var profissionalDetails_id = -1L
     var profissionalTitle_ar = ""
     var profissionalTitle_en = ""
     var role = ""
     var rating = 0F
     var speciality_id = -1L
     var streetName_ar = ""
     var streetName_en = ""
     var visitor_num = -1
     var waiting_time = ""
     var buildingNum_en = ""

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var doctorDatesList: MutableList<Date> = ArrayList()

    private var doctorDatesListAddapter: DatesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)
        initRVAdapter()
        doctorDateObserver()
        setProfilrData()
    }

    private fun doctorDateObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchDoctorDatesList(Q.DOCTORS_DATES_API + "/37")
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
                                    doctorDatesList.addAll(it)
                                    doctorDatesListAddapter!!.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(
                                        this@DoctorProfile,
                                        "data empty",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }
                        } else {
                            Toast.makeText(this@DoctorProfile, "faild", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@DoctorProfile, "faild", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
    private fun initRVAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        dates_rv.layoutManager = layoutManager
        doctorDatesListAddapter = DatesAdapter(this, doctorDatesList)
        dates_rv.adapter = doctorDatesListAddapter
    }
    private fun getIntentValues() {
        firstName_ar = intent.getStringExtra("firstName_ar")!!
        firstName_en = intent.getStringExtra("firstName_en")!!
        lastName_ar = intent.getStringExtra("lastName_ar")!!
        lastName_en = intent.getStringExtra("lastName_en")!!
        aboutDoctor_ar = intent.getStringExtra("aboutDoctor_ar")!!
        aboutDoctor_en = intent.getStringExtra("aboutDoctor_en")!!
        apartmentNum_ar = intent.getStringExtra("apartmentNum_ar")!!
        apartmentNum_en = intent.getStringExtra("apartmentNum_en")!!
        buildingNum_ar = intent.getStringExtra("buildingNum_ar")!!
        area_id = intent.getLongExtra("area_id", -1)
        featured=intent.getStringExtra("featured")!!
        gender_id=intent.getIntExtra("gender_id", -1)
        id=intent.getLongExtra("id", -1)
        landmark_ar=intent.getStringExtra("landmark_ar")!!
        landmark_en=intent.getStringExtra("landmark_en")!!
        phonenumber=intent.getLongExtra("phonenumber", -1)
        prefixTitle_id=intent.getLongExtra("prefixTitle_id", -1)
        price=intent.getLongExtra("price", -1)
        profissionalDetails_id=intent.getLongExtra("profissionalDetails_id", -1)
        profissionalTitle_ar=intent.getStringExtra("profissionalTitle_ar")!!
        profissionalTitle_en=intent.getStringExtra("profissionalTitle_en")!!
        role= intent.getStringExtra("role")!!
        rating=intent.getFloatExtra("rating", 0F)
        speciality_id=intent.getLongExtra("speciality_id", -1)
        streetName_ar= intent.getStringExtra("streetName_ar")!!
        streetName_en=intent.getStringExtra("streetName_en")!!
        visitor_num=intent.getIntExtra("visitor_num", -1)
        waiting_time=intent.getStringExtra("waiting_time")!!
        buildingNum_en=intent.getStringExtra("buildingNum_en")!!
    }
    private fun setProfilrData(){
        getIntentValues()
        GlideObject.GlideProfilePic(this,featured,circleImageView)
        viewed_num.text=visitor_num.toString()
        doc_name_txt.text=firstName_ar+" "+lastName_ar
        doc_specialty_txt.text=profissionalTitle_ar
        ratingBar.rating=rating
        visitors.text=visitor_num.toString()
        waiting_time_txt.text=waiting_time
        cost_txt.text=price.toString()
        street_txt.text=streetName_ar
        doctor_info_txt.text=aboutDoctor_ar
        see_more_txt.setOnClickListener {
            if(see_more_txt.text==getString(R.string.more)){
                doctor_info_txt.maxLines=20
                see_more_txt.text=getString(R.string.less)
            }else{
                doctor_info_txt.maxLines=2
                see_more_txt.text=getString(R.string.more)

            }
        }
    }
}