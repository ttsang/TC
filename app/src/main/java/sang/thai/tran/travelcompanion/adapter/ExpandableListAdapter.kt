package sang.thai.tran.travelcompanion.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView

import sang.thai.tran.travelcompanion.R

class ExpandableListAdapter(private val _context: Activity, private val _listDataHeader: Array<String> // header titles
                            ,
        // child data in format of header title, child title
                            private val _listDataChild: Map<String, Array<String>>, expandableListView: ExpandableListView) : BaseExpandableListAdapter() {

    init {
        expandableListView.dividerHeight = 10
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this._listDataChild[this._listDataHeader[groupPosition]]!![childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convert = convertView

        val childText = getChild(groupPosition, childPosition) as String

        if (convert == null) {
            val inflater = this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convert = inflater.inflate(R.layout.list_item, null)
        }

        val txtListChild = convert!!
                .findViewById<View>(R.id.lblListItem) as TextView

        txtListChild.text = childText
        //        mExpandableListView.setDividerHeight(5);
        return convert
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this._listDataChild[this._listDataHeader[groupPosition]]!!
                .size
    }

    override fun getGroup(groupPosition: Int): Any {
        return this._listDataHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return this._listDataHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var convert = convertView
        val headerTitle = getGroup(groupPosition) as String
        if (convert == null) {
            val inflater = this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convert = inflater.inflate(R.layout.list_group, null)
        }

        val lblListHeader = convert!!
                .findViewById<View>(R.id.lblListHeader) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = headerTitle
        //        mExpandableListView.setDividerHeight(35);
        return convert
    }

    override fun onGroupCollapsed(groupPosition: Int) {
        super.onGroupCollapsed(groupPosition)
        //        mExpandableListView.setDividerHeight(150);
        Log.d("Sang", "onGroupCollapsed groupPosition: $groupPosition")
    }

    override fun onGroupExpanded(groupPosition: Int) {
        super.onGroupExpanded(groupPosition)
        //        mExpandableListView.setDividerHeight(5);
        Log.d("Sang", "onGroupExpanded groupPosition: $groupPosition")
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
