package com.sehakhanah.patientapp.modules.offers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.modules.base.GlideObject
import com.sehakhanah.patientapp.modules.home.MainActivity

class OffersPagerAdapter(mContext:Context,list:MutableList<String>) :PagerAdapter(){
    private val mContext=mContext
    private var imagesList=list
    override fun getCount(): Int {
        return imagesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageView:ImageView= ImageView(mContext)
/*
        GlideObject.GlideProfilePic(mContext,imagesList[position],imageView)
*/
        Glide.with(mContext).applyDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.color.gray_light)
                .error(R.color.gray_light))
            .load(imagesList[position])
            .centerCrop()
            .into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }
}