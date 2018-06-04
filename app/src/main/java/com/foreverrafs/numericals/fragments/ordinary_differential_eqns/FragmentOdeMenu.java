package com.foreverrafs.numericals.fragments.ordinary_differential_eqns;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentOdeMenu extends Fragment implements View.OnClickListener {


    private View rootView;
    static TextView header;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ode_menu, container, false);
        initControls();
        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), "Ordinary Differential Equations");

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initControls() {
        header = rootView.findViewById(R.id.Header);
        Utilities.setLobsterTypeface(header, getContext());
        header.setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.btn_ode_euler).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_loc_of_roots_bisection:
                break;
        }

        Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
    }
}
