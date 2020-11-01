package com.muslim_adel.sehaty.modules.pharmacy

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.Offer
import com.muslim_adel.sehaty.data.remote.objects.PharmacyOffer
import com.muslim_adel.sehaty.modules.home.MainActivity
import com.muslim_adel.sehaty.modules.offers.OfferDetailsActivity
import kotlinx.android.synthetic.main.activity_change_language.*
import kotlinx.android.synthetic.main.offer_item.view.*
import kotlinx.android.synthetic.main.offer_item.view.doc_data_txt
import kotlinx.android.synthetic.main.offer_item.view.doc_img
import kotlinx.android.synthetic.main.offer_item.view.final_cost
import kotlinx.android.synthetic.main.offer_item.view.offer_img
import kotlinx.android.synthetic.main.offer_item.view.offer_title_txt
import kotlinx.android.synthetic.main.pharmacy_offer_item.view.*

class PharmacyOffersAdapter(
    private val mContext: PharmacyOffersActivity,
    private val list: MutableList<PharmacyOffer>
) : RecyclerView.Adapter<PharmacyOffersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.pharmacy_offer_item, parent, false)
        return ViewHolder(convertView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = list[position]
        if (mContext.preferences!!.getString("language","")=="Arabic"){
            holder.doc_data_txt!!.text=mContext.getString(R.string.pharmacy_offers_title)+offer.pharmacy.pharmacy_name_ar
            holder.offer_title_txt!!.text=mContext.getString(R.string.type)+offer.title_ar+"-"+offer.title_ar
            holder.final_cost!!.text=mContext.getString(R.string.price)+" :"+offer.price.toString()+" "+ mContext.getString(R.string.derham)
        }else{
            holder.doc_data_txt!!.text=mContext.getString(R.string.pharmacy_offers_title)+offer.pharmacy.pharmacy_name_en
            holder.offer_title_txt!!.text=mContext.getString(R.string.type)+offer.title_en+"-"+offer.title_en
            holder.final_cost!!.text=mContext.getString(R.string.price)+" :"+offer.price.toString()+" "+ mContext.getString(R.string.derham)
        }


        var lat=offer.pharmacy.lat
        var lng=offer.pharmacy.lng
        val zoom=10
        var lable=offer.pharmacy.pharmacy_name_ar
        val intent= Intent(Intent.ACTION_VIEW)
        holder.location_btn!!.setOnClickListener {
            intent.data= Uri.parse("geo:0,0?z=$zoom&q=$lat,$lng,$lable")
            if(intent.resolveActivity(mContext.packageManager)!=null){
                mContext.startActivity(intent)
            }
        }



        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(offer.pharmacy.featured)
            .fitCenter()
            .into(holder.doc_img!!)
        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.person_ic)
                .error(R.drawable.person_ic))
            .load(offer.featured)
            .centerCrop()
            .into(holder.offer_img!!)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val offer_img: ImageView? =view.offer_img
        val doc_img: ImageView? =view.doc_img
        val doc_data_txt: TextView? =view.doc_data_txt
        val offer_title_txt: TextView? =view.offer_title_txt
        val final_cost: TextView? =view.final_cost
        val location_btn:LinearLayout?=view.location_btn



    }


}