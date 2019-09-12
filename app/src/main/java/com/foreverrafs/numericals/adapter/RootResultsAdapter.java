package com.foreverrafs.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.LocationOfRootType;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;
import java.util.Locale;

public class RootResultsAdapter extends RecyclerView.Adapter<RootResultsAdapter.RootResultViewHolder> {

    private List<LocationOfRootResult> locationOfRootResult;
    private LocationOfRootType rootType;


    public RootResultsAdapter(List<LocationOfRootResult> locationOfRootResult, LocationOfRootType rootType) {
        this.locationOfRootResult = locationOfRootResult;
        this.rootType = rootType;
    }

    @NonNull
    @Override
    public RootResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();

        //inflate a view based on the location of root type
        switch (rootType) {
            case BISECTION:
                view = LayoutInflater.from(context).inflate(R.layout.item_bisection_results, null);
                break;
            case NEWTON_RAPHSON:
                view = LayoutInflater.from(context).inflate(R.layout.item_newton_raphson_results, null);
                break;

            case SECANTE:
                view = LayoutInflater.from(context).inflate(R.layout.item_secante_results, null);
                break;
            case FALSE_POSITION:
                view = LayoutInflater.from(context).inflate(R.layout.item_falseposition_results, null);
                break;

            default:
                Log.i(Utilities.LOG_TAG, "Equation type not found");
                break;
        }

        return new RootResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RootResultViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.bg.setBackgroundColor(Color.argb(30, 238, 232, 232));
        } else {
            holder.bg.setBackgroundColor(Color.argb(30, 120, 200, 250));
        }

        LocationOfRootResult result = locationOfRootResult.get(position);

        switch (rootType) {
            case BISECTION:
            case SECANTE:
            case FALSE_POSITION:
                holder.etDifference.setText(String.format(Locale.US, "[%4.4f]", result.getTolerance()));
                holder.etIteration.setText(String.format(Locale.US, "[%d] ", position + 1));
                holder.etX1.setText(String.format(Locale.US, "[%4.4f]", result.getX1()));
                holder.etX2.setText(String.format(Locale.US, "[%4.4f]", result.getX2()));
                holder.etX3.setText(String.format(Locale.US, "[%4.6f]", result.getX3()));
                break;
            case NEWTON_RAPHSON:
                holder.etIteration.setText(String.format(Locale.US, "[%d] ", position + 1));
                //holder.etX1.setText(String.format(Locale.US, "[%.4f]", result.getX1()));
                holder.etfX1.setText(String.format(Locale.US, "[%4.4f]", result.getFx1()));
                holder.etDerX1.setText(String.format(Locale.US, "[%4.4f]", result.getDerX1()));
                holder.etRoot.setText(String.format(Locale.US, "[%4.4f]", result.getX1()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return locationOfRootResult.size();
    }

    class RootResultViewHolder extends RecyclerView.ViewHolder {

        ViewGroup bg;
        TextView etX1, etX2, etX3, etDifference, etIteration, etDerX1, etfX1, etRoot;

        public RootResultViewHolder(View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.background);

            switch (rootType) {
                case BISECTION:
                case SECANTE:
                case FALSE_POSITION:
                    etDifference = itemView.findViewById(R.id.difference);
                    etX1 = itemView.findViewById(R.id.x1);
                    etX2 = itemView.findViewById(R.id.x2);
                    etX3 = itemView.findViewById(R.id.x3);
                    etIteration = itemView.findViewById(R.id.iteration);
                    break;
                case NEWTON_RAPHSON:
                    etRoot = itemView.findViewById(R.id.root);
                    etfX1 = itemView.findViewById(R.id.fx1);
                    etDerX1 = itemView.findViewById(R.id.derx1);
                    etIteration = itemView.findViewById(R.id.iteration);
                    break;
            }
        }
    }
}
