package com.example.azizrafsanjani.numericals.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentRegulaFalsi extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_regfalsi, container, false);
        initControls();
        return rootView;
    }

    public void initControls() {
        Button btnCalculate = rootView.findViewById(R.id.btnCalculate);
        Button btnBack = rootView.findViewById(R.id.btnBack);
        EditText etEquation = rootView.findViewById(R.id.text_equation);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        etEquation.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("Location of Roots", "False Position");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Utilities.replaceFragment(this, new FragmentMainMenu(), getFragmentManager(),
                        R.id.fragmentContainer, true);
                break;

            case R.id.btnCalculate:
                Log.i(Utilities.Log, "performing Regula Falsi calculation");
                onCalculate();
                break;

        }
    }

    private void onCalculate() {
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etEpsilon = rootView.findViewById(R.id.text_epsilon);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        try {
            String eqn = etEquation.getText().toString();
            Double x0 = Double.valueOf(etX0.getText().toString());
            Double x1 = Double.valueOf(etX1.getText().toString());
            Double tol = Double.valueOf(etEpsilon.getText().toString());
            int iter = Integer.valueOf(etIterations.getText().toString());


            double root = 0.00;

            try {
                root = Numericals.FalsePosition(eqn, x0, x1, iter, tol);

                if (Double.isNaN(root) || Double.isInfinite(root)) {
                    Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                    Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                    return;
                }

                tvAnswer.setText(String.valueOf(root));
                Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
            } catch (IllegalArgumentException ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                MainActivity.hideKeyboard(etEquation);
            }


        } catch (NumberFormatException ex) {
            Toast.makeText(getContext(), "Invalid equation", Toast.LENGTH_LONG).show();
            Log.i(Utilities.Log, "One or more of the input values are invalid");
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onEquationChanged();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onEquationChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        onEquationChanged();
    }

    private void onEquationChanged() {
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }
}
