package com.sehakhanah.patientapp.modules.doctors.reagons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.Reagons
import com.sehakhanah.patientapp.data.remote.objects.Specialties
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.doctors.doctorsList.DoctorsListActivity
import com.sehakhanah.patientapp.modules.labs.LabsListActivity
import com.sehakhanah.patientapp.modules.pharmacy.PharmacyOffersActivity
import com.sehakhanah.patientapp.modules.register.LoginActivity
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.reagon_item.view.*
import kotlinx.android.synthetic.main.specialties_list_item.view.*

class ReagonsAdapter(
    private val mContext: ReagonsActivity,
    private val list: MutableList<Reagons>,
    private val specialtyId:Int=0
) : RecyclerView.Adapter<ReagonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.reagon_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reagons = list[position]
        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.txtTitle.text = reagons.area_ar
        }else{
            holder.txtTitle.text = reagons.area_en
        }


        holder.reagonsLay.setOnClickListener {
            if(mContext.key==1){
                val intent = Intent(mContext, LabsListActivity::class.java)
                intent.putExtra("region_id",reagons.id)
                intent.putExtra("key",1)
                mContext.startActivity(intent)
            }else if(mContext.key==2){
                val intent = Intent(mContext, PharmacyOffersActivity::class.java)
                intent.putExtra("region_id",reagons.id)
                mContext.startActivity(intent)
            }
            else{
                val intent = Intent(mContext, DoctorsListActivity::class.java)
                intent.putExtra("specialty_id",specialtyId)
                intent.putExtra("region_id",reagons.id)
                mContext.startActivity(intent)
            }


        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.reagons_txt
        val reagonsLay: LinearLayout =view.reagons_lay

    }
}
