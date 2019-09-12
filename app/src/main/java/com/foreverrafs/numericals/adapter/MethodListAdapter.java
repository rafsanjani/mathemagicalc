package com.foreverrafs.numericals.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MethodListAdapter extends ArrayAdapter<String> {

    public MethodListAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
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
