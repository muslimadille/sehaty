package com.muslim_adel.sehaty.modules.doctors.search

import android.os.Bundle
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.BaseActivity

class SearchByNameActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var specialtyList:MutableList<Specialties> = ArrayList()
    private var specialtyListAddapter: SpecialtiesAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_name)
    }
}