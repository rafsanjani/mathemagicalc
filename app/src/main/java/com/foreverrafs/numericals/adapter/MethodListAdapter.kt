package com.foreverrafs.numericals.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes

class MethodListAdapter(context: Context, @LayoutRes resource: Int, objects: Array<String>) : ArrayAdapter<String>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.argb(30, 238, 232, 232))
        } else {
            view.setBackgroundColor(Color.argb(30, 120, 200, 250))
        }
        return view
    }
}