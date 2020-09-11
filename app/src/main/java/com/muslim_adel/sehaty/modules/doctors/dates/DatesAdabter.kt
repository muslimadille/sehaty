package com.muslim_adel.sehaty.modules.doctors.dates

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Date
import com.muslim_adel.sehaty.data.remote.objects.Dates
import com.muslim_adel.sehaty.data.remote.objects.Times
import com.muslim_adel.sehaty.modules.doctors.booking.BookingActivity
import com.muslim_adel.sehaty.modules.doctors.doctorProfile.DoctorProfile
import kotlinx.android.synthetic.main.date_item_row.view.*
import kotlinx.android.synthetic.main.doctor_dates_item.view.*
import kotlinx.android.synthetic.main.time_item.view.*
import kotlinx.android.synthetic.main.time_item_row.view.*


class DatesAdabter(
    private val mContext: DatesActivity,
    private val list: MutableList<Times>
) : RecyclerView.Adapter<DatesAdabter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(
            R.layout.time_item, parent, false
        )
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time = list[position]
        if(time.status=="1"){
            holder.time_txt.text = time.time
            holder.time_txt.setOnClickListener {
                val intent = Intent(mContext, BookingActivity::class.java)
                intent.putExtra("date_id",mContext.date_id)
                intent.putExtra("firstName_ar",mContext.firstName_ar)
                intent.putExtra("firstName_en",mContext.firstName_en)
                intent.putExtra("lastName_ar",mContext.lastName_ar)
                intent.putExtra("lastName_en",mContext.lastName_en)
                intent.putExtra("featured",mContext.featured)
                intent.putExtra("doctor_id",mContext.doctor_id)
                intent.putExtra("phonenumber",mContext.phonenumber)
                intent.putExtra("price",mContext.price)
                intent.putExtra("profissionalTitle_ar",mContext.profissionalTitle_ar)
                intent.putExtra("profissionalTitle_en",mContext.profissionalTitle_en)
                intent.putExtra("streetName_ar",mContext.streetName_ar)
                intent.putExtra("streetName_en",mContext.streetName_en)
                intent.putExtra("datename",mContext.datename)
                intent.putExtra("timename",time.time)
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
        val time_txt: TextView = view.time_item_txt
    }
}
    
