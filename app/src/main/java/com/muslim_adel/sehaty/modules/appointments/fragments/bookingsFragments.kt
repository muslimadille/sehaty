package com.muslim_adel.sehaty.modules.appointments.fragments

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
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.modules.appointments.AppointmentsAdapter
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_doctors_list.no_search_lay
import kotlinx.android.synthetic.main.activity_doctors_list.progrss_lay
import kotlinx.android.synthetic.main.fragment_bookings_fragments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class bookingsFragments : Fragment() {
    private var doctorsList:MutableList<Doctor> = ArrayList()
    //private var appointmentsList:MutableList<AppointmentData> = ArrayList()

    private var appointmentsAddapter: AppointmentsAdapter?=null
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRVAdapter()
        mContext!!.appointmentsList.forEach {
            doctorsObserver(it.doctor_id.toInt())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for context fragment
        return inflater.inflate(R.layout.fragment_bookings_fragments, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //appointmentsObserver()
    }
    private fun doctorsObserver(id:Int){
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        onObserveStart()
        val url = Q.DOCTOR_BY_ID_API + "/${id}"
        apiClient.getApiService(mContext!!).fitchDoctorById(url)
            .enqueue(object : Callback<BaseResponce<Doctor>> {
                override fun onFailure(call: Call<BaseResponce<Doctor>>, t: Throwable) {
                    alertNetwork(false
                    )
                }
                override fun onResponse(call: Call<BaseResponce<Doctor>>, response: Response<BaseResponce<Doctor>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.let {
                                    doctorsList.add(it)
                            }
                            if(doctorsList.size==mContext!!.appointmentsList.size){
                                appointmentsAddapter!!.notifyDataSetChanged()
                                 onObserveSuccess()
                            }
                        }else{
                            onObservefaled()
                        }

                    }else{
                        onObservefaled()
                    }

                }


            })
    }

    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        appointment_rv.layoutManager = layoutManager
        appointmentsAddapter = AppointmentsAdapter(mContext!!, mContext!!.appointmentsList,doctorsList)
        appointment_rv.adapter = appointmentsAddapter
    }
    private fun onObserveStart(){
        progrss_lay.visibility= View.VISIBLE
        appointment_rv.visibility= View.GONE
        no_search_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay.visibility= View.GONE
        appointment_rv.visibility= View.VISIBLE
        no_search_lay.visibility= View.GONE
    }
    private fun onObservefaled(){
        progrss_lay.visibility= View.GONE
        appointment_rv.visibility= View.GONE
        no_search_lay.visibility= View.VISIBLE
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
    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as MainActivity
    }



}