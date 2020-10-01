package com.muslim_adel.sehaty.modules.offers

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Offer
import com.muslim_adel.sehaty.data.remote.objects.OffersCategory
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.offer_category_first_item.view.*
import kotlinx.android.synthetic.main.offer_item.view.*
import kotlinx.android.synthetic.main.offers_second_item.view.*



class OfferAdapter(
    private val mContext: Context,
    private val list: MutableList<Offer>
) : RecyclerView.Adapter<OfferAdapter.ViewHolder>() {


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
        holder.descound_txt!!.text=" خصم ${offer.discount.toString()}"+"%"
        holder.doc_data_txt!!.text=docGendar+" "+offer.doctor.firstName_ar +" "+ offer.doctor.lastName_ar +"-"+offer.doctor.streetName_ar
        holder.offer_title_txt!!.text=offer.title_ar
        holder.offer_subtitle_txt!!.text=offer.device_name_ar
        holder.offer_ratingBar!!.rating=offer.rating.toFloat()
        holder.initial_cost!!.paintFlags = holder.initial_cost!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.initial_cost!!.text=offer.price.toString()+" "+mContext.getString(R.string.derham)
        holder.final_cost!!.text=((offer.price*((100-offer.discount))/100)).toString()+mContext.getString(R.string.derham)

        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(offer.images[0].featured)
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
        val booking_btb:TextView?=view.booking_btb


    }


}