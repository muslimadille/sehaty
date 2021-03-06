package com.muslim_adel.sehaty.modules.appointments.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.modules.appointments.LabsAppointmentsAdapter
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.Appointment
import com.sehakhanah.patientapp.data.remote.objects.AppointmentData
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.fragment_bookings_fragments.*
import kotlinx.android.synthetic.main.no_search_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LabBookingsFragment : Fragment() {
    private var appointmentsList: MutableList<AppointmentData> = ArrayList()

    private var appointmentsAddapter: LabsAppointmentsAdapter? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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
        initRVAdapter()
        appointmentsObserver()
    }

    private fun appointmentsObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        onObserveStart()
        apiClient.getApiService(mContext!!).fitchLabBookingList()
            .enqueue(object : Callback<BaseResponce<Appointment>> {
                override fun onFailure(call: Call<BaseResponce<Appointment>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Appointment>>,
                    response: Response<BaseResponce<Appointment>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.booking.let {
                                if (it!=null&&it.isNotEmpty()) {

                                    it.forEach { appointment: AppointmentData ->
                                        if (appointment.status_id != 2) {
                                            appointmentsList.add(appointment)
                                        }
                                    }
                                    appointmentsAddapter!!.notifyDataSetChanged()
                                    if(appointmentsList.isNotEmpty()){
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
        appointment_rv.layoutManager = layoutManager
        appointmentsAddapter = LabsAppointmentsAdapter(mContext!!,appointmentsList)
        appointment_rv.adapter = appointmentsAddapter
    }

    private fun onObserveStart() {
        progrss_lay.visibility = View.VISIBLE
        appointment_rv.visibility = View.GONE
        no_search_lay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        progrss_lay.visibility = View.GONE
        appointment_rv.visibility = View.VISIBLE
        no_search_lay.visibility = View.GONE
    }

    private fun onObservefaled() {
        progrss_lay.let {
            it.visibility = View.GONE
            appointment_rv.visibility = View.GONE
            no_search_lay.visibility = View.VISIBLE
            no_data_txt.text=getString(R.string.no_appointments)
            no_data_img.setImageResource(R.drawable.calendar_ic)
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
        if (!mContext!!.isFinishing){
            alertBuilder.show()
        }
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