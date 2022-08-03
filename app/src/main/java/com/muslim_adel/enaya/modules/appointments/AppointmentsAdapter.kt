package com.muslim_adel.enaya.modules.appointments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.enaya.utiles.Q
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.apiServices.ApiClient
import com.muslim_adel.enaya.data.remote.apiServices.SessionManager
import com.muslim_adel.enaya.data.remote.objects.AppointmentData
import com.muslim_adel.enaya.data.remote.objects.BaseResponce
import com.muslim_adel.enaya.modules.base.GlideObject
import com.muslim_adel.enaya.modules.contactUs.ContactUsActivity
import com.muslim_adel.enaya.modules.home.MainActivity
import kotlinx.android.synthetic.main.appointment_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppointmentsAdapter(
    private val mContext: MainActivity,
    private val appointmentList: MutableList<AppointmentData>,
    private val isOffer:Boolean


) : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.appointment_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointmentData = appointmentList[position]
        var lat=appointmentData.doctor.lat
        var lng=appointmentData.doctor.lng
        val zoom=10
        var lable=appointmentData.doctor.firstName_ar
        val intent= Intent(Intent.ACTION_VIEW)
        holder.map_btn.setOnClickListener {
            intent.data= Uri.parse("geo:0,0?z=$zoom&q=$lat,$lng,$lable")
            if(intent.resolveActivity(mContext.packageManager)!=null){
                mContext.startActivity(intent)
            }
        }
        if(appointmentData.status_id==1){
            holder.buttons_lay.visibility=View.VISIBLE
        }else{
            holder.buttons_lay.visibility=View.GONE

        }

        holder.appointment_ratingBar.rating=appointmentData.doctor.rating.toFloat()

        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.doctor.firstName_ar+" "+appointmentData.doctor.lastName_ar
            holder.status_txt.text=appointmentData.status.name_ar
            holder.speciality_txt.text=appointmentData.doctor.profissionalTitle_ar
            holder.location_txt.text=appointmentData.doctor.address_ar+","+appointmentData.doctor.landmark_ar
        }
        else{
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.doctor.firstName_en+" "+appointmentData.doctor.lastName_en
            holder.status_txt.text=appointmentData.status.name_en
            holder.speciality_txt.text=appointmentData.doctor.profissionalTitle_en
            holder.location_txt.text=appointmentData.doctor.address_en+","+appointmentData.doctor.landmark_en
        }
        GlideObject.GlideProfilePic(mContext,appointmentData.doctor.featured,holder.image_doc)
        holder.cancel_btn.setOnClickListener {
            if(isOffer){
                bookingOfferCancelObserver(appointmentData.id.toInt())
            }else{
            bookingCancelObserver(appointmentData.id.toInt())}
            removeMember(position)
        }
       holder.help_btn.setOnClickListener {
           val intent=Intent(mContext, ContactUsActivity::class.java)
           mContext.startActivity(intent)
       }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date_name_txt: TextView = view.date_name_txt
        val name_txt: TextView =view.name_txt
        val speciality_txt: TextView =view.speciality_txt
        val status_txt: TextView =view.status_txt
        val location_txt: TextView =view.location_txt
        val map_btn: LinearLayout =view.map_btn
        val cancel_btn: LinearLayout =view.cancel_btn
        val help_btn: LinearLayout =view.help_btn
        val image_doc: ImageView =view.image_doc
        val appointment_ratingBar:RatingBar=view.appointment_ratingBar
        val buttons_lay:LinearLayout=view.buttons_lay



    }
    fun bookingCancelObserver(id:Int){
        val url = Q.BOOKING_CANCEL_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).bookingCancel(url)
            .enqueue(object : Callback<BaseResponce<AppointmentData>> {
                override fun onFailure(call: Call<BaseResponce<AppointmentData>>, t: Throwable) {
                   //mContext!!.alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<AppointmentData>>, response: Response<BaseResponce<AppointmentData>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.let {

                                for(n in 0 until appointmentList.size){
                                    if (it.id==appointmentList[n].id){
                                        //appointmentList.removeAt(n)
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }else{
                            //onObservefaled()
                        }

                    }else{
                        // onObservefaled()
                    }

                }


            })
    }
    fun bookingOfferCancelObserver(id:Int){
        val url = Q.BOOKING_OFFER_CANCEL_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).bookingCancel(url)
            .enqueue(object : Callback<BaseResponce<AppointmentData>> {
                override fun onFailure(call: Call<BaseResponce<AppointmentData>>, t: Throwable) {
                    //mContext!!.alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<AppointmentData>>, response: Response<BaseResponce<AppointmentData>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.let {

                                for(n in 0 until appointmentList.size){
                                    if (it.id==appointmentList[n].id){
                                        //appointmentList.removeAt(n)
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }else{
                            //onObservefaled()
                        }

                    }else{
                        // onObservefaled()
                    }

                }


            })
    }
    fun removeMember(position: Int) {
        this.appointmentList.removeAt(position)
        notifyDataSetChanged()
    }
}