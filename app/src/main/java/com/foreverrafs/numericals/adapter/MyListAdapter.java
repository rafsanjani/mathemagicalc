package com.foreverrafs.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MyListAdapter extends ArrayAdapter<String> {
    private Context mCtx;


    public MyListAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
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

        return view;
    }
}
