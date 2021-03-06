package com.sehakhanah.patientapp.modules.doctors.dates

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
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.Date
import com.sehakhanah.patientapp.data.remote.objects.Dates
import com.sehakhanah.patientapp.data.remote.objects.Times
import com.sehakhanah.patientapp.modules.doctors.booking.BookingActivity
import com.sehakhanah.patientapp.modules.doctors.doctorProfile.DoctorProfile
import com.sehakhanah.patientapp.modules.labs.LabBookActivity
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
        if(mContext.key!=2){
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
                    intent.putExtra("address_ar",mContext.address_ar)
                    intent.putExtra("address_en",mContext.address_en)
                    intent.putExtra("datename",mContext.datename)
                    intent.putExtra("timename",time.time)
                    intent.putExtra("landmark_ar",mContext.landmark_ar)
                    intent.putExtra("landmark_en",mContext.landmark_en)
                    intent.putExtra("offer_id",mContext.id)
                    if(mContext.key==1){
                        intent.putExtra("key",1)
                    }else{
                        intent.putExtra("key",0)
                    }

                    mContext.startActivity(intent)
                }
            }else return
        }else{
            val time = list[position]
            if(time.status=="1"){
                holder.time_txt.text = time.time
                holder.time_txt.setOnClickListener {
                    val intent = Intent(mContext, LabBookActivity::class.java)
                    intent.putExtra("date_id",mContext.dateId)
                    intent.putExtra("lab_id",mContext.lab_id)
                    intent.putExtra("time_id",time.id)
                    intent.putExtra("time",time.time.toString())
                    intent.putExtra("service_id",mContext.service_id)
                    mContext.startActivity(intent)
                }
            }else return
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time_txt: TextView = view.time_item_txt
    }
}
    
