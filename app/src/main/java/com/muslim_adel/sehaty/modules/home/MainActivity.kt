package com.muslim_adel.sehaty.modules.home

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.Appointment
import com.muslim_adel.sehaty.data.remote.objects.AppointmentData
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.modules.appointments.AppointmentsAdapter
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.appointments.AppointmentsFragment
import com.muslim_adel.sehaty.modules.home.fragments.ExstarsFragment
import com.muslim_adel.sehaty.modules.home.fragments.HomeFragment
import com.muslim_adel.sehaty.modules.home.fragments.OffersFragment
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    var appointmentsList:MutableList<AppointmentData> = ArrayList()

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appointmentsObserver()
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
        initBottomNavigation()


    }
    private fun initBottomNavigation(){

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {

                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_offers -> {
                    val fragment = OffersFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_appointment -> {
                    val fragment =
                        AppointmentsFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_extras->{
                    val fragment = ExstarsFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottomNavigationView.labelVisibilityMode=LABEL_VISIBILITY_LABELED
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private fun appointmentsObserver(){
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        //onObserveStart()
        apiClient.getApiService(this).fitchBookingList()
            .enqueue(object : Callback<BaseResponce<Appointment>> {
                override fun onFailure(call: Call<BaseResponce<Appointment>>, t: Throwable) {
                    //alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<Appointment>>, response: Response<BaseResponce<Appointment>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.booking.let {
                                if (it.isNotEmpty()){

                                    it.forEach { appointment: AppointmentData ->
                                        appointmentsList.add(appointment)

                                    }

                                   // onObserveSuccess()
                                }else{
                                  //  onObservefaled()
                                }

                            }
                        }else{
                            //onObservefaled()
                        }

                    }else{
                       // onObservefaled()
                    }

                }


            })
    }


}






