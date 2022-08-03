package com.muslim_adel.enaya.modules.doctors.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextSwitcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.Specialties
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBySpecialityActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var specialtyList:MutableList<Specialties> = ArrayList()
    private var filteredSpecialtyList:MutableList<Specialties> = ArrayList()

    private var specialtyListAddapter: SpecialtiesAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_doctor_name)
        initRVAdapter()
        initBottomNavigation()
        search()
        specialtyObserver()

    }
    private fun search() {
        specialty_search_txt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filteredSpecialtyList.clear()
                if(specialty_search_txt.text.isBlank()||specialty_search_txt.text.isEmpty()){
                    filteredSpecialtyList.addAll(specialtyList)
                    specialtyListAddapter!!.notifyDataSetChanged()
                }else{
                    specialtyList.forEach{
                        if(it.name_ar.contains(s.toString())){
                            filteredSpecialtyList.add(it)
                        }
                    }
                    specialtyListAddapter!!.notifyDataSetChanged()

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
    private fun specialtyObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchSpecialitiesList()
            .enqueue(object : Callback<BaseResponce<List<Specialties>>> {
                override fun onFailure(call: Call<BaseResponce<List<Specialties>>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<List<Specialties>>>, response: Response<BaseResponce<List<Specialties>>>) {
                    if(response.isSuccessful){
                        response.body()!!.data.let {
                            specialtyList.addAll(it!!)
                            filteredSpecialtyList.addAll(it!!)
                            specialtyListAddapter!!.notifyDataSetChanged()
                            onObserveSuccess()
                        }
                    }else{
                       onObservefaled()
                    }

                }


            })
    }
    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctor_search_rv.layoutManager = layoutManager
        specialtyListAddapter =
            SpecialtiesAdapter(
                this,
                filteredSpecialtyList
            )
        doctor_search_rv.adapter = specialtyListAddapter
    }
    private fun onObserveStart(){
        progrss_lay.visibility= View.VISIBLE
        doctor_search_rv.visibility= View.GONE
        no_search_lay.visibility=View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay.visibility= View.GONE
        doctor_search_rv.visibility= View.VISIBLE
        no_search_lay.visibility=View.GONE
    }
    private fun onObservefaled(){
        progrss_lay.visibility= View.GONE
        doctor_search_rv.visibility= View.GONE
        no_search_lay.visibility=View.VISIBLE
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
        bottomNavigationView6.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView6.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}