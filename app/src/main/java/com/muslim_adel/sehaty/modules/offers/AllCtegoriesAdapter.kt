package com.sehakhanah.patientapp.modules.offers

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.sehakhanah.patientapp.R
import com.sehakhanah.patientapp.data.remote.objects.OffersCategory
import com.sehakhanah.patientapp.data.remote.objects.OffersSubGategory
import kotlinx.android.synthetic.main.activity_change_language.*

import java.util.HashMap

class AllCtegoriesAdapter internal constructor(private val context: AllCtegoriesActivity, private val titleList: List<OffersCategory>, private val dataList: HashMap<String, List<OffersSubGategory>>) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition].name_ar]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as OffersSubGategory
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.main_categoriy_item, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.main_cat_txt)
        if (context.preferences!!.getString("language","")=="Arabic"){
            expandedListTextView.text = expandedListText.name_ar
        }else{
            expandedListTextView.text = expandedListText.name_en
        }

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition].name_ar]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as OffersCategory
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.sub_category_item, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.continent)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        if (context.preferences!!.getString("language","")=="Arabic"){
            listTitleTextView.text = listTitle.name_ar
        }else{
            listTitleTextView.text = listTitle.name_en
        }
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
