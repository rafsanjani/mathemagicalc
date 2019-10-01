package com.foreverrafs.numericals.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.core.OdeResult;
import com.foreverrafs.numericals.R;

import java.util.List;
import java.util.Locale;

public class OdeResultsAdapter extends RecyclerView.Adapter<OdeResultsAdapter.RootResultViewHolder> {

    private List<OdeResult> results;


    public OdeResultsAdapter(List<OdeResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public RootResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_euler_results, null);
        return new RootResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OdeResultsAdapter.RootResultViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.bg.setBackgroundColor(Color.argb(30, 238, 232, 232));
        } else {
            holder.bg.setBackgroundColor(Color.argb(30, 120, 200, 250));
        }

        OdeResult resultAtPosition = results.get(position);

        holder.tvIteration.setText(String.valueOf(position));
        holder.tvSolX.setText(String.format(Locale.US, "%.3f", resultAtPosition.getX()));
        holder.tvSolY.setText(String.format(Locale.US, "%.6f", resultAtPosition.getY()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class RootResultViewHolder extends RecyclerView.ViewHolder {

        ViewGroup bg;
        TextView tvIteration, tvSolX, tvSolY, tvSolH;

        RootResultViewHolder(View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.background);
            tvIteration = itemView.findViewById(R.id.n);
            tvSolX = itemView.findViewById(R.id.solX);
            tvSolY = itemView.findViewById(R.id.solY);
        }
    }
}
