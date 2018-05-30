package com.example.azizrafsanjani.numericals.fragments.sys_of_equations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussianComplete4x4 extends Fragment implements View.OnClickListener, View.OnKeyListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gaussian_complete4x4, container, false);
        MainActivity.setToolBarInfo("System of Equations", "Gaussian Elimination (Partial Pivoting)");

        initControls();
        return rootView;
    }

    private void initControls() {

        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());

        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);


        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentSystemOfEquationsMenu(), getFragmentManager(), R.id.fragmentContainer);
                break;

            case R.id.buttonCalculate:
                Log.i(Utilities.Log, "solving the system using gaussian with partial pivoting");
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        getMatrices();
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader1), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
    }


    private void getMatrices() {
        EditText[][] etA = new EditText[4][4];
        double[][] a = new double[4][4];

        EditText[] etB = new EditText[4];
        double b[] = new double[4];


        etA[0][0] = rootView.findViewById(R.id.a11);
        etA[0][1] = rootView.findViewById(R.id.a12);
        etA[0][2] = rootView.findViewById(R.id.a13);
        etA[0][3] = rootView.findViewById(R.id.a13);

        etA[1][0] = rootView.findViewById(R.id.a21);
        etA[1][1] = rootView.findViewById(R.id.a22);
        etA[1][2] = rootView.findViewById(R.id.a23);
        etA[1][3] = rootView.findViewById(R.id.a23);

        etA[2][0] = rootView.findViewById(R.id.a31);
        etA[2][1] = rootView.findViewById(R.id.a32);
        etA[2][2] = rootView.findViewById(R.id.a33);
        etA[2][3] = rootView.findViewById(R.id.a33);

        etA[3][0] = rootView.findViewById(R.id.a41);
        etA[3][1] = rootView.findViewById(R.id.a42);
        etA[3][2] = rootView.findViewById(R.id.a43);
        etA[3][3] = rootView.findViewById(R.id.a43);

        etB[0] = rootView.findViewById(R.id.b1);
        etB[1] = rootView.findViewById(R.id.b2);
        etB[2] = rootView.findViewById(R.id.b3);
        etB[3] = rootView.findViewById(R.id.b4);

        MainActivity.hideKeyboard(etA[0][0]);

        for (int i = 0; i < etA.length; i++) {
            for (int j = 0; j < etA.length; j++) {
                etA[i][j].addTextChangedListener(this);
                try {
                    a[i][j] = Double.parseDouble(etA[i][j].getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                etB[i].addTextChangedListener(this);
                b[i] = Double.parseDouble(etB[i].getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        //get the solution matrix
        double[] solution = Numericals.GaussianWithCompletePivoting(a, b);


        //our previous matrices have been mutated so we can represent them on the textviews
        TextView[][] tvA = new TextView[4][4];
        tvA[0][0] = rootView.findViewById(R.id.sa11);
        tvA[0][1] = rootView.findViewById(R.id.sa12);
        tvA[0][2] = rootView.findViewById(R.id.sa13);
        tvA[0][3] = rootView.findViewById(R.id.sa14);

        tvA[1][0] = rootView.findViewById(R.id.sa21);
        tvA[1][1] = rootView.findViewById(R.id.sa22);
        tvA[1][2] = rootView.findViewById(R.id.sa23);
        tvA[1][3] = rootView.findViewById(R.id.sa24);

        tvA[2][0] = rootView.findViewById(R.id.sa31);
        tvA[2][1] = rootView.findViewById(R.id.sa32);
        tvA[2][2] = rootView.findViewById(R.id.sa33);
        tvA[2][3] = rootView.findViewById(R.id.sa34);

        tvA[3][0] = rootView.findViewById(R.id.sa41);
        tvA[3][1] = rootView.findViewById(R.id.sa42);
        tvA[3][2] = rootView.findViewById(R.id.sa43);
        tvA[3][3] = rootView.findViewById(R.id.sa44);


        TextView[] tvX = new TextView[4];
        TextView[] tvB = new TextView[4];

        tvB[0] = rootView.findViewById(R.id.sab1);
        tvB[1] = rootView.findViewById(R.id.sab2);
        tvB[2] = rootView.findViewById(R.id.sab3);
        tvB[3] = rootView.findViewById(R.id.sab4);

        tvX[0] = rootView.findViewById(R.id.sax1);
        tvX[1] = rootView.findViewById(R.id.sax2);
        tvX[2] = rootView.findViewById(R.id.sax3);
        tvX[3] = rootView.findViewById(R.id.sax4);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                tvA[i][j].setText(String.valueOf(a[i][j]));
            }
            tvX[i].setText(String.valueOf(solution[i]));
            tvB[i].setText(String.valueOf(b[i]));
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
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader1), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
