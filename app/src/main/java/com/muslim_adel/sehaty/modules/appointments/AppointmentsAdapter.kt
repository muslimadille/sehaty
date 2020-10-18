package com.muslim_adel.sehaty.modules.appointments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.apiServices.ApiClient
import com.muslim_adel.sehaty.data.remote.apiServices.SessionManager
import com.muslim_adel.sehaty.data.remote.objects.AppointmentData
import com.muslim_adel.sehaty.data.remote.objects.BaseResponce
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.doctorsList.DoctorsListActivity
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.appointment_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppointmentsAdapter(
    private val mContext: MainActivity,
    private val appointmentList: MutableList<AppointmentData>

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

        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.doctor.firstName_ar+" "+appointmentData.doctor.lastName_ar
            if(appointmentData.doctor.gender_id==1){
                holder.gendare_txt.text=mContext!!.getString(R.string.doctor)
            }else{
                holder.gendare_txt.text=mContext!!.getString(R.string.doctorah)
            }
            holder.speciality_txt.text=appointmentData.doctor.profissionalTitle_ar
            holder.location_txt.text=appointmentData.doctor.apartmentNum_ar+","+appointmentData.doctor.buildingNum_ar+","+appointmentData.doctor.streetName_ar+","+appointmentData.doctor.landmark_ar
        }
        else{
            holder.date_name_txt.text=appointmentData.booking_date
            holder.name_txt.text=appointmentData.doctor.firstName_en+" "+appointmentData.doctor.lastName_en
            if(appointmentData.doctor.gender_id==1){
                holder.gendare_txt.text=mContext!!.getString(R.string.doctor)
            }else{
                holder.gendare_txt.text=mContext!!.getString(R.string.doctorah)
            }
            holder.speciality_txt.text=appointmentData.doctor.profissionalTitle_en
            holder.location_txt.text=appointmentData.doctor.apartmentNum_en+","+appointmentData.doctor.buildingNum_en+","+appointmentData.doctor.streetName_en+","+appointmentData.doctor.landmark_en
        }
        GlideObject.GlideProfilePic(mContext,appointmentData.doctor.featured,holder.image_doc)
        holder.cancel_btn.setOnClickListener {
            bookingCancelObserver(appointmentData.id.toInt())
            removeMember(position)
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date_name_txt: TextView = view.date_name_txt
        val name_txt: TextView =view.name_txt
        val speciality_txt: TextView =view.speciality_txt
        val gendare_txt: TextView =view.gendare_txt
        val location_txt: TextView =view.location_txt
        val map_btn: LinearLayout =view.map_btn
        val cancel_btn: LinearLayout =view.cancel_btn
        val help_btn: LinearLayout =view.help_btn
        val phone_btn: ImageView =view.phone_btn
        val image_doc: ImageView =view.image_doc



    }
    fun bookingCancelObserver(id:Int){
        val url = Q.BOOKING_CANCEL_API +"/${id}"
        apiClient = ApiClient()
        sessionManager = SessionManager(mContext!!)
        apiClient.getApiService(mContext!!).bookingCancel(url)
            .enqueue(object : Callback<BaseResponce<AppointmentData>> {
                override fun onFailure(call: Call<BaseResponce<AppointmentData>>, t: Throwable) {
                   mContext!!.alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<AppointmentData>>, response: Response<BaseResponce<AppointmentData>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.let {

                                for(n in 0 until appointmentList.size){
                                    if (it.id==appointmentList[n].id){
                                        appointmentList.removeAt(n)
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