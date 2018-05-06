package com.example.azizrafsanjani.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BisectionAdapter extends ArrayAdapter<Double> {
    private Context mCtx;


    public BisectionAdapter(@NonNull Context context, int resource, @NonNull List<Double> objects) {
        super(context, resource, objects);
        this.mCtx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.argb(30, 238, 232, 232));
        } else {
            view.setBackgroundColor(Color.argb(30, 120, 200, 250));
        }

        TextView myView = (TextView) view;
        myView.setText("Iteration " + (position + 1) + ": " + myView.getText());

        return view;
    }
}
