package com.muslim_adel.sehaty.modules.doctors.doctorsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.data.remote.objects.Search
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsAdapter
import kotlinx.android.synthetic.main.activity_doctors_list.*
import kotlinx.android.synthetic.main.activity_reagons.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorsListActivity : BaseActivity() {
    private var regionId:Int=0
    private var specialtyId:Int=0

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var doctorsList:MutableList<Doctor> = ArrayList()
    private var doctorsListAddapter: DoctorsListAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_list)
        specialtyId=intent.getIntExtra("specialty_id",0)
        regionId=intent.getIntExtra("region_id",0)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctors_rv.layoutManager = layoutManager
        doctorsListAddapter = DoctorsListAdapter(this, doctorsList)
        doctors_rv.adapter = doctorsListAddapter


        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchDoctorsList(specialtyId,regionId)
            .enqueue(object : Callback<BaseResponce<Search>> {
                override fun onFailure(call: Call<BaseResponce<Search>>, t: Throwable) {
                    Toast.makeText(this@DoctorsListActivity, "فشل", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<BaseResponce<Search>>, response: Response<BaseResponce<Search>>) {
                    if(response!!.isSuccessful){
                        response.body()!!.data!!.search.let {
                            doctorsList.addAll(it!!)
                            doctorsListAddapter!!.notifyDataSetChanged()
                        }
                    }else{
                        Toast.makeText(this@DoctorsListActivity, "نجح بس ماجبش حاجة ", Toast.LENGTH_SHORT).show()
                    }

                }


            })
    }
}