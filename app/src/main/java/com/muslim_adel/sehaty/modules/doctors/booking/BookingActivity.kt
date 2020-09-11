package com.muslim_adel.sehaty.modules.doctors.booking

import android.os.Bundle
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Booking
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.base.GlideObject
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_doctor_profile.circleImageView
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_name_txt
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_specialty_txt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingActivity : BaseActivity() {
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
    var time = ""

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        setProfilrData()
        onBookingClicked()

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
        datename=intent.getStringExtra("datename")!!
        time=intent.getStringExtra("timename")!!


    }
    private fun setProfilrData(){
        getIntentValues()
        GlideObject.GlideProfilePic(this,featured,circleImageView)
        doc_name_txt.text=firstName_ar+" "+lastName_ar
        doc_specialty_txt.text=profissionalTitle_ar
        date_name_txt.text=datename
        time_txt.text=time
        street_txt.text=streetName_ar
        price_txt.text=price.toString()
    }
    private fun onBookingClicked(){
        booking_btn.setOnClickListener {
            doctorDateObserver()
        }
    }
    private fun doctorDateObserver() {
        var booking_date="2020-09-09 16:35"
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
        apiClient.getApiService(this).sendBook(name,email,phone,doctor_id.toInt(),chec,booking_date)
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
                            Toast.makeText(this@BookingActivity, "success", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
}