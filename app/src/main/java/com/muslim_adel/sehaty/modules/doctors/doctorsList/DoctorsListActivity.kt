package com.muslim_adel.sehaty.modules.doctors.doctorsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsAdapter
import com.muslim_adel.sehaty.modules.doctors.search.SpecialtiesAdapter
import kotlinx.android.synthetic.main.activity_doctors_list.*
import kotlinx.android.synthetic.main.activity_doctors_list.no_search_lay
import kotlinx.android.synthetic.main.activity_doctors_list.progrss_lay
import kotlinx.android.synthetic.main.activity_doctors_list.specialty_search_txt
import kotlinx.android.synthetic.main.activity_reagons.*
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorsListActivity : BaseActivity() {
    
    private var regionId:Int=0
    private var specialtyId:Int=0

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    
    private var doctorsList:MutableList<Doctor> = ArrayList()
    private var filtereddoctorsList:MutableList<Doctor> = ArrayList()

    private var doctorsListAddapter: DoctorsListAdapter?=null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_list)
        getIntents()
        initRVAdapter()
        doctorsObserver()
        search()
        
    }

    private fun search() {
        specialty_search_txt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtereddoctorsList.clear()
                if(specialty_search_txt.text.isBlank()||specialty_search_txt.text.isEmpty()){
                    filtereddoctorsList.addAll(doctorsList)
                    doctorsListAddapter!!.notifyDataSetChanged()
                }else{
                    doctorsList.forEach{
                        if(it.firstName_ar.contains(s.toString())){
                            filtereddoctorsList.add(it)
                        }
                    }
                    doctorsListAddapter!!.notifyDataSetChanged()

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
    private fun doctorsObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchDoctorsList(specialtyId,regionId)
            .enqueue(object : Callback<BaseResponce<Search>> {
                override fun onFailure(call: Call<BaseResponce<Search>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<Search>>, response: Response<BaseResponce<Search>>) {
                    if(response!!.isSuccessful){
                        response.body()!!.data!!.search.let {
                            if (it.isNotEmpty()){
                                doctorsList.addAll(it)
                                filtereddoctorsList.addAll(it)
                                doctorsListAddapter!!.notifyDataSetChanged()
                                onObserveSuccess()
                            }else{
                                onObservefaled()
                            }

                        }
                    }else{
                        onObservefaled()
                    }

                }


            })
    }
    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctors_rv.layoutManager = layoutManager
        doctorsListAddapter = DoctorsListAdapter(this, filtereddoctorsList)
        doctors_rv.adapter = doctorsListAddapter
    }
    private fun onObserveStart(){
        progrss_lay.visibility= View.VISIBLE
        doctors_rv.visibility= View.GONE
        no_search_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay.visibility= View.GONE
        doctors_rv.visibility= View.VISIBLE
        no_search_lay.visibility= View.GONE
    }
    private fun onObservefaled(){
        progrss_lay.visibility= View.GONE
        doctors_rv.visibility= View.GONE
        no_search_lay.visibility= View.VISIBLE
    }
    private fun getIntents(){
        specialtyId=intent.getIntExtra("specialty_id",0)
        regionId=intent.getIntExtra("region_id",0)
    }
}