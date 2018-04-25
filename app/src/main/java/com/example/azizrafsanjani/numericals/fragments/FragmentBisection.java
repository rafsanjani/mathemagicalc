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

public class FragmentBisection extends Fragment implements View.OnClickListener, TextWatcher {

    private View rootView;
    private ViewGroup viewGroup;

    private TextWatcher etToleranceTextWatcher = null;
    private TextWatcher etIterationsTextWatcher = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_bisection, container, false);
        initControls();
        return rootView;
    }

    public void initControls() {
        Button btnCalculate = rootView.findViewById(R.id.btnCalculate);
        Button btnBack = rootView.findViewById(R.id.btnBack);
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        final EditText etIterations = rootView.findViewById(R.id.text_iterations);
        final EditText etTolerance = rootView.findViewById(R.id.text_epsilon);

        final EditText etX0 = rootView.findViewById(R.id.x0);
        final EditText etX1 = rootView.findViewById(R.id.x1);


        etIterationsTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            //get the tolerance value based on the number of iterations
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    etTolerance.removeTextChangedListener(etToleranceTextWatcher);
                    int iterations = Integer.parseInt(etIterations.getText().toString());
                    double x0 = 0, x1 = 0;

                    x0 = Double.parseDouble(etX0.getText().toString());
                    x1 = Double.parseDouble(etX1.getText().toString());


                    double tolerance = Numericals.getTolerance(iterations, x0, x1);

                    etTolerance.setText(String.valueOf(tolerance));
                } catch (NumberFormatException ex) {
                    Log.i(Utilities.Log, "Initial guesses are not provided");
                } finally {
                    etTolerance.addTextChangedListener(etToleranceTextWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etToleranceTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //get the number of iterations based on the tolerance value
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    etIterations.removeTextChangedListener(etIterationsTextWatcher);
                    double tolerance = Double.parseDouble(etTolerance.getText().toString());

                    if (tolerance == 0) {
                        return;
                    }

                    double x0 = 0, x1 = 0;

                    x0 = Double.parseDouble(etX0.getText().toString());
                    x1 = Double.parseDouble(etX1.getText().toString());

                    int iterations = Numericals.getIterations(tolerance, x0, x1);

                    etIterations.setText(String.valueOf(iterations));
                } catch (NumberFormatException ex) {
                    Log.i(Utilities.Log, "Initial guesses are not provided");
                } finally {
                    etIterations.addTextChangedListener(etIterationsTextWatcher);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etIterations.addTextChangedListener(etIterationsTextWatcher);
        etTolerance.addTextChangedListener(etToleranceTextWatcher);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        etEquation.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("Location of Roots", "Bisection Method");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Utilities.replaceFragment(new FragmentMainMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.btnCalculate:
                Log.i(Utilities.Log, "performing bisection calculation");
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

            double root = Numericals.Bisect(eqn, x0, x1, iter, tol);

            if (Double.isNaN(root) || Double.isInfinite(root)) {
                Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                return;
            }

            tvAnswer.setText(String.valueOf(root));

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Toast.makeText(getContext(), "One or more of the input expressions are invalid", Toast.LENGTH_LONG).show();
            Log.i(Utilities.Log, "Error parsing one or more of the expressions");
        } finally {
            MainActivity.hideKeyboard(etEquation);
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
