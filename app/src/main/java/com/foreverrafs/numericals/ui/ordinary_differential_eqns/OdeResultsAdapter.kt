package com.foreverrafs.numericals.ui.ordinary_differential_eqns

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.core.OdeResult
import com.foreverrafs.numericals.R
import java.util.Locale

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
        private val container: ViewGroup = itemView.findViewById(R.id.container)
        private val n: TextView = itemView.findViewById(R.id.n)
        private val solX: TextView = itemView.findViewById(R.id.solX)
        private val solY: TextView = itemView.findViewById(R.id.solY)
        fun bind(position: Int) {
            if (position % 2 == 0) {
                container.setBackgroundColor(Color.argb(30, 238, 232, 232))
            } else {
                container.setBackgroundColor(Color.argb(30, 120, 200, 250))
            }

            val (x, y) = results[position]
            n.text = position.toString()
            solX.text = String.format(Locale.US, "%.3f", x)
            solY.text = String.format(Locale.US, "%.6f", y)
        }
    }

}