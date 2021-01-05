package com.sehakhanah.patientapp.modules.offers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.apiServices.ApiClient
import com.sehakhanah.patientapp.data.remote.apiServices.SessionManager
import com.sehakhanah.patientapp.data.remote.objects.BaseResponce
import com.sehakhanah.patientapp.data.remote.objects.Offer
import com.sehakhanah.patientapp.modules.base.BaseActivity
import com.sehakhanah.patientapp.modules.home.MainActivity
import com.sehakhanah.patientapp.utiles.Q
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_category_offers_list.*
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.offers_fragment.*
import kotlinx.android.synthetic.main.offers_fragment.offer_lay
import kotlinx.android.synthetic.main.offers_fragment.progrss_lay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryOffersListActivity : BaseActivity() {
    private var offersList: MutableList<Offer> = ArrayList()
    private var offersListAddapter: OfferAdapter? = null
    private var key=0
    private var title=""

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_offers_list)
        initBottomNavigation()
        key=intent.getIntExtra("key",0)
        if (preferences!!.getString("language","")=="Arabic"){
            intent.getStringExtra("title_ar")?.let {
                title=it
            }
        }else{
            intent.getStringExtra("title_en")?.let {
                title=it
            }
        }

        page_title.text=title


        if(key==0){
            offersObserver()
        }else{

            subOffersObserver()
        }
        initRVAdapter()

    }

    private fun offersObserver() {
        onObserveStart()
        val id =intent.getLongExtra("category_id",-1)
        val url = Q.MAIN_CATEGORY_OFFERS_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchMainCategoryOffersList(url)
            .enqueue(object : Callback<BaseResponce<List<Offer>>> {
                override fun onFailure(call: Call<BaseResponce<List<Offer>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<Offer>>>,
                    response: Response<BaseResponce<List<Offer>>>
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
    private fun subOffersObserver() {
        onObserveStart()
        val id =intent.getLongExtra("category_id",-1)
        val url = Q.SUB_CATEGORY_OFFERS_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).fitchMainCategoryOffersList(url)
            .enqueue(object : Callback<BaseResponce<List<Offer>>> {
                override fun onFailure(call: Call<BaseResponce<List<Offer>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<Offer>>>,
                    response: Response<BaseResponce<List<Offer>>>
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
        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val offersLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        /*categories_rv.layoutManager = layoutManager
        categoriesListAddapter= CategoriesAdapter(this, categoriesList)
        categories_rv.adapter = categoriesListAddapter*/

        category_offers_rv.layoutManager = offersLayoutManager
        offersListAddapter = OfferAdapter(this, offersList)
        category_offers_rv.adapter = offersListAddapter

    }

    private fun onObserveStart() {
        progrss_lay.visibility = View.VISIBLE
        category_offers_rv.visibility = View.GONE
        no_search_lay.visibility = View.GONE
    }

    private fun onObserveSuccess() {
        progrss_lay.visibility = View.GONE
        no_search_lay.visibility = View.GONE

        category_offers_rv.visibility = View.VISIBLE
    }

    private fun onObservefaled() {
        no_search_lay.visibility = View.VISIBLE
        progrss_lay.visibility = View.GONE
        category_offers_rv.visibility = View.GONE
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
        bottomNavigationView14.labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView14.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}