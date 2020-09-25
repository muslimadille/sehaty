package com.muslim_adel.sehaty.modules.home.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.Appointment
import com.muslim_adel.sehaty.data.remote.objects.AppointmentData
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.OfferSlider
import com.muslim_adel.sehaty.modules.appointments.AppointmentsAdapter
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.introSlider.adapters.IntroPagerAdapter
import com.muslim_adel.sehaty.modules.offers.OffersPagerAdapter
import kotlinx.android.synthetic.main.activity_intro_wizerd.*
import kotlinx.android.synthetic.main.offers_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class OffersFragment : Fragment() {
    private val imagesList: MutableList<String> = ArrayList()

    private var sliderAddapter: OffersPagerAdapter? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.offers_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sliderImagesObserver()
        initSlider()
        super.onViewCreated(view, savedInstanceState)


    }
    fun alertNetwork(isExit: Boolean = true) {
        val alertBuilder = AlertDialog.Builder(mContext!!)
        //alertBuilder.setTitle(R.string.error)
        alertBuilder.setMessage(R.string.no_internet)
        if (isExit) {
            // alertBuilder.setPositiveButton(R.string.exit) { dialog: DialogInterface, _: Int -> context!!.finish() }
        } else {
            alertBuilder.setPositiveButton(R.string.dismiss) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        }
        alertBuilder.show()
    }

    private fun sliderImagesObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).fitchOffersSLiderImages()
            .enqueue(object : Callback<BaseResponce<List<OfferSlider>>> {
                override fun onFailure(call: Call<BaseResponce<List<OfferSlider>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<OfferSlider>>>,
                    response: Response<BaseResponce<List<OfferSlider>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    it.forEach { offer: OfferSlider ->
                                        imagesList.add(offer.featured_ar)
                                        sliderAddapter!!.notifyDataSetChanged()
                                    }
                                } else {
                                    Toast.makeText(mContext, "empty", Toast.LENGTH_SHORT).show()
                                }

                            }
                        } else {
                            Toast.makeText(mContext, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(mContext, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as MainActivity
    }

    private fun initSlider(){
        var count=0
        sliderAddapter = OffersPagerAdapter(mContext!!,imagesList)
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
}