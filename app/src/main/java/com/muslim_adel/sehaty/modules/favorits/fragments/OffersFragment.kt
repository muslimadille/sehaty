package com.muslim_adel.sehaty.modules.favorits.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslim_adel.sehaty.R
import com.muslim_adel.sehaty.modules.favorits.FavoritsActivity


class OffersFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }
    var mContext: FavoritsActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FavoritsActivity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity as FavoritsActivity
    }

}