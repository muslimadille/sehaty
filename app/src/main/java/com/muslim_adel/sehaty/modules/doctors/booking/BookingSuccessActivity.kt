package com.muslim_adel.sehaty.modules.doctors.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_booking_success.*
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
    var streetName_ar = ""
    var streetName_en = ""
    var datename = ""
    var time = ""

    var apartmentNum_ar = ""
    var apartmentNum_en = ""
    var landmark_ar = ""
    var landmark_en = ""
    var buildingNum_ar = ""
    var role = ""
    var buildingNum_en = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_success)
        getIntentValues()
        setData()
        onAppointmentsClicked()
    }
    private fun getIntentValues() {
        firstName_ar = intent.getStringExtra("firstName_ar")!!
        firstName_en = intent.getStringExtra("firstName_en")!!
        lastName_ar = intent.getStringExtra("lastName_ar")!!
        lastName_en = intent.getStringExtra("lastName_en")!!
        doctor_id=intent.getLongExtra("doctor_id", -1)
        phonenumber=intent.getLongExtra("phonenumber", -1)
        price=intent.getLongExtra("price", -1)
        streetName_ar= intent.getStringExtra("streetName_ar")!!
        streetName_en=intent.getStringExtra("streetName_en")!!
        datename=intent.getStringExtra("datename")!!
        time=intent.getStringExtra("timename")!!

        apartmentNum_ar = intent.getStringExtra("apartmentNum_ar")!!
        apartmentNum_en =intent.getStringExtra("apartmentNum_en")!!
        landmark_ar = intent.getStringExtra("landmark_ar")!!
        landmark_en = intent.getStringExtra("landmark_en")!!
        buildingNum_ar = intent.getStringExtra("buildingNum_ar")!!
        role =intent.getStringExtra("role")!!
        buildingNum_en =intent.getStringExtra("buildingNum_en")!!


    }
    private fun setData(){
        doc_name.text=firstName_ar+" "+lastName_ar
        time_txt.text=time
        date_txt.text=datename
        address_txt.text=streetName_ar+"-"+landmark_ar+"-"+getString(R.string.building_num)+buildingNum_ar+"-"+getString(R.string.Apartment_num)+apartmentNum_ar
        costt_txt.text=price.toString()+" "+getString(R.string.derham)
    }
    private fun onAppointmentsClicked(){
        appointments_btn.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("key",true)
            startActivity(intent)
        }
    }

}