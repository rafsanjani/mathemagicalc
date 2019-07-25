package com.foreverrafs.numericals.fragments.sys_of_equations;


import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentSystemOfEquationsMenu extends Fragment implements View.OnClickListener {


    static TextView header;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_system_of_equations_menu, container, false);
        initControls();
        //(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initControls() {
        header = rootView.findViewById(R.id.text_header);
        header.setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.btn_sys_of_eqn_gaussian3x3complete).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_gaussian3x3partial).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_gaussian4x4complete).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_gaussian4x4partial).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_gaussseidel).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_gaussseidel_SOR).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sys_of_eqn_jacobi).setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lobster-Regular.ttf");
        header.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_sys_of_eqn_gaussian3x3complete:
                fragment = new FragmentGaussianComplete3x3();
                break;

            case R.id.btn_sys_of_eqn_gaussian3x3partial:
                fragment = new FragmentGaussianPartial3x3();
                break;

            case R.id.btn_sys_of_eqn_gaussseidel:
                fragment = new FragmentGaussSeidel();
                break;

            case R.id.btn_sys_of_eqn_gaussseidel_SOR:
                fragment = new FragmentGaussSeidelWithSOR();
                break;

            case R.id.btn_sys_of_eqn_jacobi:
                fragment = new FragmentJacobi();
                break;

            case R.id.btn_sys_of_eqn_gaussian4x4partial:
                fragment = new FragmentGaussianPartial4x4();
                break;
            case R.id.btn_sys_of_eqn_gaussian4x4complete:
                fragment = new FragmentGaussianComplete4x4();
        }

        if (fragment != null)
            Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer);
    }
}
