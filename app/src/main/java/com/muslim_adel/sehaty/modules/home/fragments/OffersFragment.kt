package com.muslim_adel.sehaty.modules.home.fragments

import android.app.Activity
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Offer
import com.muslim_adel.sehaty.data.remote.objects.OfferSlider
import com.muslim_adel.sehaty.data.remote.objects.OffersCategory
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.offers.AllCtegoriesActivity
import com.muslim_adel.sehaty.modules.offers.CategoriesAdapter
import com.muslim_adel.sehaty.modules.offers.OfferAdapter
import com.muslim_adel.sehaty.modules.offers.OffersPagerAdapter
import kotlinx.android.synthetic.main.offers_fragment.*
import me.relex.circleindicator.CircleIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class OffersFragment : Fragment() {
    private val imagesList: MutableList<String> = ArrayList()

    private var sliderAddapter: OffersPagerAdapter? = null
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var categoriesList: MutableList<OffersCategory> = ArrayList()
    private var categoriesListAddapter: CategoriesAdapter? = null

    private var offersList: MutableList<Offer> = ArrayList()
    private var offersListAddapter: OfferAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.offers_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sliderImagesObserver()
        initSlider()
        initRVAdapter()
        categoriesObserver()
        offersObserver()
        show_all_btn.setOnClickListener {
            mContext!!.intent= Intent(mContext, AllCtegoriesActivity::class.java)
            mContext!!.startActivity(mContext!!.intent)
        }
        super.onViewCreated(view, savedInstanceState)


    }
    fun alertNetwork(isExit: Boolean = true) {
        val alertBuilder = AlertDialog.Builder(mContext!!)
        //alertBuilder.setTitle(R.string.error)
        alertBuilder.setMessage(R.string.no_internet)
        if (isExit) {
            // alertBuilder.setPositiveButton(R.string.exit) { dialog: DialogInterface, _: Int -> context!!.finish() }
        } else {
            alertBuilder.setPositiveButton(R.string.dismiss) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        }
        alertBuilder.show()
    }

    private fun sliderImagesObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).fitchOffersSLiderImages()
            .enqueue(object : Callback<BaseResponce<List<OfferSlider>>> {
                override fun onFailure(call: Call<BaseResponce<List<OfferSlider>>>, t: Throwable) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<OfferSlider>>>,
                    response: Response<BaseResponce<List<OfferSlider>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    it.forEach { offer: OfferSlider ->
                                        imagesList.add(offer.featured_ar)
                                        sliderAddapter!!.notifyDataSetChanged()
                                    }
                                } else {
                                    Toast.makeText(mContext, "empty", Toast.LENGTH_SHORT).show()
                                }

                            }
                        } else {
                            Toast.makeText(mContext, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(mContext, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    private fun categoriesObserver() {
        onObserveStart()
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).fitchOffersSGategories()
            .enqueue(object : Callback<BaseResponce<List<OffersCategory>>> {
                override fun onFailure(
                    call: Call<BaseResponce<List<OffersCategory>>>,
                    t: Throwable
                ) {
                    alertNetwork(true)
                }

                override fun onResponse(
                    call: Call<BaseResponce<List<OffersCategory>>>,
                    response: Response<BaseResponce<List<OffersCategory>>>
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body()!!.success) {
                            response.body()!!.data!!.let {
                                if (it.isNotEmpty()) {
                                    categoriesList.addAll(it)
                                    categoriesListAddapter!!.notifyDataSetChanged()
                                } else {
                                    Toast.makeText(mContext, "empty", Toast.LENGTH_SHORT).show()
                                }

                            }
                        } else {
                            Toast.makeText(mContext, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(mContext, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    private fun offersObserver() {
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).fitchOffersMostRequest()
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
                                    Toast.makeText(mContext, "empty", Toast.LENGTH_SHORT).show()
                                }

                            }
                        } else {
                            Toast.makeText(mContext, "faid", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(mContext, "connect faid", Toast.LENGTH_SHORT).show()

                    }

                }


            })
    }
    var mContext: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as MainActivity
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initSlider(){


        sliderAddapter = OffersPagerAdapter(mContext!!, imagesList)
        offers_pager_Slider.adapter=sliderAddapter
        val indicator: CircleIndicator = requireView().findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(offers_pager_Slider)
        sliderAddapter!!.registerDataSetObserver(indicator.getDataSetObserver());

        val handler = Handler()
        val update = Runnable {
            if(offers_pager_Slider!=null){
                var count=0

                if (offers_pager_Slider.getCurrentItem() == 0) {
                    offers_pager_Slider.currentItem = count+1
                    count += 1
                } else if (offers_pager_Slider.getCurrentItem() == imagesList.size-1) {
                    count=0
                    offers_pager_Slider.currentItem = count
                } else {
                    offers_pager_Slider.currentItem = count
                    count+=1
                }
            }


        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2500, 2500)

    }
    private fun initRVAdapter() {
        val layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        val offersLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)

        categories_rv.layoutManager = layoutManager
        categoriesListAddapter= CategoriesAdapter(mContext!!, categoriesList)
        categories_rv.adapter = categoriesListAddapter

        offers_rv.layoutManager = offersLayoutManager
        offersListAddapter= OfferAdapter(mContext!!, offersList)
        offers_rv.adapter = offersListAddapter


    }

    private fun onObserveStart(){
        progrss_lay?.let {
            it.visibility= View.VISIBLE
        }
        offer_lay.visibility= View.GONE
    }
    private fun onObserveSuccess(){
        progrss_lay?.let {
            it.visibility= View.GONE
        }
        offer_lay?.let {
            it.visibility= View.VISIBLE
        }
    }
    private fun onObservefaled(){
        progrss_lay?.let {
            it.visibility= View.GONE
        }
        offer_lay?.let {
            it.visibility= View.GONE
        }
    }
    var dotsCount: Int = 0
    var dots = ArrayList<RadioButton>()
    var mLastPositionOffset = 0f


    }
    fun convertDpToPixel(dp: Int, cont: Context): Int {
        val resources = cont.resources
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi / 160f)).toInt()
    }
