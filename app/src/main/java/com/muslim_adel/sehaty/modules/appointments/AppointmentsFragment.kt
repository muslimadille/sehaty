package com.sehakhanah.patientapp.modules.appointments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muslim_adel.sehaty.modules.appointments.fragments.LabBookingsFragment
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.modules.base.CustomTabLayout
import com.sehakhanah.patientapp.modules.appointments.fragments.MyOffersFragments
import com.sehakhanah.patientapp.modules.appointments.fragments.bookingsFragments
import com.sehakhanah.patientapp.modules.register.LoginActivity
import com.sehakhanah.patientapp.utiles.ComplexPreferences
import kotlinx.android.synthetic.main.appointments_fragment.*

class AppointmentsFragment : Fragment() {
    var preferences: ComplexPreferences? = null
    val listFragments = ArrayList<Fragment>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.appointments_fragment, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = ComplexPreferences.getComplexPreferences(context as FragmentActivity, Q.PREF_FILE, Q.MODE_PRIVATE)
        viewSelector()

    }

    fun setupViewPager() {
        val adapter = AppointmentesPagerAddapter(context as FragmentActivity, listFragments)
        viewPager.adapter = adapter

        val tabLayout = requireView().findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text =getString(R.string.DOCTORS)

                }
                1 -> {
                    tab.text = getString(R.string.LABS)

                }
                2 ->{
                    tab.text = getString(R.string.offers_item)
                }

            }
            viewPager.setCurrentItem(tab.position, true)
            val customTabLayout = CustomTabLayout(context as FragmentActivity)
            customTabLayout.setTitle(tab.text.toString())
            tab.customView = customTabLayout

        }.attach()



    }

    fun addFragment() {
        listFragments.add(bookingsFragments())
        listFragments.add(LabBookingsFragment())
        listFragments.add(MyOffersFragments())

    }
    private fun viewSelector(){
        if(preferences!!.getBoolean(Q.IS_LOGIN, false)){
            bookings_lay.visibility=View.VISIBLE
            booking_alet_lay.visibility=View.GONE
            addFragment()
            setupViewPager()
        }else{
            bookings_lay.visibility=View.GONE
            booking_alet_lay.visibility=View.VISIBLE
            login_nave.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}