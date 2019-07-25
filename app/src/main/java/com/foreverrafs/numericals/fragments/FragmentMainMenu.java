package com.foreverrafs.numericals.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.fragments.conversions.FragmentConversionsMenu;
import com.foreverrafs.numericals.fragments.ordinary_differential_eqns.FragmentOdeMenu;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentSystemOfEquationsMenu;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements View.OnClickListener {


    private View rootView;
    private AppCompatActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();
        //(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = ((AppCompatActivity) getActivity());
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initControls() {
        //click listeners for all the buttons from the main menu
        rootView.findViewById(R.id.btn_number_conversion).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn).setOnClickListener(this);
        rootView.findViewById(R.id.btn_about).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ord_diff_eqn).setOnClickListener(this);
        rootView.findViewById(R.id.btn_algorithms).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_number_conversion:
                fragment = new FragmentConversionsMenu();
                break;

            case R.id.btn_sys_of_eqn:
                fragment = new FragmentSystemOfEquationsMenu();
                break;
            case R.id.btn_ord_diff_eqn:
                fragment = new FragmentOdeMenu();
                break;
            case R.id.btn_algorithms:
                startActivity(new Intent(getContext(), ShowAlgorithm.class));
                mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return;
            case R.id.btn_about:

                break;
        }
        if (fragment != null)
            Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer);

    }
}
