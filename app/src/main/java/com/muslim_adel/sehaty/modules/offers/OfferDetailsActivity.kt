package com.sehakhanah.patientapp.modules.offers

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.View
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
import com.sehakhanah.patientapp.data.remote.objects.Date
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_doctor_profile.*
import kotlinx.android.synthetic.main.activity_offer_details.*
import kotlinx.android.synthetic.main.activity_offer_details.dates_rv
import kotlinx.android.synthetic.main.activity_offer_details.rates_rv
import kotlinx.android.synthetic.main.offers_fragment.*
import kotlinx.android.synthetic.main.offers_fragment.offers_pager_Slider
import kotlinx.android.synthetic.main.offers_fragment.progrss_lay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OfferDetailsActivity : BaseActivity() {
    private val imagesList: MutableList<String> = ArrayList()
    private var sliderAddapter: OffersPagerAdapter? = null
    private var offerDatesListAddapter: OfferDatesAdapter? = null
    private var offerDatesList: MutableList<Date> = ArrayList()
    private var offersList: MutableList<Offer> = ArrayList()
    private var offerRatesList: MutableList<Rates> = ArrayList()
    private var OfferRatesListAddapter: OfferRatingsAdapter? = null

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)
        initBottomNavigation()
        initSlider()
        initRVAdapter()
        offerObserver()
    }
    private fun setPageData(offer:Offer){
        var gendar=""
        if(offer.doctor.gender_id==1){
            gendar=getString(R.string.doctor)
        }else{
            gendar=getString(R.string.doctorah)
        }
        if (preferences!!.getString("language","")=="Arabic"){
            descound_txt!!.text=" خصم ${offer.discount.toString()}"+"%"
            offer_title_txt!!.text=offer.title_ar
            offer_subtitle_txt!!.text=offer.device_name_ar
            offer_ratingBar!!.rating=offer.rating.toFloat()
            initial_cost!!.paintFlags =initial_cost!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            initial_cost!!.text=offer.price.toString()+" "+getString(R.string.derham)
            final_cost!!.text=((offer.price*((100-offer.discount))/100)).toString()+getString(R.string.derham)
            offer_info_txt.text=offer.description_ar
            GlideObject.GlideProfilePic(this,offer.doctor.featured,offer_doc_img)
            offer_doc_name.text=gendar+" "+offer.doctor.firstName_ar+" "+offer.doctor.lastName_ar
            offer_doc_speciality.text=offer.doctor.profissionalTitle_ar
        }else{
            descound_txt!!.text=" ${getString(R.string.discount)} ${offer.discount.toString()}"+"%"
            offer_title_txt!!.text=offer.title_en
            offer_subtitle_txt!!.text=offer.device_name_en
            offer_ratingBar!!.rating=offer.rating.toFloat()
            initial_cost!!.paintFlags =initial_cost!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            initial_cost!!.text=offer.price.toString()+" "+getString(R.string.derham)
            final_cost!!.text=((offer.price*((100-offer.discount))/100)).toString()+getString(R.string.derham)
            offer_info_txt.text=offer.description_en
            GlideObject.GlideProfilePic(this,offer.doctor.featured,offer_doc_img)
            offer_doc_name.text=gendar+" "+offer.doctor.firstName_en+" "+offer.doctor.lastName_en
            offer_doc_speciality.text=offer.doctor.profissionalTitle_en
        }

        show_more_txt.setOnClickListener {
            if(show_more_txt.text==getString(R.string.more)){
                offer_info_txt.maxLines=20
                show_more_txt.text=getString(R.string.less)
            }else{
                offer_info_txt.maxLines=5
                show_more_txt.text=getString(R.string.more)

            }
        }
    }
    private fun initSlider(){
        var count=0
        sliderAddapter = OffersPagerAdapter(this,imagesList)
        offers_pager_Slider.adapter=sliderAddapter
        val handler = Handler()
        val update = Runnable {
            if(offers_pager_Slider!=null){


                if (offers_pager_Slider.getCurrentItem() == 0) {

                    offers_pager_Slider.currentItem = count+1
                    count += 1
                } else if (offers_pager_Slider.getCurrentItem() == imagesList.size-1) {
                    count=0
                    offers_pager_Slider.currentItem = count
                } else {

                    offers_pager_Slider.currentItem = count
                    count+=1
                }
            }


        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2500, 2500)

    }
    private fun offerObserver() {
        onObserveStart()
        val id =intent.getLongExtra("offer_id",-1)
        val url = Q.GET_OFFER_BY_ID_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchOfferById(url)
            .enqueue(object : Callback<BaseResponce<Offer>> {
                override fun onFailure(call: Call<BaseResponce<Offer>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Offer>>,
                    response: Response<BaseResponce<Offer>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                it.images.forEach {image: OfferImage ->
                                    imagesList.add(image.featured)
                                    sliderAddapter!!.notifyDataSetChanged()

                                }
                                it.dates.forEach {date:Date->
                                    if(date.status==1){
                                        offerDatesList.add(date)
                                    }
                                }
                                offersList.add(it)
                                offerRatesList.addAll(it.ratings)
                                setPageData(it)
                                onObserveSuccess()
                            }
                        } else {
                            Toast.makeText(this@OfferDetailsActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@OfferDetailsActivity, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    private fun onObserveStart(){
        progrss_lay.visibility= View.VISIBLE
        offer_details_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay.visibility= View.GONE
        offer_details_lay.visibility= View.VISIBLE
    }
    private fun initRVAdapter() {
        val layoutManager1 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        dates_rv.layoutManager = layoutManager1
        offerDatesListAddapter = OfferDatesAdapter(this, offerDatesList,offersList)
        dates_rv.adapter = offerDatesListAddapter

        rates_rv.layoutManager = layoutManager2
        OfferRatesListAddapter = OfferRatingsAdapter(this, offerRatesList,offersList)
        rates_rv.adapter = OfferRatesListAddapter


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
        bottomNavigationView15.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView15.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}