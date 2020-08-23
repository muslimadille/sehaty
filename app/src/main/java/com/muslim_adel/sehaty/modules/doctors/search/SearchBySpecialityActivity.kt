package com.muslim_adel.sehaty.modules.doctors.search

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.specialties.SpecialtiesAdapter
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBySpecialityActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var specialtyList:MutableList<Specialties> = ArrayList()
    private var specialtyListAddapter:SpecialtiesAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_doctor_name)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctor_search_rv.layoutManager = layoutManager
        specialtyListAddapter = SpecialtiesAdapter(this, specialtyList)
        doctor_search_rv.adapter = specialtyListAddapter

        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchSpecialitiesList()
            .enqueue(object : Callback<BaseResponce<List<Specialties>>> {
                override fun onFailure(call: Call<BaseResponce<List<Specialties>>>, t: Throwable) {
                    Toast.makeText(this@SearchBySpecialityActivity, "فشل", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<BaseResponce<List<Specialties>>>, response: Response<BaseResponce<List<Specialties>>>) {
                    if(response.isSuccessful){
                        response.body()!!.data.let {
                            specialtyList.addAll(it!!)
                            specialtyListAddapter!!.notifyDataSetChanged()
                        }
                    }else{
                       Toast.makeText(this@SearchBySpecialityActivity, "نجح بس ماجبش حاجة ", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
}