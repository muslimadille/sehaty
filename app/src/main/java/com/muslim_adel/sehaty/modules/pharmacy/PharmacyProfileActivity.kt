package com.sehakhanah.patientapp.modules.pharmacy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_offer_details.*
import kotlinx.android.synthetic.main.activity_pharmacy_profile.*
import kotlinx.android.synthetic.main.offers_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PharmacyProfileActivity : BaseActivity() {
    var lat=0.0
    var lng=0.0
    var labName=""
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_profile)
        initBottomNavigation()
        labObserver()
        navToMap()
    }
    private fun setPageData(pharm:PharmacyOffer){
        if (preferences!!.getString("language","")=="Arabic"){
            pharm_title_txt!!.text=pharm.pharmacy.pharmacy_name_ar
            address!!.text=pharm.pharmacy.address_ar
            pharm_info_txt.text=pharm.pharmacy.about_ar
            pharm_doc_name.text=pharm.pharmacy.firstName_ar+" "+pharm.pharmacy.lastName_ar
        }else{
            pharm_title_txt!!.text=pharm.pharmacy.pharmacy_name_en
            address!!.text=pharm.pharmacy.address_en
            pharm_info_txt.text=pharm.pharmacy.about_en
            pharm_doc_name.text=pharm.pharmacy.firstName_en+" "+pharm.pharmacy.lastName_en


        }


        pharm_show_more_txt.setOnClickListener {
            if(pharm_show_more_txt.text==getString(R.string.more)){
                pharm_info_txt.maxLines=20
                pharm_show_more_txt.text=getString(R.string.less)
            }else{
                pharm_info_txt.maxLines=5
                pharm_show_more_txt.text=getString(R.string.more)

            }
        }
    }
    private fun labObserver() {
        onObserveStart()
        val id =intent.getLongExtra("pharm_id",-1)
        val url = Q.GET_PHARM_BY_ID_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchPharmacyById(url)
            .enqueue(object : Callback<BaseResponce<PharmacyOffer>> {
                override fun onFailure(call: Call<BaseResponce<PharmacyOffer>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<PharmacyOffer>>,
                    response: Response<BaseResponce<PharmacyOffer>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                lat=it.pharmacy.lat
                                lng=it.pharmacy.lng
                                labName=it.pharmacy.pharmacy_name_ar
                                Glide.with(this@PharmacyProfileActivity).applyDefaultRequestOptions(
                                    RequestOptions()
                                        .placeholder(R.drawable.person_ic)
                                        .error(R.drawable.person_ic))
                                    .load(it.featured)
                                    .centerCrop()
                                    .into(pharm_pager_Slider)
                                setPageData(it)
                                onObserveSuccess()
                            }
                        } else {
                            Toast.makeText(this@PharmacyProfileActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@PharmacyProfileActivity, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    private fun onObserveStart(){
        pharm_progrss_lay.visibility= View.VISIBLE
        pharm_details_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        pharm_progrss_lay.visibility= View.GONE
        pharm_details_lay.visibility= View.VISIBLE
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
        bottomNavigationView20.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView20.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private fun navToMap(){
        val zoom=40
        var lable=labName
        val intent= Intent(Intent.ACTION_VIEW)
        lab_location_btn.setOnClickListener {
            intent.data= Uri.parse("geo:0,0?z=$zoom&q=$lat,$lng,$lable")
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
        }
    }
}