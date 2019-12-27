package com.foreverrafs.numericals.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.core.OdeResult
import com.foreverrafs.numericals.R
import kotlinx.android.synthetic.main.item_euler_results.view.*
import java.util.*

class OdeResultsAdapter(private val results: List<OdeResult>) : RecyclerView.Adapter<OdeResultsAdapter.RootResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_euler_results, null)
        return RootResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RootResultViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class RootResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            if (position % 2 == 0) {
                itemView.container.setBackgroundColor(Color.argb(30, 238, 232, 232))
            } else {
                itemView.container.setBackgroundColor(Color.argb(30, 120, 200, 250))
            }

            val (x, y) = results[position]
            itemView.n.text = position.toString()
            itemView.solX.text = String.format(Locale.US, "%.3f", x)
            itemView.solY.text = String.format(Locale.US, "%.6f", y)
        }
    }

}