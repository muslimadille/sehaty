package com.muslim_adel.enaya.modules.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.muslim_adel.enaya.modules.base.SpinnerAdapterCustomFont
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.data.remote.objects.CountryModel
import com.muslim_adel.enaya.modules.doctors.reagons.ReagonsActivity
import com.muslim_adel.enaya.modules.doctors.search.SearchByNameActivity
import com.muslim_adel.enaya.modules.doctors.search.SearchBySpecialityActivity
import com.muslim_adel.enaya.modules.favorits.FavoritsActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import com.muslim_adel.enaya.modules.labs.LabsActivity
import com.muslim_adel.enaya.modules.labs.LabsListActivity
import com.muslim_adel.enaya.modules.pharmacy.PharmacyOffersActivity
import com.muslim_adel.enaya.modules.pharmacy.PharmaySearchActivity
import kotlinx.android.synthetic.main.activity_select_country.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.countries_spinner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error

class HomeFragment : Fragment() {
    private lateinit var countriesSpinnerAdapter: SpinnerAdapterCustomFont
    private var selectedCountriesIndex=0
    private var countriesNamesList=ArrayList<String>()
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_fragment, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onNameSearchClicked()
        onSpicialistSearchClicked()
        onfarmacySearchClicked()
        onLabsSearchClicked()
        initLanguageSpinner()
        getCountries()
    }

    fun onNameSearchClicked(){
        search_name_btn.setOnClickListener {
            val intent = Intent(context, SearchByNameActivity::class.java)
            startActivity(intent)
        }
    }

    fun onSpicialistSearchClicked(){
        search_spicialist_btn.setOnClickListener {
            val intent = Intent(context, SearchBySpecialityActivity::class.java)
            startActivity(intent)
        }
    }

    fun onfarmacySearchClicked(){
        pharmacy_search_btn.setOnClickListener {
            val intent = Intent(context, PharmaySearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun onLabsSearchClicked(){
        labs_search_btn.setOnClickListener {
            val intent = Intent(context, LabsActivity::class.java)
            startActivity(intent)

        }
    }
    fun initLanguageSpinner(){
        countriesSpinnerAdapter = SpinnerAdapterCustomFont(mContext!!, android.R.layout.simple_spinner_item, countriesNamesList)
        countriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countriesSpinnerAdapter.textSize = 12
        countries_spinner.adapter = countriesSpinnerAdapter
        countries_spinner.setSelection(0)
        countriesSpinnerAdapter.notifyDataSetChanged()
        countries_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCountriesIndex=position
                Q.selectedCountry= Q.countriesList[position]
                mContext!!.preferences!!.putInteger("COUNTRY_ID", Q.countriesList[position].id!!)
                mContext!!.preferences!!.commit()

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        try {
            if(Q.selectedCountry!=null) {
                if(Q.selectedCountry.id==1){
                    countries_spinner.setSelection(0)
                }else if(Q.selectedCountry.id==2){
                    countries_spinner.setSelection(1)
                }
            }
        }catch (e:Error){

        }

    }
    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }
    fun getCountries(){
        if(Q.countriesList.isEmpty()){
            apiClient = ApiClient()
            sessionManager = SessionManager(mContext!!)
            apiClient.getApiService(mContext!!)
                .getAllCountriesList()
                .enqueue(object : Callback<BaseResponce<List<CountryModel>>> {
                    override fun onFailure(call: Call<BaseResponce<List<CountryModel>>>, t: Throwable) {
                        mContext!!.alertNetwork(true)
                    }

                    override fun onResponse(
                        call: Call<BaseResponce<List<CountryModel>>>,
                        response: Response<BaseResponce<List<CountryModel>>>
                    ) {
                        val myResponse = response.body()
                        if (myResponse!!.success) {
                            Q.countriesList.clear()
                            Q.countriesList.addAll(myResponse.data!!)
                            Q.countriesList.forEach{
                                if (mContext!!.preferences!!.getString("language","")=="English"){
                                    countriesNamesList.add(it.nameEn!!)
                                }else{
                                    countriesNamesList.add(it.nameAr!!)
                                }

                            }
                            Q.countriesList.clear()
                            Q.countriesList.addAll(myResponse.data!!)
                            Q.selectedCountry=Q.countriesList[0]
                            countriesSpinnerAdapter.notifyDataSetChanged()
                        } else {

                            Toast.makeText(
                                mContext!!,
                                "Error:${myResponse.data}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                })
        }else{
            Q.countriesList.forEach{
                if (mContext!!.preferences!!.getString("language","")=="English"){
                    countriesNamesList.add(it.nameEn!!)
                }else{
                    countriesNamesList.add(it.nameAr!!)
                }}
            countriesSpinnerAdapter.notifyDataSetChanged()
        }

    }

}