package com.muslim_adel.sehaty.modules.favorits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.appointments.AppointmentesPagerAddapter
import com.muslim_adel.sehaty.modules.appointments.fragments.MyOffersFragments
import com.muslim_adel.sehaty.modules.appointments.fragments.bookingsFragments
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.base.CustomTabLayout
import com.muslim_adel.sehaty.modules.favorits.fragments.DoctorsFragment
import com.muslim_adel.sehaty.modules.favorits.fragments.OffersFragment
import kotlinx.android.synthetic.main.appointments_fragment.*

class FavoritsActivity : BaseActivity() {
    val listFragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorits)
        addFragment()
        setupViewPager()
    }
    fun setupViewPager() {
        val adapter = AppointmentesPagerAddapter(this, listFragments)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text =getString(R.string.fav_doc)
                }
                1 -> {
                    tab.text = getString(R.string.fav_offers)

                }
            }
            viewPager.setCurrentItem(tab.position, true)
            val customTabLayout = CustomTabLayout(this)
            customTabLayout.setTitle(tab.text.toString())
            tab.customView = customTabLayout

        }.attach()

    }

    fun addFragment() {
        listFragments.add(DoctorsFragment())
        listFragments.add(OffersFragment())

    }
}