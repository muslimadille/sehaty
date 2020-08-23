package com.muslim_adel.sehaty.modules.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.doctors.SearchByDoctorNameActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_fragment, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onNameSearchClicked()
    }
    fun onNameSearchClicked(){
        search_name_btn.setOnClickListener {
            val intent = Intent(context, SearchByDoctorNameActivity::class.java)
            startActivity(intent)
        }
    }
    fun onSpicialistSearchClicked(){
        search_name_btn.setOnClickListener {  }
    }
    fun onfarmacySearchClicked(){
        search_name_btn.setOnClickListener {  }
    }
    fun onLabsSearchClicked(){
        search_name_btn.setOnClickListener {  }
    }
}