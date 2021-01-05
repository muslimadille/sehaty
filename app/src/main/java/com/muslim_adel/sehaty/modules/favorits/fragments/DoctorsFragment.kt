package com.sehakhanah.patientapp.modules.favorits.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.*
import com.sehakhanah.patientapp.modules.favorits.FavDoctorAdapter
import com.sehakhanah.patientapp.modules.favorits.FavoritsActivity
import kotlinx.android.synthetic.main.fragment_bookings_fragments.no_search_lay
import kotlinx.android.synthetic.main.fragment_bookings_fragments.progrss_lay
import kotlinx.android.synthetic.main.fragment_doctors.*
import kotlinx.android.synthetic.main.no_search_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorsFragment : Fragment() {
    private var doctorsList: MutableList<Doctor> = ArrayList()

    private var doctorsAdapter: FavDoctorAdapter? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRVAdapter()
        favDoctorsObserver()
    }

    private fun favDoctorsObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        onObserveStart()
        apiClient.getApiService(mContext!!).fitchAllDoctorsList()
            .enqueue(object : Callback<BaseResponce<Search>> {
                override fun onFailure(call: Call<BaseResponce<Search>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Search>>,
                    response: Response<BaseResponce<Search>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.search.isNotEmpty()) {
                                    it.search.forEach { doctor: Doctor ->
                                        if (mContext!!.preferences!!.getLong("${doctor.id}",-1)==doctor.id) {
                                            doctorsList.add(doctor)
                                        }
                                    }
                                    doctorsAdapter!!.notifyDataSetChanged()
                                    if(doctorsList.isNotEmpty()){
                                        onObserveSuccess()

                                    }else{
                                        onObservefaled()
                                    }
                                } else {
                                    onObservefaled()
                                }

                            }
                        } else {
                            onObservefaled()
                        }

                    } else {
                        onObservefaled()
                    }

                }


            })
    }

    private fun initRVAdapter() {
        val layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        favorite_doctors_rv.layoutManager = layoutManager
        doctorsAdapter = FavDoctorAdapter(mContext!!,doctorsList)
        favorite_doctors_rv.adapter = doctorsAdapter
    }

    private fun onObserveStart() {
        progrss_lay.visibility = View.VISIBLE
        favorite_doctors_rv.visibility = View.GONE
        no_search_lay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        progrss_lay.visibility = View.GONE
        favorite_doctors_rv.visibility = View.VISIBLE
        no_search_lay.visibility = View.GONE
    }

    private fun onObservefaled() {
        progrss_lay?.let {
            it.visibility = View.GONE
            favorite_doctors_rv.visibility = View.GONE
            no_search_lay.visibility = View.VISIBLE
            no_data_txt.text="لايوجد أطباء مفضلة لديك "
            no_data_img.setImageResource(R.drawable.person_ic)
        }

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
    var mContext: FavoritsActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FavoritsActivity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as FavoritsActivity
    }

}