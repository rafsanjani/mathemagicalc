package com.foreverrafs.numericals.ui.location_of_roots

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.core.LocationOfRootResult
import com.foreverrafs.core.LocationOfRootType
import com.foreverrafs.numericals.R
import timber.log.Timber
import java.util.Locale

class RootResultsAdapter(private val locationOfRootResult: List<LocationOfRootResult>, private val rootType: LocationOfRootType) : RecyclerView.Adapter<RootResultsAdapter.RootResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootResultViewHolder {
        var view: View? = null
        val context = parent.context
        when (rootType) {
            LocationOfRootType.BISECTION -> view = LayoutInflater.from(context).inflate(R.layout.item_bisection_results, parent, false)
            LocationOfRootType.NEWTON_RAPHSON -> view = LayoutInflater.from(context).inflate(R.layout.item_newton_raphson_results, parent, false)
            LocationOfRootType.SECANTE -> view = LayoutInflater.from(context).inflate(R.layout.item_secante_results, parent, false)
            LocationOfRootType.FALSE_POSITION -> view = LayoutInflater.from(context).inflate(R.layout.item_falseposition_results, parent, false)
            else -> Timber.i("Equation type not found")
        }
        return RootResultViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: RootResultViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return locationOfRootResult.size
    }

    inner class RootResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var bg: ViewGroup = itemView.findViewById(R.id.background)
        val difference: TextView = itemView.findViewById(R.id.difference)
        val iteration: TextView = itemView.findViewById(R.id.iteration)
        val x1: TextView = itemView.findViewById(R.id.x1)
        private val fx1: TextView = itemView.findViewById(R.id.fx1)
        private val derx1: TextView = itemView.findViewById(R.id.derx1)
        private val root: TextView = itemView.findViewById(R.id.root)
        val x2: TextView = itemView.findViewById(R.id.x2)
        val x3: TextView = itemView.findViewById(R.id.x3)

        fun bind(position: Int) {
            if (position % 2 == 0) {
                bg.setBackgroundColor(Color.argb(30, 238, 232, 232))
            } else {
                bg.setBackgroundColor(Color.argb(30, 120, 200, 250))
            }
            val result = locationOfRootResult[position]
            when (rootType) {
                LocationOfRootType.BISECTION, LocationOfRootType.SECANTE, LocationOfRootType.FALSE_POSITION -> {
                    difference.text = String.format(Locale.US, "[%4.4f]", result.tolerance)
                    iteration.text = String.format(Locale.US, "[%d] ", position + 1)
                    x1.text = String.format(Locale.US, "[%4.4f]", result.x1)
                    x2.text = String.format(Locale.US, "[%4.4f]", result.x2)
                    x3.text = String.format(Locale.US, "[%4.6f]", result.x3)
                }

                LocationOfRootType.NEWTON_RAPHSON -> {
                    iteration.text = String.format(Locale.US, "[%d] ", position + 1)
                    fx1.text = String.format(Locale.US, "[%4.4f]", result.fx1)
                    derx1.text = String.format(Locale.US, "[%4.4f]", result.derX1)
                    root.text = String.format(Locale.US, "[%4.4f]", result.x1)
                }
            }
        }
    }

}