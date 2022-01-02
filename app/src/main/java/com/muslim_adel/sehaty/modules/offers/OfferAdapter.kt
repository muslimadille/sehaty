package com.sehakhanah.patientapp.modules.offers

import android.content.Context
import android.content.Intent
import android.graphics.Paint
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
import com.sehakhanah.patientapp.data.remote.objects.Offer
import com.sehakhanah.patientapp.utiles.ComplexPreferences
import kotlinx.android.synthetic.main.offer_item.view.*


class OfferAdapter(
    private val mContext: Context,
    private val list: MutableList<Offer>
) : RecyclerView.Adapter<OfferAdapter.ViewHolder>() {
    var preferences: ComplexPreferences? = null
    init {
        preferences = ComplexPreferences.getComplexPreferences(mContext, Q.PREF_FILE, Q.MODE_PRIVATE)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val convertView = inflater.inflate(R.layout.offer_item, parent, false)
            return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = list[position]
        var docGendar=""
        if(offer.doctor.gender_id==1){
            docGendar=mContext.getString(R.string.doctor)
        }else{
            docGendar=mContext.getString(R.string.doctorah)
        }
        if (preferences!!.getString("language","")=="Arabic"){
            holder.descound_txt!!.text=" خصم ${offer.discount.toString()}"+"%"
            holder.doc_data_txt!!.text=docGendar+" "+offer.doctor.firstName_ar +" "+ offer.doctor.lastName_ar +"-"+offer.doctor.address_ar
            holder.offer_title_txt!!.text=offer.title_ar
            holder.offer_subtitle_txt!!.text=offer.device_name_ar
            holder.offer_ratingBar!!.rating=offer.rating.toFloat()
            holder.initial_cost!!.paintFlags = holder.initial_cost!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.initial_cost!!.text=offer.price.toString()+" "+Q.CURNCY_NAME_AR
            holder.final_cost!!.text=((offer.price*((100-offer.discount))/100)).toString()+Q.CURNCY_NAME_AR
        }else{
            holder.descound_txt!!.text=" ${mContext.getString(R.string.discount)} ${offer.discount.toString()}"+"%"
            holder.doc_data_txt!!.text=docGendar+" "+offer.doctor.firstName_en +" "+ offer.doctor.lastName_en +"-"+offer.doctor.address_en
            holder.offer_title_txt!!.text=offer.title_en
            holder.offer_subtitle_txt!!.text=offer.device_name_en
            holder.offer_ratingBar!!.rating=offer.rating.toFloat()
            holder.initial_cost!!.paintFlags = holder.initial_cost!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.initial_cost!!.text=offer.price.toString()+" "+Q.CURNCY_NAME_EN
            holder.final_cost!!.text=((offer.price*((100-offer.discount))/100)).toString()+Q.CURNCY_NAME_EN

        }


        var offerImage=""
        if(offer.images.isNotEmpty()){
            offerImage=offer.images[0].featured
        }else{
            offerImage=""
        }
        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(offerImage)
            .centerCrop()
            .into(holder.offer_img!!)
        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(offer.doctor.featured)
            .centerCrop()
            .into(holder.doc_img!!)
        holder.booking_btb!!.setOnClickListener {
            val intent=Intent(mContext,OfferDetailsActivity::class.java)
            intent.putExtra("offer_id",offer.id)
            mContext.startActivity(intent)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val offer_img: ImageView? =view.offer_img
        val doc_img: ImageView? =view.doc_img
        val descound_txt: TextView? =view.descound_txt
        val doc_data_txt: TextView? =view.doc_data_txt
        val offer_title_txt: TextView? =view.offer_title_txt
        val offer_subtitle_txt: TextView? =view.offer_subtitle_txt

        val offer_ratingBar: RatingBar? =view.offer_ratingBar
        val final_cost: TextView? =view.final_cost
        val initial_cost: TextView? =view.initial_cost
        val booking_btb:CardView?=view.offer_lay


    }


}