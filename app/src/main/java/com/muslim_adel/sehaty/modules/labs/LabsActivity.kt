package com.muslim_adel.sehaty.modules.labs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsActivity
import com.muslim_adel.sehaty.modules.doctors.search.SearchByNameActivity
import com.muslim_adel.sehaty.modules.doctors.search.SearchBySpecialityActivity
import com.muslim_adel.sehaty.modules.pharmacy.PharmacyOffersActivity
import kotlinx.android.synthetic.main.activity_labs.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.search_name_btn
import kotlinx.android.synthetic.main.home_fragment.search_spicialist_btn

class LabsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labs)
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
}