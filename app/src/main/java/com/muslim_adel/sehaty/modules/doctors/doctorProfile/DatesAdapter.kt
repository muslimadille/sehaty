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
import com.muslim_adel.sehaty.modules.doctors.dates.DatesActivity
import kotlinx.android.synthetic.main.doctor_dates_item.view.*


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
                val intent = Intent(mContext, DatesActivity::class.java)
                intent.putExtra("date_id",date.id)
                intent.putExtra("firstName_ar",mContext.firstName_ar)
                intent.putExtra("firstName_en",mContext.firstName_en)
                intent.putExtra("lastName_ar",mContext.lastName_ar)
                intent.putExtra("lastName_en",mContext.lastName_en)
                intent.putExtra("featured",mContext.featured)
                intent.putExtra("doctor_id",mContext.id)
                intent.putExtra("phonenumber",mContext.phonenumber)
                intent.putExtra("price",mContext.price)
                intent.putExtra("profissionalTitle_ar",mContext.profissionalTitle_ar)
                intent.putExtra("profissionalTitle_en",mContext.profissionalTitle_en)
                intent.putExtra("streetName_ar",mContext.streetName_ar)
                intent.putExtra("streetName_en",mContext.streetName_en)
                intent.putExtra("apartmentNum_ar",mContext.apartmentNum_ar)
                intent.putExtra("apartmentNum_en",mContext.apartmentNum_en)
                intent.putExtra("landmark_ar",mContext.landmark_ar)
                intent.putExtra("landmark_en",mContext.landmark_en)
                intent.putExtra("buildingNum_ar",mContext.buildingNum_ar)
                intent.putExtra("role",mContext.role)
                intent.putExtra("buildingNum_en",mContext.buildingNum_en)
                mContext.startActivity(intent)
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