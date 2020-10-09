package com.muslim_adel.sehaty.modules.labs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Doctor
import com.muslim_adel.sehaty.data.remote.objects.Laboratory
import com.muslim_adel.sehaty.modules.base.GlideObject
import kotlinx.android.synthetic.main.labs_item.view.*


class LabsListAdaptor(
    private val mContext: Context,
    private val list: MutableList<Laboratory>
) : RecyclerView.Adapter<LabsListAdaptor.ViewHolder>() {

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
        if(lab.laboratory_photos!=null){
             imgUrl=lab.laboratory_photos[0].featured
        }else{
             imgUrl=""

        }
        holder.lab_name.text = "${lab.laboratory_name_ar}"
        holder.lab_address.text =  "${lab.address_ar}"
        holder.ratingBar.rating=lab.rating.toFloat()
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
        val booking_btn: TextView =view.booking_btn




    }
}