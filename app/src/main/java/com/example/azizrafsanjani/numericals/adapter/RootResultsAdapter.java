package com.example.azizrafsanjani.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.model.LocationOfRootResult;
import com.example.azizrafsanjani.numericals.model.LocationOfRootType;
import com.example.azizrafsanjani.numericals.utils.Utilities;

import java.util.List;
import java.util.Locale;

public class RootResultsAdapter extends RecyclerView.Adapter<RootResultsAdapter.RootResultViewHOlder> {

    private List<LocationOfRootResult> locationOfRootResult;
    private Context mCtx;
    private LocationOfRootType rootType;


    public RootResultsAdapter(List<LocationOfRootResult> bisectionResults, Context mCtx, LocationOfRootType rootType) {
        this.locationOfRootResult = bisectionResults;
        this.mCtx = mCtx;
        this.rootType = rootType;
    }

    @NonNull
    @Override
    public RootResultViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        //inflate a view based on the location of root type
        switch (rootType) {
            case BISECTION:
                view = LayoutInflater.from(mCtx).inflate(R.layout.bisection_results_view, null);
                break;

            default:
                Log.i(Utilities.Log, "Equation type not found");
                break;
        }

        return new RootResultViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RootResultViewHOlder holder, int position) {
        if (position % 2 == 0) {
            holder.bg.setBackgroundColor(Color.argb(30, 238, 232, 232));
        } else {
            holder.bg.setBackgroundColor(Color.argb(30, 120, 200, 250));
        }

        LocationOfRootResult result = locationOfRootResult.get(position);

        switch (rootType) {
            case BISECTION:
                holder.etDifference.setText(String.format(Locale.US, "[%f]", result.getTolerance()));
                holder.etIteration.setText(String.format(Locale.US, "[%d] ", position + 1));
                holder.etX1.setText(String.format(Locale.US, "[%.4f]", result.getX1()));
                holder.etX2.setText(String.format(Locale.US, "[%.4f]", result.getX2()));
                holder.etX3.setText(String.format(Locale.US, "[%.6f]", result.getX3()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return locationOfRootResult.size();
    }

    class RootResultViewHOlder extends RecyclerView.ViewHolder {

        ViewGroup bg;
        TextView etX1, etX2, etX3, etDifference, etIteration;

        public RootResultViewHOlder(View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.background);

            switch (rootType) {
                case BISECTION:
                    etDifference = itemView.findViewById(R.id.difference);
                    etX1 = itemView.findViewById(R.id.x1);
                    etX2 = itemView.findViewById(R.id.x2);
                    etX3 = itemView.findViewById(R.id.x3);
                    etIteration = itemView.findViewById(R.id.iteration);
                    break;
            }
        }
    }
}
