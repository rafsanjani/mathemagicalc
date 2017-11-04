package com.example.azizrafsanjani.numericals.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Utilities;

public class FragmentDecToBinInt extends Fragment implements Button.OnClickListener {

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_dec_to_bin_int, container, false);
        MainActivity.setToolBarInfo("Decimal Calculator","Convert decimals to binary");


        Button button = (Button)rootView.findViewById(R.id.buttonBack);

        button.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonBack:
                Utilities.replaceFragment(this, new FragmentMainMenu(),getFragmentManager(),R.id.fragmentContainer);
                break;
        }
    }
}
