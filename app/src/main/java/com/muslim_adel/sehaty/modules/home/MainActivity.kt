package com.sehakhanah.patientapp.modules.home

import android.os.Bundle
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.Appointment
import com.sehakhanah.patientapp.data.remote.objects.AppointmentData
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Doctor
import com.sehakhanah.patientapp.modules.appointments.AppointmentsAdapter
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.appointments.AppointmentsFragment
import com.sehakhanah.patientapp.modules.home.fragments.ExstarsFragment
import com.sehakhanah.patientapp.modules.home.fragments.HomeFragment
import com.sehakhanah.patientapp.modules.home.fragments.OffersFragment
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    var key=false
    var navK=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        key=intent.getBooleanExtra("key",false)
        navK=intent.getIntExtra("navK",0)
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
        if(key){
            bottomNavigationView.selectedItemId = R.id.navigation_appointment
        }
        when (navK) {
            0 -> {
                bottomNavigationView.selectedItemId = R.id.navigation_home
            }
            1 -> {
                bottomNavigationView.selectedItemId = R.id.navigation_offers
            }
            2 -> {
                bottomNavigationView.selectedItemId = R.id.navigation_appointment
            }
            3 -> {
                bottomNavigationView.selectedItemId = R.id.navigation_extras
            }
        }
    }



}






