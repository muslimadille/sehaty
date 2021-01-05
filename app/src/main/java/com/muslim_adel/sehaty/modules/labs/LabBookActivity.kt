package com.sehakhanah.patientapp.modules.labs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.doctors.booking.BookingSuccessActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.book_check_box
import kotlinx.android.synthetic.main.activity_booking.booking_btn
import kotlinx.android.synthetic.main.activity_booking.date_name_txt
import kotlinx.android.synthetic.main.activity_booking.mail_txt
import kotlinx.android.synthetic.main.activity_booking.phone_num
import kotlinx.android.synthetic.main.activity_booking.time_txt
import kotlinx.android.synthetic.main.activity_booking.username
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_doctor_profile.circleImageView
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_name_txt
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_specialty_txt
import kotlinx.android.synthetic.main.activity_doctor_profile.street_txt
import kotlinx.android.synthetic.main.activity_lab_book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabBookActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    var lab_id =0L
    var date_id =0
    var time_id =0
    var service_id =0L

    var booking_date=""
    var dayName=""
    var date=""
    var time=""
    var laboratoryServices=""
    var lab_name=""
    var lab_location=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_book)
        initBottomNavigation()
        getIntentValues()
        labObserver()
        onBookingClicked()
        setUserData()
    }
    private fun getIntentValues() {
        intent.getStringExtra("time")?.let {
            time=it
        }
        lab_id=intent.getLongExtra("lab_id",0L)
        date_id=intent.getIntExtra("date_id",0)
        time_id=intent.getIntExtra("time_id",0)
        service_id=intent.getLongExtra("service_id",0)
    }
    private fun setProfilrData(laboratory: Laboratory,dayName:String,date:String,tim:String,laboratoryServices:String){
        if (preferences!!.getString("language","")=="Arabic"){
            GlideObject.GlideProfilePic(this,laboratory.featured,circleImageView)
            doc_name_txt.text=laboratory.laboratory_name_ar
            doc_specialty_txt.text=laboratoryServices
            date_name_txt.text=dayName+" "+date
            time_txt.text=time
            street_txt.text=laboratory.address_ar
        }else{
            GlideObject.GlideProfilePic(this,laboratory.featured,circleImageView)
            doc_name_txt.text=laboratory.laboratory_name_en
            doc_specialty_txt.text=laboratoryServices
            date_name_txt.text=dayName+" "+date
            time_txt.text=time
            street_txt.text=laboratory.address_en
        }

    }
    private fun onBookingClicked(){
        booking_btn.setOnClickListener {
            bookDateObserver()
        }
    }
    private fun bookDateObserver() {
        //booking_date=datename.split(" ")[1]+" "+time//"2020-09-09 16:35"
        var name=username.text.toString()
        var phone=phone_num.text.toString()
        var email=mail_txt.text.toString()
        var chec=-1
        if (book_check_box.isChecked){
            chec=1
        }else{
            chec=0
        }
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).sendLabBook(name,email,phone,lab_id.toInt(),chec,booking_date)
            .enqueue(object : Callback<BaseResponce<Booking>> {
                override fun onFailure(call: Call<BaseResponce<Booking>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Booking>>,
                    response: Response<BaseResponce<Booking>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            done()
                        } else {
                            Toast.makeText(this@LabBookActivity, "faild", Toast.LENGTH_SHORT).show()
                        }


                    } else {
                        Toast.makeText(this@LabBookActivity, "faild", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
    private fun labObserver() {
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
                            if (preferences!!.getString("language","")=="Arabic"){
                                response.body()!!.data!!.let {
                                    lab_name=it.laboratory_name_ar
                                    lab_location=it.address_ar
                                    it.dates.forEach {date:Date->
                                        if(date.id==date_id){
                                            dayName=date.day_ar
                                            booking_date=date.date+" "+time
                                        }
                                    }
                                    it.laboratory_services.forEach { ser:LaboratoryServices->
                                        if(ser.id==service_id){
                                            laboratoryServices=ser.name_ar
                                        }
                                    }
                                    setProfilrData(it,dayName,date,time,laboratoryServices)



                                }
                            }else{
                                response.body()!!.data!!.let {
                                    lab_name=it.laboratory_name_en
                                    lab_location=it.address_en
                                    it.dates.forEach {date:Date->
                                        if(date.id==date_id){
                                            dayName=date.day_en
                                            date.times.forEach { tim:Times->
                                                if(tim.id==time_id){
                                                    time=tim.time
                                                    booking_date=date.date+" "+time
                                                }
                                            }
                                        }
                                    }
                                    it.laboratory_services.forEach { ser:LaboratoryServices->
                                        if(ser.id==service_id){
                                            laboratoryServices=ser.name_en
                                        }
                                    }
                                    setProfilrData(it,dayName,date,time,laboratoryServices)



                                }
                            }

                        } else {
                            Toast.makeText(this@LabBookActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@LabBookActivity, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }

    private fun done(){
        val intent = Intent(this@LabBookActivity, BookingSuccessActivity::class.java)
        intent.putExtra("key",2)
        intent.putExtra("booking_date",booking_date.toString())
        intent.putExtra("dayName",dayName.toString())
        intent.putExtra("date",date.toString())
        intent.putExtra("time",time.toString())
        intent.putExtra("lab_name",lab_name.toString())
        intent.putExtra("lab_location",lab_location.toString())
        intent.putExtra("laboratoryServices",laboratoryServices.toString())
        startActivity(intent)

    }
    private fun setUserData(){
        if(preferences!!.getBoolean(Q.IS_LOGIN,false)){
            book_check_box.visibility= View.VISIBLE

            username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
            phone_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
            mail_txt.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
            book_check_box.setOnClickListener {
                if(book_check_box.isChecked){
                    book_check_box.isChecked=false
                    username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
                    phone_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
                    mail_txt.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
                }else{
                    book_check_box.isChecked=true
                    username.setText("", TextView.BufferType.EDITABLE)
                    phone_num.setText("", TextView.BufferType.EDITABLE)
                    mail_txt.setText("", TextView.BufferType.EDITABLE)
                }
            }


        }else{
            book_check_box.visibility= View.GONE

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
        bottomNavigationView8.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView8.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}