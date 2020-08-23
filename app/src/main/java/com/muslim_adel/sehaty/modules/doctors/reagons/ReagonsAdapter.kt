package com.muslim_adel.sehaty.modules.doctors.reagons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Reagons
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.doctorsList.DoctorsListActivity
import com.muslim_adel.sehaty.modules.register.LoginActivity
import com.muslim_adel.sehaty.utiles.Q
import kotlinx.android.synthetic.main.reagon_item.view.*
import kotlinx.android.synthetic.main.specialties_list_item.view.*

class ReagonsAdapter(
    private val mContext: Context,
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
        holder.txtTitle.text = reagons.area_ar
        holder.reagonsLay.setOnClickListener {
            val intent = Intent(mContext, DoctorsListActivity::class.java)
            intent.putExtra("specialty_id",specialtyId)
            intent.putExtra("region_id",reagons.id)
            mContext.startActivity(intent)

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.reagons_txt
        val reagonsLay: LinearLayout =view.reagons_lay

    }
}
