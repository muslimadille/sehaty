package com.muslim_adel.sehaty.modules.doctors.reagons

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.doctors.doctorsList.DoctorsListAdapter
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_doctors_list.*
import kotlinx.android.synthetic.main.activity_reagons.*
import kotlinx.android.synthetic.main.activity_search_by_doctor_name.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ReagonsActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var reagonsList:MutableList<Reagons> = ArrayList()
    private var reagonsListAddapter: ReagonsAdapter?=null
    var specialtyId:Int=0
    var key=0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reagons)
        key=intent.getIntExtra("key",0)

        getIntents()
        initBottomNavigation()
        initRVAdapter()
        regonObserver()

    }
    private fun onObserveStart(){
        regon_progrss_lay.visibility= View.VISIBLE
        reagons_rv.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        regon_progrss_lay.visibility= View.GONE
        reagons_rv.visibility= View.VISIBLE
    }
    private fun onObservefaled(){
        regon_progrss_lay.visibility= View.GONE
        reagons_rv.visibility= View.GONE
    }
    private fun getIntents(){
        specialtyId=intent.getIntExtra("specialty_id",0)
    }
    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        reagons_rv.layoutManager = layoutManager
        reagonsListAddapter = ReagonsAdapter(this, reagonsList,specialtyId)
        reagons_rv.adapter = reagonsListAddapter
    }
    private fun regonObserver(){
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchReagonsList()
            .enqueue(object : Callback<BaseResponce<List<Reagons>>> {
                override fun onFailure(call: Call<BaseResponce<List<Reagons>>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<List<Reagons>>>, response: Response<BaseResponce<List<Reagons>>>) {
                    if(response!!.isSuccessful){
                        response.body()!!.data.let {
                            reagonsList.addAll(it!!)
                            reagonsListAddapter!!.notifyDataSetChanged()
                            onObserveSuccess()
                        }
                    }else{
                        onObservefaled()
                    }

                }

            })
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
        bottomNavigationView4.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView4.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}