package com.muslim_adel.enaya.modules.doctors.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.base.GlideObject
import com.muslim_adel.enaya.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_booking_success.*
import kotlinx.android.synthetic.main.activity_booking_success.bottomNavigationView
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_doctor_profile.*

class BookingSuccessActivity : BaseActivity() {
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
    var key=0

    var booking_date=""
    var dayName=""
    var date=""
    var laboratoryServices=""
    var lab_location=""
    var lab_name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_success)
        initBottomNavigation()

        key=intent.getIntExtra("key",0)
        if(key!=2){
            getIntentValues()
            setData()
            onAppointmentsClicked()
        }else{
            getLabIntentValues()
            setLabData()
            onDoneClicked()
        }

    }
    private fun getIntentValues() {
        firstName_ar = intent.getStringExtra("firstName_ar")!!
        firstName_en = intent.getStringExtra("firstName_en")!!
        lastName_ar = intent.getStringExtra("lastName_ar")!!
        lastName_en = intent.getStringExtra("lastName_en")!!
        doctor_id=intent.getLongExtra("doctor_id", -1)
        phonenumber=intent.getLongExtra("phonenumber", -1)
        price=intent.getLongExtra("price", -1)
        address_ar= intent.getStringExtra("address_ar")!!
        address_en=intent.getStringExtra("address_en")!!
        datename=intent.getStringExtra("datename")!!
        time=intent.getStringExtra("timename")!!
        landmark_ar = intent.getStringExtra("landmark_ar")!!
        landmark_en = intent.getStringExtra("landmark_en")!!


    }
    private fun getLabIntentValues() {
        booking_date = intent.getStringExtra("booking_date")!!
        dayName = intent.getStringExtra("dayName")!!
        date = intent.getStringExtra("date")!!
        laboratoryServices = intent.getStringExtra("laboratoryServices")!!
        lab_name=intent.getStringExtra("lab_name")!!
        lab_location=intent.getStringExtra("lab_location")!!
        time=intent.getStringExtra("time")!!
    }
    private fun setData(){
        if (preferences!!.getString("language","")=="Arabic"){
            doc_name.text=firstName_ar+" "+lastName_ar
            time_txt.text=time
            date_txt.text=datename
            address_txt.text=address_ar+"-"+landmark_ar
            costt_txt.text=price.toString()+" "+ Q.CURNCY_NAME_AR
        }else{
            doc_name.text=firstName_en+" "+lastName_en
            time_txt.text=time
            date_txt.text=datename
            address_txt.text=address_en+"-"+landmark_en
            costt_txt.text=price.toString()+" "+Q.CURNCY_NAME_EN
        }

    }
    private fun setLabData(){
        doc_name.text=lab_name
        service_name.text=laboratoryServices
        service_name.visibility=View.VISIBLE
        cost_lay.visibility=View.GONE
        appointments_btn.setText(getString(R.string.done))
        time_txt.text=time
        date_txt.text=dayName+" "+date
        address_txt.text=lab_location

    }
    private fun onAppointmentsClicked(){
        appointments_btn.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("key",true)
            startActivity(intent)
        }
    }
    private fun onDoneClicked(){
        appointments_btn.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("key",false)
            startActivity(intent)
        }
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