package com.foreverrafs.numericals.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.About;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.fragments.conversions.FragmentConversionsMenu;
import com.foreverrafs.numericals.fragments.ordinary_differential_eqns.FragmentOdeMenu;
import com.foreverrafs.numericals.fragments.roots.FragmentLocationOfRootsMenu;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentSystemOfEquationsMenu;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements View.OnClickListener {


    TextView header;
    private View rootView;
    private AppCompatActivity mActivity;


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
        mActivity = ((AppCompatActivity) getActivity());
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initControls() {
        header = rootView.findViewById(R.id.Header);
        header.setVisibility(View.VISIBLE);

        //click listeners for all the buttons from the main menu
        rootView.findViewById(R.id.btn_number_conversion).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn).setOnClickListener(this);
        rootView.findViewById(R.id.btn_about).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ord_diff_eqn).setOnClickListener(this);
        rootView.findViewById(R.id.btn_algorithms).setOnClickListener(this);


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
            case R.id.btn_ord_diff_eqn:
                fragment = new FragmentOdeMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;
            case R.id.btn_algorithms:
                // Bundle bundle = new Bundle();
                //bundle.putString("algorithm_name","index");
                startActivity(new Intent(getContext(), ShowAlgorithm.class));
                mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.btn_about:
                fragment = new About();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                /*
                startActivity(new Intent(getContext(), About.class));
                mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                break;
        }

    }
}
