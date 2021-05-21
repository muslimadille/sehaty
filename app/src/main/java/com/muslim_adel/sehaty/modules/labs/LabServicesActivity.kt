package com.sehakhanah.patientapp.modules.labs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Laboratory
import com.sehakhanah.patientapp.data.remote.objects.LaboratoryServices
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import kotlinx.android.synthetic.main.activity_lab_services.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabServicesActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var labServicesList:MutableList<LaboratoryServices> = ArrayList()
    private var LabServicesListAddapter: LabServicesAdapter?=null
    var dateId=0
    var labId=0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_services)
        dateId=intent.getIntExtra("date_id",0)
        labId=intent.getLongExtra("lab_id",0)
        initBottomNavigation()
        initRVAdapter()
        servicesObserver()

    }
    private fun onObserveStart(){
        sevices_progrss_lay.visibility= View.VISIBLE
        services_rv.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        sevices_progrss_lay.visibility= View.GONE
        services_rv.visibility= View.VISIBLE
    }
    private fun onObservefaled(){
        sevices_progrss_lay.visibility= View.GONE
        services_rv.visibility= View.GONE
    }

    private fun initRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        services_rv.layoutManager = layoutManager
        LabServicesListAddapter = LabServicesAdapter(this, labServicesList,dateId,labId)
        services_rv.adapter = LabServicesListAddapter
    }
    private fun servicesObserver(){
        val id =intent.getLongExtra("lab_id",-1)
        val url = Q.GET_LAB_BY_ID_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchLabById(url)
            .enqueue(object : Callback<BaseResponce<Laboratory>> {
                override fun onFailure(call: Call<BaseResponce<Laboratory>>, t: Throwable) {
                    alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<Laboratory>>, response: Response<BaseResponce<Laboratory>>) {
                    if(response!!.isSuccessful){
                        response.body()!!.data!!.laboratory_services.let {
                            labServicesList.addAll(it!!)
                            LabServicesListAddapter!!.notifyDataSetChanged()
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
        bottomNavigationView11.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView11.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}