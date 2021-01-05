package com.sehakhanah.patientapp.modules.pharmacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Offer
import com.sehakhanah.patientapp.data.remote.objects.PharmacyOffer
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.modules.offers.OfferAdapter
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_category_offers_list.*
import kotlinx.android.synthetic.main.activity_category_offers_list.no_search_lay
import kotlinx.android.synthetic.main.activity_category_offers_list.progrss_lay
import kotlinx.android.synthetic.main.activity_pharmacy_offers.*
import kotlinx.android.synthetic.main.offers_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PharmacyOffersActivity : BaseActivity() {
    var region_id=0
    var key=0
    private var offersList: MutableList<PharmacyOffer> = ArrayList()
    private var offersListAddapter: PharmacyOffersAdapter? = null

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_offers)
        region_id=intent.getIntExtra("region_id",0)
        initRVAdapter()
        pharmacyOffersObserver()
        initBottomNavigation()
    }
    private fun pharmacyOffersObserver() {
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchPharmacyOffers(region_id)
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
    private fun initRVAdapter() {
        val offersLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        pharmacy_offers_rv.layoutManager = offersLayoutManager
        offersListAddapter = PharmacyOffersAdapter(this, offersList)
        pharmacy_offers_rv.adapter = offersListAddapter

    }

    private fun onObserveStart() {
        progrss_lay.visibility = View.VISIBLE
        pharmacy_offers_rv.visibility = View.GONE
        no_search_lay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        progrss_lay.visibility = View.GONE
        no_search_lay.visibility = View.GONE

        pharmacy_offers_rv.visibility = View.VISIBLE
    }

    private fun onObservefaled() {
        no_search_lay.visibility = View.VISIBLE
        progrss_lay.visibility = View.GONE
        pharmacy_offers_rv.visibility = View.GONE
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
        bottomNavigationView16.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView16.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}