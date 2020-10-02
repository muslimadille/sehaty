package com.muslim_adel.sehaty.modules.offers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.data.remote.objects.OffersCategory
import com.muslim_adel.sehaty.modules.base.GlideObject
import com.muslim_adel.sehaty.modules.home.MainActivity
import kotlinx.android.synthetic.main.offer_category_first_item.view.*
import kotlinx.android.synthetic.main.offers_second_item.view.*


class CategoriesAdapter(
    private val mContext: MainActivity,
    private val list: MutableList<OffersCategory>
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    var viewType=-1
    var lastPosition=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (viewType == 1) {
            val convertView = inflater.inflate(R.layout.offer_category_first_item, parent, false)
            return ViewHolder(convertView)
        } else {
            val convertView = inflater.inflate(R.layout.offers_second_item, parent, false)
            return ViewHolder(convertView)        }


    }

    override fun getItemCount(): Int {
        if(list.size!=0){
            if((list.size)%2>0){
                return ((list.size-1)/2)+1
            }else{
                return (list.size/2)+1
            }
        }
        else return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        if (viewType==1){
            lastPosition=position
            holder.category_img!!.setOnClickListener {
                mContext.intent=Intent(mContext,CategoryOffersListActivity::class.java)
                mContext. intent.putExtra("title_ar",category.name_ar)
                mContext.intent.putExtra("title_en",category.name_en)
                mContext.intent.putExtra("category_id",category.id)
                mContext.startActivity(mContext.intent)
            }
            /*GlideObject.GlideProfilePic(mContext,category.efeaturedl,holder.category_img!!)*/
            Glide.with(mContext).applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.person_ic)
                    .error(R.drawable.person_ic))
                .load(category.efeaturedl)
                .centerCrop()
                .into(holder.category_img!!)
            holder.category_title_txt?.text=category.name_ar
            }
        else if (viewType==2){
            lastPosition += 1
            if (lastPosition<=list.size-1){
                holder.category1_img!!.setOnClickListener {
                    mContext.intent=Intent(mContext,CategoryOffersListActivity::class.java)
                    mContext. intent.putExtra("title_ar",list[lastPosition].name_ar)
                    mContext.intent.putExtra("title_en",list[lastPosition].name_en)
                    mContext.intent.putExtra("category_id",list[lastPosition].id)
                    mContext.startActivity(mContext.intent)
                }
                /*GlideObject.GlideProfilePic(mContext,list[lastPosition+1].efeaturedl,holder.category1_img!!)*/
                Glide.with(mContext).applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.person_ic)
                        .error(R.drawable.person_ic))
                    .load(list[lastPosition+1].efeaturedl)
                    .centerCrop()
                    .into(holder.category1_img!!)
                holder.category1_title_txt?.text=list[lastPosition].name_ar
                if(lastPosition<=list.size-3){
                    holder.category2_img!!.setOnClickListener {
                        mContext.intent=Intent(mContext,CategoryOffersListActivity::class.java)
                        mContext.intent.putExtra("category_id",list[lastPosition+1].id)
                        mContext. intent.putExtra("title_ar",list[lastPosition+1].name_ar)
                        mContext.intent.putExtra("title_en",list[lastPosition+1].name_en)
                        mContext.startActivity(mContext.intent)
                    }
                    /*GlideObject.GlideProfilePic(mContext,list[lastPosition+2].efeaturedl,holder.category2_img!!)*/
                    Glide.with(mContext).applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.person_ic)
                            .error(R.drawable.person_ic))
                        .load(list[lastPosition+2].efeaturedl)
                        .centerCrop()
                        .into(holder.category2_img!!)
                    holder.category2_title_txt?.text=list[lastPosition+1].name_ar
                    lastPosition += 1

                }

            }



        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val category_img: ImageView? =view.category_img
        val category_title_txt: TextView? =view.category_title_txt

        val category1_img: ImageView? =view.category1_img
        val category1_title_txt: TextView? =view.category1_title_txt

        val category2_img: ImageView? =view.category2_img
        val category2_title_txt: TextView? =view.category2_title_txt
    }

    override fun getItemViewType(position: Int): Int {
         if (position == 0) {
             viewType=1
             return 1
         };
        else {
             viewType=2
             return 2
         }
    }
}