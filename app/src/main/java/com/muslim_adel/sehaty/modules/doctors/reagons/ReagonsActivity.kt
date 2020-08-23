package com.muslim_adel.sehaty.modules.doctors.reagons

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.BaseActivity
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
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reagons)
        val current: Locale = resources.configuration.locales[0]



        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        reagons_rv.layoutManager = layoutManager
        reagonsListAddapter = ReagonsAdapter(this, reagonsList)
        reagons_rv.adapter = reagonsListAddapter

        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchReagonsList()
            .enqueue(object : Callback<BaseResponce<List<Reagons>>> {
                override fun onFailure(call: Call<BaseResponce<List<Reagons>>>, t: Throwable) {
                    Toast.makeText(this@ReagonsActivity, "فشل", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<BaseResponce<List<Reagons>>>, response: Response<BaseResponce<List<Reagons>>>) {
                    if(response!!.isSuccessful){
                        response.body()!!.data.let {
                            reagonsList.addAll(it!!)
                            reagonsListAddapter!!.notifyDataSetChanged()
                        }
                    }else{
                        Toast.makeText(this@ReagonsActivity, "نجح بس ماجبش حاجة ", Toast.LENGTH_SHORT).show()
                    }

                }


            })

    }
}