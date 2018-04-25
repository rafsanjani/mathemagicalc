package com.example.azizrafsanjani.numericals.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussian4x4 extends Fragment implements View.OnClickListener, View.OnKeyListener, TextWatcher {
    
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gaussian_partial3x3, container, false);
        MainActivity.setToolBarInfo("System of Equations","Gaussian Elimination (Partial Pivoting)");

        initControls();
        return rootView;
    }

    private void initControls(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FallingSky.otf");
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);
        tvAnswer.setTypeface(typeface);

        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);


        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonBack:
                Utilities.replaceFragment( new FragmentMainMenu(),getFragmentManager(),R.id.fragmentContainer);
                break;

            case R.id.buttonCalculate:
                onCalculate();
                break;
        }
    }

    private void onCalculate() {


    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

                Utilities.animateAnswer(rootView.findViewById(R.id.textview_answer),
                        (ViewGroup)rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
    }
}
