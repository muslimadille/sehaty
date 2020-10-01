package com.muslim_adel.sehaty.modules.offers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.OffersCategory
import com.muslim_adel.sehaty.data.remote.objects.OffersSubGategory
import com.muslim_adel.sehaty.modules.base.BaseActivity
import kotlinx.android.synthetic.main.offers_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCtegoriesActivity : BaseActivity() {
    internal var expandableListView: ExpandableListView? = null
    internal var adapter: AllCtegoriesAdapter? = null
    internal var titleList: List<String> ? = null

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var categoriesList: MutableList<OffersCategory> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_ctegories)
        categoriesObserver()
    }
    private fun categoriesObserver() {
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(this!!)
        apiClient.getApiService(this!!).fitchOffersSGategories()
            .enqueue(object : Callback<BaseResponce<List<OffersCategory>>> {
                override fun onFailure(call: Call<BaseResponce<List<OffersCategory>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<OffersCategory>>>,
                    response: Response<BaseResponce<List<OffersCategory>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            var myList:List<OffersCategory>
                            var myMap=HashMap<String,List<OffersSubGategory>>()
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    categoriesList.addAll(it)
                                    it.forEach {cat:OffersCategory->
                                        myList = listOf(cat)
                                        myMap[cat.name_ar]=cat.subcategories
                                    }
                                    initAdapter(it,myMap)

                                } else {
                                    Toast.makeText(this@AllCtegoriesActivity, "empty", Toast.LENGTH_SHORT).show()
                                }

                            }
                        } else {
                            Toast.makeText(this@AllCtegoriesActivity, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(this@AllCtegoriesActivity, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    fun initAdapter(cat:List<OffersCategory>,sub:HashMap<String,List<OffersSubGategory>>){
        expandableListView = findViewById(R.id.expandableListView)
        if (expandableListView != null) {
            adapter = AllCtegoriesAdapter(this,cat, sub)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition ->  }

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->  }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                 intent=Intent(this,CategoryOffersListActivity::class.java)
                intent.putExtra("key",2)
                intent.putExtra("category_id",categoriesList[groupPosition].subcategories[childPosition].id)
                intent.putExtra("title_ar",categoriesList[groupPosition].name_ar)
                intent.putExtra("title_en",categoriesList[groupPosition].name_en)
                startActivity(intent)

                false
            }
        }

    }
    private fun onObserveStart(){
        //progrss_lay.visibility= View.VISIBLE
        //offer_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        //progrss_lay.visibility= View.GONE
        //offer_lay.visibility= View.VISIBLE
    }
    private fun onObservefaled(){
       // progrss_lay.visibility= View.GONE
       // offer_lay.visibility= View.GONE
    }
}