package com.muslim_adel.sehaty.modules.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsActivity
import com.muslim_adel.sehaty.modules.doctors.search.SearchByNameActivity
import com.muslim_adel.sehaty.modules.doctors.search.SearchBySpecialityActivity
import com.muslim_adel.sehaty.modules.labs.LabsActivity
import com.muslim_adel.sehaty.modules.labs.LabsListActivity
import com.muslim_adel.sehaty.modules.pharmacy.PharmacyOffersActivity
import com.muslim_adel.sehaty.modules.pharmacy.PharmaySearchActivity
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_fragment, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onNameSearchClicked()
        onSpicialistSearchClicked()
        onfarmacySearchClicked()
        onLabsSearchClicked()
    }
    fun onNameSearchClicked(){
        search_name_btn.setOnClickListener {
            val intent = Intent(context, SearchByNameActivity::class.java)
            startActivity(intent)
        }
    }
    fun onSpicialistSearchClicked(){
        search_spicialist_btn.setOnClickListener {
            val intent = Intent(context, SearchBySpecialityActivity::class.java)
            startActivity(intent)
        }
    }
    fun onfarmacySearchClicked(){
        pharmacy_search_btn.setOnClickListener {
            val intent = Intent(context, PharmaySearchActivity::class.java)
            startActivity(intent)
        }
    }
    fun onLabsSearchClicked(){
        labs_search_btn.setOnClickListener {
            val intent = Intent(context, LabsActivity::class.java)
            startActivity(intent)

        }
    }
}