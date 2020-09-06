package com.muslim_adel.sehaty.modules.doctors.doctorProfile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Date
import com.muslim_adel.sehaty.data.remote.objects.Dates
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.doctorsList.DoctorsListActivity
import kotlinx.android.synthetic.main.doctor_dates_item.view.*
import kotlinx.android.synthetic.main.doctors_list_item.view.*
import kotlinx.android.synthetic.main.doctors_list_item.view.doctor_gender


class DatesAdapter(
    private val mContext: DoctorProfile,
    private val list: MutableList<Date>
) : RecyclerView.Adapter<DatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.doctor_dates_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = list[position]
        if (date.status==1){
            holder.day_txt.text=date.day_ar+"\n ${date.date}"
            holder.start_time_txt.text=date.times[0].time
            holder.end_time_txt.text=date.times[date.times.size-1].time
            holder.book_btn.setOnClickListener {

            }
        }else return

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day_txt: TextView =view.day_txt
        val start_time_txt: TextView =view.start_time_txt
        val end_time_txt: TextView =view.end_time_txt
        val book_btn: LinearLayout =view.book_btn
    }
}