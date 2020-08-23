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
        holder.doctor_name.text = doctor.lastName_ar
        holder.doctor_gender.text = doctor.firstName_ar
        holder.doctor_specialty.text = doctor.profissionalTitle_ar
        holder.description.text = doctor.aboutDoctor_ar
        holder.address.text = doctor.streetName_ar
        holder.cost.text = doctor.price.toString()
        holder.doctor_name.text = doctor.firstName_ar
        holder.doctor_name.text = doctor.firstName_ar
        holder.doctor_name.text = doctor.firstName_ar
        holder.doctor_name.text = doctor.firstName_ar

        holder.booking_btn.setOnClickListener {
            val intent = Intent(mContext, ReagonsActivity::class.java)
            //intent.putExtra("specialty_id",specialtyId)
            //intent.putExtra("region_id",reagons.id)
            //mContext.startActivity(intent)

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