package com.example.azizrafsanjani.numericals.fragments;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentConversionsMenu;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentLocationOfRootsMenu;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentSystemOfEquationsMenu;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements  View.OnClickListener {


    private View rootView;
    static TextView header;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();
        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initControls() {
        header = rootView.findViewById(R.id.Header);
        header.setVisibility(View.VISIBLE);

        //click listeners for all the buttons from the main menu
        rootView.findViewById(R.id.btn_number_conversion).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn).setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lobster-Regular.ttf");
        header.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btn_number_conversion:
                fragment = new FragmentConversionsMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;

            case R.id.btn_loc_of_roots:
                fragment = new FragmentLocationOfRootsMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;
            case R.id.btn_sys_of_eqn:
                fragment = new FragmentSystemOfEquationsMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;
        }

    }
}
