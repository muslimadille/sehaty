package com.muslim_adel.sehaty.modules.labs

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.data.remote.objects.Date
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.offers.OfferDatesAdapter
import com.muslim_adel.sehaty.modules.offers.OfferRatingsAdapter
import com.muslim_adel.sehaty.modules.offers.OffersPagerAdapter
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.activity_lab_details.*
import kotlinx.android.synthetic.main.activity_offer_details.*
import kotlinx.android.synthetic.main.activity_offer_details.dates_rv
import kotlinx.android.synthetic.main.activity_offer_details.offer_details_lay
import kotlinx.android.synthetic.main.activity_offer_details.offer_doc_img
import kotlinx.android.synthetic.main.activity_offer_details.offer_doc_name
import kotlinx.android.synthetic.main.activity_offer_details.offer_doc_speciality
import kotlinx.android.synthetic.main.activity_offer_details.offer_info_txt
import kotlinx.android.synthetic.main.activity_offer_details.offer_title_txt
import kotlinx.android.synthetic.main.activity_offer_details.rates_rv
import kotlinx.android.synthetic.main.activity_offer_details.show_more_txt
import kotlinx.android.synthetic.main.offers_fragment.*
import kotlinx.android.synthetic.main.offers_fragment.offers_pager_Slider
import kotlinx.android.synthetic.main.offers_fragment.progrss_lay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LabDetailsActivity : BaseActivity() {
    private val imagesList: MutableList<String> = ArrayList()
    private var sliderAddapter: OffersPagerAdapter? = null
    private var labDatesListAddapter: LabDatesAdapter? = null
    private var labDatesList: MutableList<Date> = ArrayList()
    private var offersList: MutableList<Laboratory> = ArrayList()
    private var labRatesList: MutableList<Rates> = ArrayList()
    private var labRatesListAddapter: LabRatesAdapter? = null

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_details)
        initSlider()
        initRVAdapter()
        labObserver()
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

    private fun setPageData(lab:Laboratory){
        if (preferences!!.getString("language","")=="Arabic"){
            lab_name_txt!!.text=lab.laboratory_name_ar
            lab_address_txt!!.text=lab.address_ar
            lab_ratingBar!!.rating=lab.rating.toFloat()
        }else{
            lab_name_txt!!.text=lab.laboratory_name_en
            lab_address_txt!!.text=lab.address_en
            lab_ratingBar!!.rating=lab.rating.toFloat()
        }


        lab_show_more_txt.setOnClickListener {
            if(lab_show_more_txt.text==getString(R.string.more)){
                lab_info_txt.maxLines=20
                lab_show_more_txt.text=getString(R.string.less)
            }else{
                lab_info_txt.maxLines=5
                lab_show_more_txt.text=getString(R.string.more)

            }
        }
    }
    private fun labObserver() {
        onObserveStart()
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
                            response.body()!!.data!!.let {
                                it.laboratory_photos.forEach {image: LaboratoryPhotos ->
                                    imagesList.add(image.featured)
                                    sliderAddapter!!.notifyDataSetChanged()

                                }
                                it.dates.forEach {date:Date->
                                    if(date.status==1){
                                        labDatesList.add(date)
                                    }
                                }
                                offersList.add(it)
                                labRatesList.addAll(it.ratings)
                                setPageData(it)
                                onObserveSuccess()
                            }
                        } else {
                            Toast.makeText(this@LabDetailsActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@LabDetailsActivity, "connect faid", Toast.LENGTH_SHORT).show()

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
        labDatesListAddapter = LabDatesAdapter(this, labDatesList,offersList)
        dates_rv.adapter = labDatesListAddapter

        rates_rv.layoutManager = layoutManager2
        labRatesListAddapter = LabRatesAdapter(this, labRatesList)
        rates_rv.adapter = labRatesListAddapter


    }
}