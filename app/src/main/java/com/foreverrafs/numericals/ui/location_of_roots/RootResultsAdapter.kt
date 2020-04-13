package com.foreverrafs.numericals.ui.location_of_roots

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreverrafs.core.LocationOfRootResult
import com.foreverrafs.core.LocationOfRootType
import com.foreverrafs.numericals.R
import kotlinx.android.synthetic.main.fragment_loc_of_roots_newton_results.view.fx1
import kotlinx.android.synthetic.main.item_bisection_results.view.*
import kotlinx.android.synthetic.main.item_bisection_results.view.iteration
import kotlinx.android.synthetic.main.item_newton_raphson_results.view.*
import java.util.*

class RootResultsAdapter(private val locationOfRootResult: List<LocationOfRootResult>, private val rootType: LocationOfRootType) : RecyclerView.Adapter<RootResultsAdapter.RootResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootResultViewHolder {
        var view: View? = null
        val context = parent.context
        when (rootType) {
            LocationOfRootType.BISECTION -> view = LayoutInflater.from(context).inflate(R.layout.item_bisection_results, null)
            LocationOfRootType.NEWTON_RAPHSON -> view = LayoutInflater.from(context).inflate(R.layout.item_newton_raphson_results, null)
            LocationOfRootType.SECANTE -> view = LayoutInflater.from(context).inflate(R.layout.item_secante_results, null)
            LocationOfRootType.FALSE_POSITION -> view = LayoutInflater.from(context).inflate(R.layout.item_falseposition_results, null)
            else -> Log.i(TAG, "Equation type not found")
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
        var bg: ViewGroup = itemView.findViewById(R.id.background)

        fun bind(position: Int) {
            if (position % 2 == 0) {
                bg.setBackgroundColor(Color.argb(30, 238, 232, 232))
            } else {
                bg.setBackgroundColor(Color.argb(30, 120, 200, 250))
            }
            val result = locationOfRootResult[position]
            when (rootType) {
                LocationOfRootType.BISECTION, LocationOfRootType.SECANTE, LocationOfRootType.FALSE_POSITION -> {
                    itemView.difference.text = String.format(Locale.US, "[%4.4f]", result.tolerance)
                    itemView.iteration.text = String.format(Locale.US, "[%d] ", position + 1)
                    itemView.x1.text = String.format(Locale.US, "[%4.4f]", result.x1)
                    itemView.x2.text = String.format(Locale.US, "[%4.4f]", result.x2)
                    itemView.x3.text = String.format(Locale.US, "[%4.6f]", result.x3)
                }
                LocationOfRootType.NEWTON_RAPHSON -> {
                    itemView.iteration.text = String.format(Locale.US, "[%d] ", position + 1)
                    itemView.fx1.text = String.format(Locale.US, "[%4.4f]", result.fx1)
                    itemView.derx1.text = String.format(Locale.US, "[%4.4f]", result.derX1)
                    itemView.root.text = String.format(Locale.US, "[%4.4f]", result.x1)

                }
            }
        }
    }

    companion object {
        private const val TAG = "RootResultsAdapter"
    }

}