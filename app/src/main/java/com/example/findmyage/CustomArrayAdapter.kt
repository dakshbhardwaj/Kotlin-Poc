package com.example.findmyage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.findmyage.models.ListData
import com.makeramen.roundedimageview.RoundedImageView

class CustomArrayAdapter(private val activity: Activity, private val list:List<ListData>) :
    ArrayAdapter<ListData>(activity, R.layout.list_item) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val context = activity.layoutInflater
        val rowView = context.inflate(R.layout.list_item, null,true)

        val images = rowView.findViewById<RoundedImageView>(R.id.images)
        val title = rowView.findViewById<TextView>(R.id.title)
        val desc = rowView.findViewById<TextView>(R.id.desc)

        title.text = list[position].title
        desc.text = list[position].desc
        images.setImageResource(list[position].images)

        return rowView
    }
}