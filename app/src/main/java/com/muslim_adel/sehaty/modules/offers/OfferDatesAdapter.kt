package com.sehakhanah.patientapp.modules.offers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.Date
import com.sehakhanah.patientapp.data.remote.objects.Offer
import com.sehakhanah.patientapp.modules.doctors.dates.DatesActivity
import com.sehakhanah.patientapp.modules.doctors.doctorProfile.DoctorProfile
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.doctor_dates_item.view.*



class OfferDatesAdapter(
    private val mContext: OfferDetailsActivity,
    private val list: MutableList<Date>,
    private val offer: MutableList<Offer>

) : RecyclerView.Adapter<OfferDatesAdapter.ViewHolder>() {

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
            if (mContext.preferences!!.getString("language","")=="Arabic"){
                holder.day_txt.text=date.day_ar+"\n ${date.date}"
                holder.start_time_txt.text=date.times[0].time
                holder.end_time_txt.text=date.times[date.times.size-1].time
            }else{
                holder.day_txt.text=date.day_en+"\n ${date.date}"
                holder.start_time_txt.text=date.times[0].time
                holder.end_time_txt.text=date.times[date.times.size-1].time
            }

            holder.book_btn.setOnClickListener {
                val intent = Intent(mContext, DatesActivity::class.java)
                intent.putExtra("date_id",date.id)
                intent.putExtra("firstName_ar",offer[0].doctor.firstName_ar)
                intent.putExtra("firstName_en",offer[0].doctor.firstName_en)
                intent.putExtra("lastName_ar",offer[0].doctor.lastName_ar)
                intent.putExtra("lastName_en",offer[0].doctor.lastName_en)
                intent.putExtra("featured",offer[0].doctor.featured)
                intent.putExtra("doctor_id",offer[0].doctor.id)
                intent.putExtra("phonenumber",offer[0].doctor.phonenumber)
                intent.putExtra("price",offer[0].doctor.price)
                intent.putExtra("profissionalTitle_ar",offer[0].doctor.profissionalTitle_ar)
                intent.putExtra("profissionalTitle_en",offer[0].doctor.profissionalTitle_en)
                intent.putExtra("address_ar",offer[0].doctor.address_ar)
                intent.putExtra("address_en",offer[0].doctor.address_en)
                intent.putExtra("landmark_ar",offer[0].doctor.landmark_ar)
                intent.putExtra("landmark_en",offer[0].doctor.landmark_en)
                intent.putExtra("offer_id",offer[0].id)
                intent.putExtra("key",1)
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