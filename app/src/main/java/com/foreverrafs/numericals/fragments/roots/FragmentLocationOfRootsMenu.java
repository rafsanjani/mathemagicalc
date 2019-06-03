package com.foreverrafs.numericals.fragments.roots;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
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

public class FragmentLocationOfRootsMenu extends Fragment implements View.OnClickListener {


    static TextView header;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_location_of_roots_menu, container, false);
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
        Utilities.setTypeFace(header, getContext(), Utilities.TypeFaceName.raleway_bold);
        header.setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.btn_loc_of_roots_bisection).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots_false_position).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots_newton_raphson).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots_secante).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_loc_of_roots_bisection:
                fragment = new FragmentBisection();
                break;
            case R.id.btn_loc_of_roots_false_position:
                fragment = new FragmentFalsePosition();
                break;

            case R.id.btn_loc_of_roots_newton_raphson:
                fragment = new FragmentNewtonRaphson();
                break;

            case R.id.btn_loc_of_roots_secante:
                fragment = new FragmentSecante();
                break;
        }

        Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
    }
}
