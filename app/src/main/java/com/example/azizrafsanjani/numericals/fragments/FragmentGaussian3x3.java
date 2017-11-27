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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussian3x3 extends Fragment implements View.OnClickListener, View.OnKeyListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gaussian_partial3x3, container, false);
        MainActivity.setToolBarInfo("System of Equations", "Gaussian Elimination (Partial Pivoting)");

        initControls();
        return rootView;
    }

    private void initControls() {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FallingSky.otf");
        TextView tvAnswer = (TextView) rootView.findViewById(R.id.textview_answer);
        tvAnswer.setTypeface(typeface);

        Button btnBack = (Button) rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = (Button) rootView.findViewById(R.id.buttonCalculate);


        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(this, new FragmentMainMenu(), getFragmentManager(), R.id.fragmentContainer);
                break;

            case R.id.buttonCalculate:
                Toast.makeText(getContext(), "clicked me", Toast.LENGTH_LONG).show();
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        getMatrices();
    }


    private void getMatrices() {
        EditText[][] etA = new EditText[3][5];
        double a[][] = new double[3][5];

        EditText[] etB = new EditText[3];
        double b[] = new double[3];

        EditText[] etX = new EditText[3];


        etA[0][0] = (EditText) rootView.findViewById(R.id.a11);
        etA[0][1] = (EditText) rootView.findViewById(R.id.a12);
        etA[0][2] = (EditText) rootView.findViewById(R.id.a13);
        etA[1][0] = (EditText) rootView.findViewById(R.id.a21);
        etA[1][1] = (EditText) rootView.findViewById(R.id.a22);
        etA[1][2] = (EditText) rootView.findViewById(R.id.a23);
        etA[2][0] = (EditText) rootView.findViewById(R.id.a31);
        etA[2][1] = (EditText) rootView.findViewById(R.id.a32);
        etA[2][2] = (EditText) rootView.findViewById(R.id.a33);

        etB[0] = (EditText) rootView.findViewById(R.id.b1);
        etB[1] = (EditText) rootView.findViewById(R.id.b2);
        etB[2] = (EditText) rootView.findViewById(R.id.b3);

        etX[0] = (EditText) rootView.findViewById(R.id.x1);

        for (int i = 0; i < etA.length; i++) {
            try {
                b[i] = Double.parseDouble(etB[i].getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < etA.length; j++) {
                try {
                    a[i][j] = Double.parseDouble(etA[i][j].getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }

        double[] solution = Numericals.GaussianWithPartialPivoting(a, b);

        //print it all out and see
        for (int i = 0; i < etA.length; i++) {
            for (int j = 0; j < etA.length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print(b[i]);
            System.out.println();
        }
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
        Utilities.animateAnswer((TextView) rootView.findViewById(R.id.textview_answer),
                (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
    }
}
