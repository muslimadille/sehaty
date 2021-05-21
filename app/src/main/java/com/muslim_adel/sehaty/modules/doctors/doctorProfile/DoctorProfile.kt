package com.sehakhanah.patientapp.modules.doctors.doctorProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_doctor_profile.bottomNavigationView
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
     var area_id = -1L
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
     var rating = 0F
     var speciality_id = -1L
     var address_ar = ""
     var address_en = ""
     var visitor_num = -1
     var waiting_time = ""
    var lat=0.0
    var lng=0.0

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var doctorDatesList: MutableList<Date> = ArrayList()
    private var doctorRatesList: MutableList<Rates> = ArrayList()


    private var doctorDatesListAddapter: DatesAdapter? = null
    private var doctorRatesListAddapter: RatesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)
        initRVAdapter()
        initBottomNavigation()
        doctorDateObserver()
        doctorRatesObserver()
        onFavoritClicked()
        navToMap()
    }

    private fun doctorDateObserver() {
        setProfilrData()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        val url = Q.DOCTORS_DATES_API + "/${id}"
        apiClient.getApiService(this).fitchDoctorDatesList(url)
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
                                        if(date.status==1){
                                            doctorDatesList.add(date)
                                        }
                                    }
                                    doctorDatesListAddapter!!.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(
                                        this@DoctorProfile,
                                        "",
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
    private fun doctorRatesObserver() {
        val url = Q.DOCTORS_RATES_API + "/${id}"
        apiClient.getApiService(this).fitchDoctorRatesList(url)
            .enqueue(object : Callback<BaseResponce<List<Rates>>> {
                override fun onFailure(call: Call<BaseResponce<List<Rates>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<Rates>>>,
                    response: Response<BaseResponce<List<Rates>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    doctorRatesList.addAll(it)
                                    doctorRatesListAddapter!!.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(
                                        this@DoctorProfile,
                                        "",
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
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        dates_rv.layoutManager = layoutManager1
        doctorDatesListAddapter = DatesAdapter(this, doctorDatesList)
        dates_rv.adapter = doctorDatesListAddapter

        rates_rv.layoutManager = layoutManager2
        doctorRatesListAddapter = RatesAdapter(this, doctorRatesList)
        rates_rv.adapter = doctorRatesListAddapter


    }
    private fun getIntentValues() {
        firstName_ar = intent.getStringExtra("firstName_ar")!!
        firstName_en = intent.getStringExtra("firstName_en")!!
        lastName_ar = intent.getStringExtra("lastName_ar")!!
        lastName_en = intent.getStringExtra("lastName_en")!!
        aboutDoctor_ar = intent.getStringExtra("aboutDoctor_ar")!!
        aboutDoctor_en = intent.getStringExtra("aboutDoctor_en")!!

        area_id = intent.getLongExtra("area_id", -1)
        featured=intent.getStringExtra("featured")!!
        gender_id=intent.getIntExtra("gender_id", -1)
        id=intent.getLongExtra("doctor_id", -1L)
        landmark_ar=intent.getStringExtra("landmark_ar")!!
        landmark_en=intent.getStringExtra("landmark_en")!!
        phonenumber=intent.getLongExtra("phonenumber", -1)
        prefixTitle_id=intent.getLongExtra("prefixTitle_id", -1)
        price=intent.getLongExtra("price", -1)
        profissionalDetails_id=intent.getLongExtra("profissionalDetails_id", -1)
        profissionalTitle_ar=intent.getStringExtra("profissionalTitle_ar")!!
        profissionalTitle_en=intent.getStringExtra("profissionalTitle_en")!!
        rating=intent.getFloatExtra("rating", 0F)
        speciality_id=intent.getLongExtra("speciality_id", -1)
        address_ar= intent.getStringExtra("address_ar")!!
        address_en=intent.getStringExtra("address_en")!!
        visitor_num=intent.getIntExtra("visitor_num", -1)
        waiting_time=intent.getStringExtra("waiting_time")!!
        lat=intent.getDoubleExtra("lat",0.0)
        lng=intent.getDoubleExtra("lng",0.0)
    }
    private fun setProfilrData(){
        getIntentValues()
        if(id==preferences!!.getLong("$id",-1)){
            favorit_btn.setImageResource(R.drawable.heart_solid)
        }else{
            favorit_btn.setImageResource(R.drawable.hart_border)
        }
        GlideObject.GlideProfilePic(this,featured,circleImageView)
        if (preferences!!.getString("language","")=="Arabic"){
            viewed_num.text=visitor_num.toString()
            doc_name_txt.text=firstName_ar+" "+lastName_ar
            doc_specialty_txt.text=profissionalTitle_ar
            ratingBar.rating=rating
            visitors.text=visitor_num.toString()
            waiting_time_txt.text=waiting_time
            cost_txt.text=price.toString()
            street_txt.text=address_ar
            doctor_info_txt.text=aboutDoctor_ar
        }else{
            viewed_num.text=visitor_num.toString()
            doc_name_txt.text=firstName_en+" "+lastName_en
            doc_specialty_txt.text=profissionalTitle_en
            ratingBar.rating=rating
            visitors.text=visitor_num.toString()
            waiting_time_txt.text=waiting_time
            cost_txt.text=price.toString()
            street_txt.text=address_en
            doctor_info_txt.text=aboutDoctor_en
        }


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
    private fun onFavoritClicked(){


        favorit_btn.setOnClickListener {
            if(id==preferences!!.getLong("$id",-1)){
                preferences!!.putLong("$id",-1)
                preferences!!.commit()
                favorit_btn.setImageResource(R.drawable.hart_border)
            }else{
                preferences!!.putLong("$id",id)
                preferences!!.commit()
                favorit_btn.setImageResource(R.drawable.heart_solid)
            }

        }
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
    private fun navToMap(){
        val zoom=10
        var lable=firstName_ar
        val intent= Intent(Intent.ACTION_VIEW)
        doc_location_btn.setOnClickListener {
            intent.data= Uri.parse("geo:0,0?z=$zoom&q=$lat,$lng,$lable")
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
        }
    }
}