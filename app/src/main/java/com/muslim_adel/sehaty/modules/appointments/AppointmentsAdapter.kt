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
import kotlinx.android.synthetic.main.appointment_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppointmentsAdapter(
    private val mContext: MainActivity,
    private val appointmentList: MutableList<AppointmentData>,
    private val doctorList: MutableList<Doctor>


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
        val doctorData = doctorList.let {
            it[position]
        }

        holder.date_name_txt.text=appointmentData.booking_date
        holder.name_txt.text=doctorData.firstName_ar+" "+doctorData.lastName_ar
        if(doctorData.gender_id==1){
            holder.gendare_txt.text=mContext!!.getString(R.string.doctor)
        }else{
            holder.gendare_txt.text=mContext!!.getString(R.string.doctorah)
        }
        holder.speciality_txt.text=doctorData.profissionalTitle_ar
        holder.location_txt.text=doctorData.apartmentNum_ar+","+doctorData.buildingNum_ar+","+doctorData.streetName_ar+","+doctorData.landmark_ar
        GlideObject.GlideProfilePic(mContext,doctorData.featured,holder.image_doc)
        holder.cancel_btn.setOnClickListener {
            bookingCancelObserver(appointmentData.id.toInt())
        }
        /*holder.booking_btn.setOnClickListener {
            val intent = Intent(mContext, DoctorProfile::class.java)
            intent.putExtra("firstName_ar",doctor.firstName_ar)
            intent.putExtra("firstName_en",doctor.firstName_en)
            intent.putExtra("lastName_ar",doctor.lastName_ar)
            intent.putExtra("lastName_en",doctor.lastName_en)
            intent.putExtra("aboutDoctor_ar",doctor.aboutDoctor_ar)
            intent.putExtra("aboutDoctor_en",doctor.aboutDoctor_en)
            intent.putExtra("apartmentNum_ar",doctor.apartmentNum_ar)
            intent.putExtra("apartmentNum_en",doctor.apartmentNum_en)
            intent.putExtra("area_id",doctor.area_id)
            intent.putExtra("buildingNum_ar",doctor.buildingNum_ar)
            intent.putExtra("featured",doctor.featured)
            intent.putExtra("gender_id",doctor.gender_id)
            intent.putExtra("doctor_id",doctor.id)
            intent.putExtra("landmark_ar",doctor.landmark_ar)
            intent.putExtra("landmark_en",doctor.landmark_en)
            intent.putExtra("phonenumber",doctor.phonenumber)
            intent.putExtra("prefixTitle_id",doctor.prefixTitle_id)
            intent.putExtra("price",doctor.price)
            intent.putExtra("profissionalDetails_id",doctor.profissionalDetails_id)
            intent.putExtra("profissionalTitle_ar",doctor.profissionalTitle_ar)
            intent.putExtra("profissionalTitle_en",doctor.profissionalTitle_en)
            intent.putExtra("role",doctor.role)
            intent.putExtra("rating",doctor.rating)
            intent.putExtra("speciality_id",doctor.speciality_id)
            intent.putExtra("streetName_ar",doctor.streetName_ar)
            intent.putExtra("streetName_en",doctor.streetName_en)
            intent.putExtra("visitor_num",doctor.visitor_num)
            intent.putExtra("waiting_time",doctor.waiting_time)
            intent.putExtra("buildingNum_en",doctor.buildingNum_en)
            mContext.startActivity(intent)

        }*/

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
        //onObserveStart()
        apiClient.getApiService(mContext!!).bookingCancel(url)
            .enqueue(object : Callback<BaseResponce<AppointmentData>> {
                override fun onFailure(call: Call<BaseResponce<AppointmentData>>, t: Throwable) {
                   mContext!!.alertNetwork(true)
                }
                override fun onResponse(call: Call<BaseResponce<AppointmentData>>, response: Response<BaseResponce<AppointmentData>>) {
                    if(response!!.isSuccessful){
                        if(response.body()!!.success){
                            response.body()!!.data!!.let {

                                for(n in 0 until mContext!!.appointmentsList.size){
                                    if (it.id==mContext!!.appointmentsList[n].id){
                                        mContext!!.appointmentsList.removeAt(n)
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
}