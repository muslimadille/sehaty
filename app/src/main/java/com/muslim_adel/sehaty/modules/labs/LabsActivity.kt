package com.sehakhanah.patientapp.modules.labs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.doctors.reagons.ReagonsActivity
import com.sehakhanah.patientapp.modules.doctors.search.SearchByNameActivity
import com.sehakhanah.patientapp.modules.doctors.search.SearchBySpecialityActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.pharmacy.PharmacyOffersActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_labs.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.search_name_btn
import kotlinx.android.synthetic.main.home_fragment.search_spicialist_btn

class LabsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labs)
        initBottomNavigation()
        onNameSearchClicked()
        onRegionSearchClicked()
        onAllSearchClicked()
    }
    fun onNameSearchClicked(){
        search_name_btn.setOnClickListener {
            val intent = Intent(this, SearchByNameActivity::class.java)
            intent.putExtra("key",1)
            startActivity(intent)
        }
    }
    fun onRegionSearchClicked(){
        search_region_btn.setOnClickListener {
            val intent = Intent(this, ReagonsActivity::class.java)
            intent.putExtra("key",1)
            startActivity(intent)
        }
    }
    fun onAllSearchClicked(){
        show_all_btn.setOnClickListener {
            val intent = Intent(this, LabsListActivity::class.java)
            startActivity(intent)
        }
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
        bottomNavigationView10.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView10.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}