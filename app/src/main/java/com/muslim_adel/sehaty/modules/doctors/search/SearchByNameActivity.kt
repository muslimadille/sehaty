package com.muslim_adel.sehaty.modules.doctors.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.*
import com.muslim_adel.sehaty.modules.base.BaseActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.labs.LabsListAdaptor
import com.muslim_adel.sehaty.modules.pharmacy.PharmacyOffersAdapter
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_doctors_list.doctors_rv
import kotlinx.android.synthetic.main.activity_doctors_list.no_search_lay
import kotlinx.android.synthetic.main.activity_doctors_list.progrss_lay
import kotlinx.android.synthetic.main.activity_doctors_list.specialty_search_txt
import kotlinx.android.synthetic.main.activity_pharmacy_offers.*
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

    private var labsList:MutableList<Laboratory> = ArrayList()
    private var filteredLabsList:MutableList<Laboratory> = ArrayList()
    private var labsListAddapter: LabsListAdaptor?=null

    private var offersList: MutableList<PharmacyOffer> = ArrayList()
    private var offersListAddapter: PharmacyOffersAdapter? = null
    var key=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_name)
        initBottomNavigation()
        key=intent.getIntExtra("key",0)
        if(key==1){
            specialty_search_txt.hint=getString(R.string.search_by_nam_labs)
            initLabsRVAdapter()
            labsSearch()
        }else if(key==2){
            specialty_search_txt.hint=getString(R.string.ph_search_name)
            initPhRVAdapter()
            pharmacySearch()

        }else{
            initRVAdapter()
            search()
        }



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
    private fun labsSearch() {
        search_btn.setOnClickListener {
            labsObserver(specialty_search_txt.text.toString())
        }

        specialty_search_txt.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            labsObserver(specialty_search_txt.text.toString())
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
    private fun pharmacySearch() {
        search_btn.setOnClickListener {
            pharmacyOffersObserver(specialty_search_txt.text.toString())
        }

        specialty_search_txt.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            pharmacyOffersObserver(specialty_search_txt.text.toString())
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
    private fun labsObserver(name: String){
        filteredLabsList.clear()
        labsListAddapter!!.notifyDataSetChanged()
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchLabsByNameList(name)
            .enqueue(object : Callback<BaseResponce<LabsSearch>> {
                override fun onFailure(call: Call<BaseResponce<LabsSearch>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<LabsSearch>>,
                    response: Response<BaseResponce<LabsSearch>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response!!.body()!!.success) {
                            response.body()!!.data!!.search.let {
                                if (it.isNotEmpty()) {
                                    labsList.addAll(it)
                                    filteredLabsList.addAll(it)
                                    labsListAddapter!!.notifyDataSetChanged()
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
    private fun initLabsRVAdapter(){
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctors_rv.layoutManager = layoutManager
        labsListAddapter = LabsListAdaptor(this, filteredLabsList)
        doctors_rv.adapter = labsListAddapter
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
        bottomNavigationView5.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView5.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private fun pharmacyOffersObserver(name: String) {
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchPharmacyOffersByName(name)
            .enqueue(object : Callback<BaseResponce<List<PharmacyOffer>>> {
                override fun onFailure(call: Call<BaseResponce<List<PharmacyOffer>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<PharmacyOffer>>>,
                    response: Response<BaseResponce<List<PharmacyOffer>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    offersList.addAll(it)
                                    offersListAddapter!!.notifyDataSetChanged()
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
    private fun initPhRVAdapter() {
        val offersLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        doctors_rv.layoutManager = offersLayoutManager
        offersListAddapter = PharmacyOffersAdapter(this, offersList)
        doctors_rv.adapter = offersListAddapter

    }
}