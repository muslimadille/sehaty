package com.muslim_adel.sehaty.modules.doctors.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.data.remote.objects.Search
import com.muslim_adel.sehaty.modules.base.BaseActivity
import kotlinx.android.synthetic.main.activity_doctors_list.doctors_rv
import kotlinx.android.synthetic.main.activity_doctors_list.no_search_lay
import kotlinx.android.synthetic.main.activity_doctors_list.progrss_lay
import kotlinx.android.synthetic.main.activity_doctors_list.specialty_search_txt
import kotlinx.android.synthetic.main.activity_search_by_name.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchByNameActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var doctorsList:MutableList<Doctor> = ArrayList()
    private var filtereddoctorsList:MutableList<Doctor> = ArrayList()
    private var doctorsListAddapter: SearchByNameAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_name)

        initRVAdapter()
        search()
    }

    private fun search() {
        search_btn.setOnClickListener {
            doctorsObserver(specialty_search_txt.text.toString())
        }

        specialty_search_txt.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            doctorsObserver(specialty_search_txt.text.toString())
                            return true
                        }
                        else -> {
                        }
                    }
                }
                return false
            }
        })

    }
    private fun doctorsObserver(name: String){
        filtereddoctorsList.clear()
        doctorsListAddapter!!.notifyDataSetChanged()
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchDoctorsList(name)
            .enqueue(object : Callback<BaseResponce<Search>> {
                override fun onFailure(call: Call<BaseResponce<Search>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<Search>>,
                    response: Response<BaseResponce<Search>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response!!.body()!!.success) {
                            response.body()!!.data!!.search.let {
                                if (it.isNotEmpty()) {
                                    doctorsList.addAll(it)
                                    filtereddoctorsList.addAll(it)
                                    doctorsListAddapter!!.notifyDataSetChanged()
                                    onObserveSuccess()
                                } else {
                                    onObservefaled()
                                }

                            }
                        } else {
                            onObservefaled()
                        }

                    } else {
                        onObservefaled()
                    }

                }


            })
    }
    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctors_rv.layoutManager = layoutManager
        doctorsListAddapter = SearchByNameAdapter(this, filtereddoctorsList)
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

}