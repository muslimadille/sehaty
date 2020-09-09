package com.muslim_adel.sehaty.modules.doctors.doctorsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.doctorProfile.DoctorProfile
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsActivity
import kotlinx.android.synthetic.main.doctors_list_item.view.*
import kotlinx.android.synthetic.main.reagon_item.view.*


class DoctorsListAdapter(
    private val mContext: DoctorsListActivity,
    private val list: MutableList<Doctor>
) : RecyclerView.Adapter<DoctorsListAdapter.ViewHolder>() {

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
        holder.doctor_name.text = "${doctor.firstName_ar} ${doctor.lastName_ar}"
        holder.doctor_gender.text = mContext.getString(R.string.doctor)
        holder.doctor_specialty.text = doctor.profissionalTitle_ar
        holder.description.text = doctor.aboutDoctor_ar
        holder.address.text = doctor.streetName_ar
        holder.cost.text = doctor.price.toString()
        holder.waiting.text=doctor.waiting_time
        holder.ratingBar.rating=doctor.rating.toFloat()
        holder.visitor_num.text=doctor.visitor_num.toString()+mContext.getString(R.string.visitors)
        GlideObject.GlideProfilePic(mContext,doctor.featured,holder.doctor_image)
        holder.booking_btn.setOnClickListener {
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
            intent.putExtra("id",doctor.id)
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