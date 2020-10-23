package com.muslim_adel.sehaty.modules.favorits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
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
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_about_us.bottomNavigationView
import kotlinx.android.synthetic.main.activity_favorits.*
import kotlinx.android.synthetic.main.appointments_fragment.*
import kotlinx.android.synthetic.main.appointments_fragment.viewPager

class FavoritsActivity : BaseActivity() {
    val listFragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorits)
        initBottomNavigation()
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
        bottomNavigationView7.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView7.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}