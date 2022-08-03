package com.muslim_adel.enaya.modules.labs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.Laboratory
import com.muslim_adel.enaya.data.remote.objects.LabsSearch
import com.muslim_adel.enaya.modules.base.BaseActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_labs_list.*
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.*
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.no_search_lay
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.progrss_lay
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.specialty_search_txt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabsListActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    var key=0
    var region_id=0

    private var specialtyList:MutableList<Laboratory> = ArrayList()
    private var filteredSpecialtyList:MutableList<Laboratory> = ArrayList()

    private var specialtyListAddapter: LabsListAdaptor?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labs_list)
        initBottomNavigation()
        region_id=intent.getIntExtra("region_id",0)
        key=intent.getIntExtra("key",0)
        specialty_search_txt.hint=getString(R.string.lab_search_hint)
        if(key==1){
            initRVAdapter()
            search()
            regionsLabsObserver()
        }else{
            initRVAdapter()
            search()
            allLabsObserver()
        }

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
                        if(it.laboratory_name_ar.contains(s.toString())||it.laboratory_name_en.contains(s.toString())){
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
    private fun allLabsObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchAllLabsList()
            .enqueue(object : Callback<BaseResponce<LabsSearch>> {
                override fun onFailure(call: Call<BaseResponce<LabsSearch>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<LabsSearch>>, response: Response<BaseResponce<LabsSearch>>) {
                    if(response.isSuccessful){
                        response.body()!!.data!!.search.let {
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
    private fun regionsLabsObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchLabsByRegionList(region_id)
            .enqueue(object : Callback<BaseResponce<LabsSearch>> {
                override fun onFailure(call: Call<BaseResponce<LabsSearch>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<LabsSearch>>, response: Response<BaseResponce<LabsSearch>>) {
                    if(response.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data?.let {lab:LabsSearch->
                                lab.search.let {
                                    specialtyList.addAll(it!!)
                                    filteredSpecialtyList.addAll(it!!)
                                    specialtyListAddapter!!.notifyDataSetChanged()
                                    onObserveSuccess()
                                }
                            }
                        }else{
                            onObservefaled()
                        }

                    }else{
                        onObservefaled()
                    }

                }


            })
    }

    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        labs_rv.layoutManager = layoutManager
        specialtyListAddapter =
            LabsListAdaptor(this, filteredSpecialtyList)
        labs_rv.adapter = specialtyListAddapter
    }
    private fun onObserveStart(){
        progrss_lay.visibility= View.VISIBLE
        labs_rv.visibility= View.GONE
        no_search_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay.visibility= View.GONE
        labs_rv.visibility= View.VISIBLE
        no_search_lay.visibility= View.GONE
    }
    private fun onObservefaled(){
        progrss_lay.visibility= View.GONE
        labs_rv.visibility= View.GONE
        no_search_lay.visibility= View.VISIBLE
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
        bottomNavigationView12.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView12.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}