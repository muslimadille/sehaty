package com.muslim_adel.sehaty.modules.labs

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Laboratory
import com.muslim_adel.sehaty.data.remote.objects.LaboratoryServices
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.utiles.Q
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
}