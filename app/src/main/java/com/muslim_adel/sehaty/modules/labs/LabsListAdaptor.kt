package com.sehakhanah.patientapp.modules.labs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslim_adel.sehaty.utiles.Q
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.Laboratory
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.utiles.ComplexPreferences
import kotlinx.android.synthetic.main.labs_item.view.*


class LabsListAdaptor(
    private val mContext: Context,
    private val list: MutableList<Laboratory>
) : RecyclerView.Adapter<LabsListAdaptor.ViewHolder>() {
    var preferences: ComplexPreferences? = null
    init {
        preferences = ComplexPreferences.getComplexPreferences(mContext, Q.PREF_FILE, Q.MODE_PRIVATE)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.labs_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var imgUrl=""
        val lab = list[position]
        if(lab.laboratory_photos!=null&&lab.laboratory_photos.isNotEmpty()){
             imgUrl=lab.laboratory_photos[0].featured
        }else{
             imgUrl=""

        }

        if (preferences!!.getString("language","")=="Arabic"){
            holder.lab_name.text = "${lab.laboratory_name_ar}"
            holder.lab_address.text =  "${lab.address_ar}"
            holder.ratingBar.rating=lab.rating.toFloat()
        }else{
            holder.lab_name.text = "${lab.laboratory_name_en}"
            holder.lab_address.text =  "${lab.address_en}"
            holder.ratingBar.rating=lab.rating.toFloat()
        }

        GlideObject.GlideProfilePic(mContext,lab.featured,holder.lad_image)
        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(imgUrl)
            .centerCrop()
            .into(holder.lab_slider_image)
        holder.booking_btn.setOnClickListener {
            val intent=Intent(mContext,LabDetailsActivity::class.java)
            intent.putExtra("lab_id",lab.id)
            mContext.startActivity(intent)
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lab_slider_image: ImageView = view.lab_slider_images
        val lad_image:  ImageView = view.lab_img
        val lab_name: TextView =view.lab_name_txt
        val lab_address: TextView =view.lab_address_txt
        val ratingBar: RatingBar =view.lab_ratingBar
        val booking_btn: CardView =view.offer_lay




    }
}