package com.sehakhanah.patientapp.modules.doctors.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.Doctor
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.doctors.doctorProfile.DoctorProfile
import com.sehakhanah.patientapp.modules.doctors.doctorsList.DoctorsListActivity
import com.sehakhanah.patientapp.modules.doctors.reagons.ReagonsActivity
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.doctors_list_item.view.*




class SearchByNameAdapter(
    private val mContext: SearchByNameActivity,
    private val list: MutableList<Doctor>
) : RecyclerView.Adapter<SearchByNameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.doctors_list_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = list[position]
        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.doctor_name.text = "${doctor.firstName_ar} ${doctor.lastName_ar}"
            holder.doctor_gender.text = mContext.getString(R.string.doctor)
            holder.doctor_specialty.text = doctor.profissionalTitle_ar
            holder.description.text = doctor.aboutDoctor_ar
            holder.address.text = doctor.address_ar
            holder.cost.text = doctor.price.toString()
            holder.waiting.text=doctor.waiting_time
            holder.ratingBar.rating=doctor.rating.toFloat()
            holder.visitor_num.text=doctor.visitor_num.toString()+mContext.getString(R.string.visitors)
        }else{
            holder.doctor_name.text = "${doctor.firstName_en} ${doctor.lastName_en}"
            holder.doctor_gender.text = mContext.getString(R.string.doctor)
            holder.doctor_specialty.text = doctor.profissionalTitle_en
            holder.description.text = doctor.aboutDoctor_en
            holder.address.text = doctor.address_en
            holder.cost.text = doctor.price.toString()
            holder.waiting.text=doctor.waiting_time
            holder.ratingBar.rating=doctor.rating.toFloat()
            holder.visitor_num.text=doctor.visitor_num.toString()+mContext.getString(R.string.visitors)
        }

        GlideObject.GlideProfilePic(mContext,doctor.featured,holder.doctor_image)
        holder.booking_btn.setOnClickListener {
            val intent = Intent(mContext, DoctorProfile::class.java)
            intent.putExtra("firstName_ar",doctor.firstName_ar)
            intent.putExtra("firstName_en",doctor.firstName_en)
            intent.putExtra("lastName_ar",doctor.lastName_ar)
            intent.putExtra("lastName_en",doctor.lastName_en)
            intent.putExtra("aboutDoctor_ar",doctor.aboutDoctor_ar)
            intent.putExtra("aboutDoctor_en",doctor.aboutDoctor_en)
            intent.putExtra("area_id",doctor.area_id)
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
            intent.putExtra("rating",doctor.rating)
            intent.putExtra("speciality_id",doctor.speciality_id)
            intent.putExtra("address_ar",doctor.address_ar)
            intent.putExtra("address_en",doctor.address_en)
            intent.putExtra("visitor_num",doctor.visitor_num)
            intent.putExtra("waiting_time",doctor.waiting_time)
            mContext.startActivity(intent)

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctor_image: ImageView = view.doctor_image
        val doctor_gender: TextView =view.doctor_gender
        val doctor_name: TextView =view.doctor_name
        val doctor_specialty: TextView =view.doctor_specialty
        val visitor_num: TextView =view.visitor_num
        val ratingBar: RatingBar =view.ratingBar
        val description: TextView =view.description
        val address: TextView =view.address
        val cost: TextView =view.cost
        val waiting: TextView =view.waiting
        val booking_btn: Button =view.booking_btn




    }
}