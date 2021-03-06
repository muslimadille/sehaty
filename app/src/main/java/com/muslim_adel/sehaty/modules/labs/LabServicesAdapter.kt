package com.sehakhanah.patientapp.modules.labs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.LaboratoryServices
import com.sehakhanah.patientapp.data.remote.objects.Reagons
import com.sehakhanah.patientapp.modules.doctors.dates.DatesActivity
import com.sehakhanah.patientapp.modules.doctors.doctorsList.DoctorsListActivity
import com.sehakhanah.patientapp.modules.doctors.reagons.ReagonsActivity
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.reagon_item.view.*


class LabServicesAdapter(
    private val mContext: LabServicesActivity,
    private val list: MutableList<LaboratoryServices>,
    private val dateId: Int,
    private val labId: Long

) : RecyclerView.Adapter<LabServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.reagon_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = list[position]
        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.txtTitle.text = service.name_ar
        }else{
            holder.txtTitle.text = service.name_en
        }
        holder.reagonsLay.setOnClickListener {
            val intent = Intent(mContext, DatesActivity::class.java)
            intent.putExtra("date_id", dateId)
            intent.putExtra("key", 2)
            intent.putExtra("service_id", service.id)
            intent.putExtra("lab_id", labId)
            mContext.startActivity(intent)


        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.reagons_txt
        val reagonsLay: LinearLayout = view.reagons_lay

    }
}
