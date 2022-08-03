package com.muslim_adel.enaya.modules.doctors.doctorProfile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.enaya.R
import com.muslim_adel.enaya.data.remote.objects.Rates
import kotlinx.android.synthetic.main.rate_item.view.*

class RatesAdapter(
    private val mContext: DoctorProfile,
    private val list: MutableList<Rates>
) : RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.rate_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate = list[position]

        holder.rate.rating = rate.rate.toFloat()
        holder.comment.text = rate.comment
        holder.name.text = rate.person_name
        holder.date.text=rate.date

       /* holder.book_btn.setOnClickListener {
            val intent = Intent(mContext, DatesActivity::class.java)
            intent.putExtra("date_id", date.id)
            intent.putExtra("firstName_ar", mContext.firstName_ar)
            intent.putExtra("firstName_en", mContext.firstName_en)
            intent.putExtra("lastName_ar", mContext.lastName_ar)
            intent.putExtra("lastName_en", mContext.lastName_en)
            intent.putExtra("featured", mContext.featured)
            intent.putExtra("doctor_id", mContext.id)
            intent.putExtra("phonenumber", mContext.phonenumber)
            intent.putExtra("price", mContext.price)
            intent.putExtra("profissionalTitle_ar", mContext.profissionalTitle_ar)
            intent.putExtra("profissionalTitle_en", mContext.profissionalTitle_en)
            intent.putExtra("address_ar", mContext.address_ar)
            intent.putExtra("address_en", mContext.address_en)
            mContext.startActivity(intent)
        }*/


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rate: RatingBar = view.rating_rate
        val comment: TextView = view.rating_comment
        val name: TextView = view.rating_name
        val date: TextView = view.rating_date
    }
}