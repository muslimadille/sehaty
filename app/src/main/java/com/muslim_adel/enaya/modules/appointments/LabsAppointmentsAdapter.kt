package com.muslim_adel.enaya.modules.appointments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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


class LabsAppointmentsAdapter(
    private val mContext: MainActivity,
    private val appointmentList: MutableList<AppointmentData>

) : RecyclerView.Adapter<LabsAppointmentsAdapter.ViewHolder>() {
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
        var lat=appointmentData.laboratory.lat
        var lng=appointmentData.laboratory.lng
        val zoom=10
        var lable=appointmentData.laboratory.firstName_ar
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
        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.laboratory.laboratory_name_ar
            holder.status_txt.text=appointmentData.status.name_ar
            holder.speciality_txt.visibility=View.GONE
            holder.location_txt.text=appointmentData.laboratory.address_ar+","+appointmentData.laboratory.landmark_ar
        }
        else{
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.laboratory.laboratory_name_en
            holder.status_txt.text=appointmentData.status.name_en
            holder.speciality_txt.visibility=View.GONE
            holder.location_txt.text=appointmentData.laboratory.address_en+","+appointmentData.laboratory.landmark_en
        }
        GlideObject.GlideProfilePic(mContext,appointmentData.laboratory.featured,holder.image_doc)
        holder.cancel_btn.setOnClickListener {
            bookingCancelObserver(appointmentData.id.toInt())
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
        val buttons_lay:LinearLayout=view.buttons_lay




    }
    fun bookingCancelObserver(id:Int){
        val url = Q.BOOKING_LAB_CANCEL_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).bookingCancel(url)
            .enqueue(object : Callback<BaseResponce<AppointmentData>> {
                override fun onFailure(call: Call<BaseResponce<AppointmentData>>, t: Throwable) {
                   // mContext!!.alertNetwork(true)
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