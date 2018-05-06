package com.example.azizrafsanjani.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.model.BisectionResult;

import java.util.List;
import java.util.Locale;

public class BisectionRecyclerAdapter extends RecyclerView.Adapter<BisectionRecyclerAdapter.BisectionResultViewHolder> {

    private List<BisectionResult> bisectionResults;
    private Context mCtx;


    public BisectionRecyclerAdapter(List<BisectionResult> bisectionResults, Context mCtx) {
        this.bisectionResults = bisectionResults;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public BisectionResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.bisection_results_view, null);
        return new BisectionResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BisectionResultViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.bg.setBackgroundColor(Color.argb(30, 238, 232, 232));
        } else {
            holder.bg.setBackgroundColor(Color.argb(30, 120, 200, 250));
        }

        BisectionResult result = bisectionResults.get(position);

        holder.etDifference.setText(String.format(Locale.US, "[%f]", result.getTolerance()));
        holder.etIteration.setText(String.format(Locale.US, "[%d] ", position + 1));
        holder.etX1.setText(String.format(Locale.US, "[%.4f]", result.getX1()));
        holder.etX2.setText(String.format(Locale.US, "[%.4f]", result.getX2()));
        holder.etX3.setText(String.format(Locale.US, "[%.6f]", result.getX3()));
    }

    @Override
    public int getItemCount() {
        return bisectionResults.size();
    }

    class BisectionResultViewHolder extends RecyclerView.ViewHolder {

        ViewGroup bg;
        TextView etX1, etX2, etX3, etDifference, etIteration;

        public BisectionResultViewHolder(View itemView) {
            super(itemView);
            etDifference = itemView.findViewById(R.id.difference);
            etX1 = itemView.findViewById(R.id.x1);
            etX2 = itemView.findViewById(R.id.x2);
            etX3 = itemView.findViewById(R.id.x3);
            bg = itemView.findViewById(R.id.background);
            etIteration = itemView.findViewById(R.id.iteration);
        }
    }
}
