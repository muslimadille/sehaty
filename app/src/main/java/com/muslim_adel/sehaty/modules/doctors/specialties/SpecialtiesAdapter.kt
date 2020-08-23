package com.muslim_adel.sehaty.modules.doctors.specialties

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Specialties
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.doctors.reagons.ReagonsActivity
import com.muslim_adel.sehaty.modules.register.LoginActivity
import com.muslim_adel.sehaty.utiles.Q
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.specialties_list_item.view.*

class SpecialtiesAdapter(
    private val mContext: Context,
    private val list: MutableList<Specialties>
) : RecyclerView.Adapter<SpecialtiesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.specialties_list_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specialty = list[position]
        holder.txtTitle.text = specialty.name_ar
        val imageName = Q.AVATAR_PATH + specialty.icon
        GlideObject.GlideProfilePic(mContext, imageName, holder.specialtyImg)
        holder.specialtyLay.setOnClickListener {
            val intent = Intent(mContext, ReagonsActivity::class.java)
            mContext.startActivity(intent)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.specialities_txt
        val specialtyImg: ImageView = view.specialties_iv
        val specialtyLay:LinearLayout=view.specialty_lay

    }
}
