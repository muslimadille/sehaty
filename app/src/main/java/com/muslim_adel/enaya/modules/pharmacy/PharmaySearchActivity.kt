package com.muslim_adel.enaya.modules.pharmacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.doctors.reagons.ReagonsActivity
import com.muslim_adel.enaya.modules.doctors.search.SearchByNameActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import com.muslim_adel.enaya.modules.labs.LabsListActivity
import kotlinx.android.synthetic.main.activity_labs.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.search_name_btn

class PharmaySearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmay_search)
        initBottomNavigation()
        onNameSearchClicked()
        onRegionSearchClicked()
        onAllSearchClicked()
    }
    fun onNameSearchClicked(){
        search_name_btn.setOnClickListener {
            val intent = Intent(this, SearchByNameActivity::class.java)
            intent.putExtra("key",2)
            startActivity(intent)
        }
    }
    fun onRegionSearchClicked(){
        search_region_btn.setOnClickListener {
            val intent = Intent(this, ReagonsActivity::class.java)
            intent.putExtra("key",2)
            startActivity(intent)
        }
    }
    fun onAllSearchClicked(){
        show_all_btn.setOnClickListener {
            val intent = Intent(this, PharmacyOffersActivity::class.java)
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