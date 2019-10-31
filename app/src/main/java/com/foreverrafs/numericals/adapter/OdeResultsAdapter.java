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

import butterknife.BindView;
import butterknife.ButterKnife;

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

    static class RootResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.background)
        ViewGroup bg;

        @BindView(R.id.n)
        TextView tvIteration;

        @BindView(R.id.solX)
        TextView tvSolX;

        @BindView(R.id.solY)
        TextView tvSolY;

        RootResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
