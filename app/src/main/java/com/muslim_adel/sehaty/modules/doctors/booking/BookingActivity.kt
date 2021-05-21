package com.sehakhanah.patientapp.modules.doctors.booking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Booking
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.bottomNavigationView
import kotlinx.android.synthetic.main.activity_booking.username
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_doctor_profile.circleImageView
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_name_txt
import kotlinx.android.synthetic.main.activity_doctor_profile.doc_specialty_txt
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.koin.ext.isInt
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
    var address_ar = ""
    var address_en = ""
    var datename = ""
    var time = ""

    var landmark_ar = ""
    var landmark_en = ""
    var key = 0



    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        setProfilrData()
        onBookingClicked()
        setUserData()
        initBottomNavigation()

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
        datename=intent.getStringExtra("datename")!!
        time=intent.getStringExtra("timename")!!
        landmark_ar = intent.getStringExtra("landmark_ar")!!
        landmark_en = intent.getStringExtra("landmark_en")!!
        key=intent.getIntExtra("key",0)!!


    }
    private fun setProfilrData(){
        getIntentValues()
        GlideObject.GlideProfilePic(this,featured,circleImageView)
        if (preferences!!.getString("language","")=="Arabic"){
            doc_name_txt.text=firstName_ar+" "+lastName_ar
            doc_specialty_txt.text=profissionalTitle_ar
            date_name_txt.text=datename
            time_txt.text=time
            street_txt.text=address_ar
            price_txt.text=price.toString()
        }else{
            doc_name_txt.text=firstName_en+" "+lastName_en
            doc_specialty_txt.text=profissionalTitle_en
            date_name_txt.text=datename
            time_txt.text=time
            street_txt.text=address_en
            price_txt.text=price.toString()+" "+getString(R.string.derham)
        }

    }
    private fun onBookingClicked(){
        booking_btn.setOnClickListener {
            if(key==1){
                offerDateObserver()

            }else{
                doctorDateObserver()
            }
        }
    }
    private fun doctorDateObserver() {
        var booking_date=datename.split(" ")[1]+" "+time//"2020-09-09 16:35"
        var name=username.text.toString()
        var phone=phone_num.text.toString()
        var email=mail_txt.text.toString()
        var chec=-1
        if (book_check_box.isChecked){
            chec=1
        }else{
            chec=0
        }
        if(!name.isInt()&&name.isNotEmpty()&&phone.length==10){
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
                                done()
                            } else {
                                Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                            }


                        } else {
                            Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                        }

                    }


                })
        }else{
            Toast.makeText(this, "الرجاء أدخل بيانات صحيحة", Toast.LENGTH_SHORT).show()
        }

    }
    private fun offerDateObserver() {
        var id=intent.getLongExtra("offer_id",0)
        var booking_date=datename.split(" ")[1]+" "+time//"2020-09-09 16:35"
        var name=username.text.toString()
        var phone=phone_num.text.toString()
        var email=mail_txt.text.toString()
        var chec=-1
        if (book_check_box.isChecked){
            chec=1
        }else{
            chec=0
        }
        if(!name.isInt()&&name.isNotEmpty()&&phone.isInt()&&phone.length==10){
            apiClient = ApiClient()
            sessionManager = SessionManager(this)
            apiClient.getApiService(this).sendOfferBook(name,email,phone,id.toInt(),chec,booking_date)
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
                                Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                            }


                        } else {
                            Toast.makeText(this@BookingActivity, "faild", Toast.LENGTH_SHORT).show()
                        }

                    }


                })

        }else{
            Toast.makeText(this, "الرجاء أدخل بيانات صحيحة", Toast.LENGTH_SHORT).show()
        }

    }
    private fun done(){
        val intent = Intent(this@BookingActivity, BookingSuccessActivity::class.java)
        intent.putExtra("firstName_ar",firstName_ar)
        intent.putExtra("firstName_en",firstName_en)
        intent.putExtra("lastName_ar",lastName_ar)
        intent.putExtra("lastName_en",lastName_en)
        intent.putExtra("landmark_ar",landmark_ar)
        intent.putExtra("landmark_en",landmark_en)
        intent.putExtra("phonenumber",phonenumber)
        intent.putExtra("price",price)
        intent.putExtra("profissionalTitle_ar",profissionalTitle_ar)
        intent.putExtra("profissionalTitle_en",profissionalTitle_en)
        intent.putExtra("address_ar",address_ar)
        intent.putExtra("address_en",address_en)
        intent.putExtra("datename",datename)!!
        intent.putExtra("timename",time)!!


        startActivity(intent)

    }
    private fun setUserData(){
        if(preferences!!.getBoolean(Q.IS_LOGIN,false)){
            book_check_box.visibility=View.VISIBLE

            username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
            phone_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
            mail_txt.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
            book_check_box.setOnClickListener {
                if(!book_check_box.isChecked){
                    username.setText(preferences!!.getString(Q.USER_NAME, ""), TextView.BufferType.EDITABLE)
                    phone_num.setText(preferences!!.getString(Q.USER_PHONE, ""), TextView.BufferType.EDITABLE)
                    mail_txt.setText(preferences!!.getString(Q.USER_EMAIL, ""), TextView.BufferType.EDITABLE)
                }else{
                    username.setText("", TextView.BufferType.EDITABLE)
                    phone_num.setText("", TextView.BufferType.EDITABLE)
                    mail_txt.setText("", TextView.BufferType.EDITABLE)
                }
            }


        }else{
            book_check_box.visibility=View.GONE

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
}