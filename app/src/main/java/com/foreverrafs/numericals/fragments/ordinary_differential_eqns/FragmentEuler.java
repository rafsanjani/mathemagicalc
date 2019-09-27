package com.foreverrafs.numericals.fragments.ordinary_differential_eqns;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.OdeResult;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentEuler extends Fragment implements View.OnClickListener, TextWatcher {

    List<OdeResult> eulerResults = null;
    private View rootView;
    private ViewGroup viewGroup;
    private TextInputEditText etH, etX0, etX1, etInitY, etEquation;
    private TextInputLayout tilX0, tilX1, tilH, tilInitY, tilEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ode_euler, container, false);

        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.btnCalculate);
        Button btnBack = rootView.findViewById(R.id.btnBack);
        Button btnShowAlgorithm = rootView.findViewById(R.id.btnShowAlgo);

        //initialize TextInputLayouts
        tilX0 = rootView.findViewById(R.id.til_x0);
        tilX1 = rootView.findViewById(R.id.til_x1);
        tilInitY = rootView.findViewById(R.id.til_initY);
        tilH = rootView.findViewById(R.id.til_step_size);
        tilEquation = rootView.findViewById(R.id.til_user_input);


        //initialize EditTexts
        etEquation = rootView.findViewById(R.id.text_equation);
        etH = rootView.findViewById(R.id.text_h);
        etInitY = rootView.findViewById(R.id.text_initial_y);
        etX0 = rootView.findViewById(R.id.x0);
        etX1 = rootView.findViewById(R.id.x1);


        //Helps us to return app to normal state after navigation between results viewer and input area
        Bundle eulerArgs = getArguments();

        if (eulerArgs != null) {
            etEquation.setText(eulerArgs.getString("equation"));
            etX0.setText(String.valueOf(eulerArgs.getDouble("x0")));
            etX1.setText(String.valueOf(eulerArgs.getDouble("x1")));
            etInitY.setText(String.valueOf(eulerArgs.getDouble("initY")));
            etH.setText(String.valueOf(eulerArgs.getInt("h")));
        }

        View.OnKeyListener myKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                tilEquation.setErrorEnabled(false);
                // tilEquation.setErrorEnabled(false);
                tilH.setErrorEnabled(false);
                tilX0.setErrorEnabled(false);
                tilX1.setErrorEnabled(false);
                tilInitY.setErrorEnabled(false);
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return false;

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate(btnCalculate.getText().toString());
                    return true;
                }
                return false;
            }
        };

        etH.setOnKeyListener(myKeyListener);
        etInitY.setOnKeyListener(myKeyListener);
        etEquation.setOnKeyListener(myKeyListener);


        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnShowAlgorithm.setOnClickListener(this);

        etEquation.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        //("Location of Roots", "Bisection Method");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //lets initialize all our views, shall we?
        initControls();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                Utilities.replaceFragment(new FragmentOdeMenu(), getFragmentManager(), R.id.fragmentContainer);
                break;

            case R.id.btnCalculate:
                Button btn = (Button) view;
                Log.i(Utilities.LOG_TAG, "performing Euler's Forward calculation");
                onCalculate(btn.getText().toString());
                break;
            case R.id.btnShowAlgo:
                Utilities.showAlgorithmScreen(getContext(), "euler");
                break;

        }
    }

    private void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput()) {
            return;
        }

        //etEquation = rootView.findViewById(R.id.text_equation);

        TextView tvAnswer = rootView.findViewById(R.id.tvAnswer);
        Button calculateButton = rootView.findViewById(R.id.btnCalculate);

        String eqn;
        double x0;
        double x1;
        double h;
        double[] interval;
        int initY;

        try {
            eqn = etEquation.getText().toString();
            x0 = Double.valueOf(etX0.getText().toString());
            x1 = Double.valueOf(etX1.getText().toString());

            interval = new double[]{x0, x1};

            h = Double.valueOf(etH.getText().toString());
            initY = Integer.valueOf(etInitY.getText().toString());

        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            //Toast.makeText(getContext(), "One or more of the input expressions are invalid", Toast.LENGTH_LONG).show();
            Log.i(Utilities.LOG_TAG, "Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration
        if (buttonText == getResources().getString(R.string.solve)) {
            double answerAtLastIterate = 0;

            try {
                eulerResults = Numericals.solveOdeByEulersMethod(eqn, h, interval, initY);
            } catch (Exception ex) {
                tilEquation.setErrorEnabled(true);
                tilEquation.setError(ex.getMessage());
                return;
            }

            answerAtLastIterate = eulerResults.get(eulerResults.size() - 1).getY();

            tvAnswer.setText(String.valueOf(answerAtLastIterate));

            //display the answer
            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText == getResources().getString(R.string.show_iterations)) {
            FragmentEulerResults resultPane = new FragmentEulerResults();
            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putDouble("x1", x1);
            eqnArgs.putDouble("h", h);
            eqnArgs.putDouble("initY", initY);

            resultPane.setArguments(eqnArgs);
            resultPane.setResults(eulerResults);

            //Utilities.replaceFragment(resultPane, getFragmentManager(), R.id.fragmentContainer, false);
        }

        //replace the calculate button with show iterations so that clicking will show the iteration steps rather
        calculateButton.setText(getResources().getString(R.string.show_iterations));
    }

    private boolean checkForEmptyInput() {
        boolean validated = true;

        if (etEquation.getText().toString().isEmpty()) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("Cannot be empty");
            validated = false;
        }

        if (etInitY.getText().toString().isEmpty()) {
            tilInitY.setErrorEnabled(true);
            tilInitY.setError("error");
            validated = false;
        }

        if (etX0.getText().toString().isEmpty()) {
            tilX0.setErrorEnabled(true);
            tilX0.setError("error");
            validated = false;
        }

        if (etX1.getText().toString().isEmpty()) {
            tilX1.setErrorEnabled(true);
            tilX1.setError("error");
            validated = false;
        }

        if (etH.getText().toString().isEmpty()) {
            tilH.setErrorEnabled(true);
            tilH.setError("error");
            validated = false;
        }
        return validated;
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
        TextView tvAnswer = rootView.findViewById(R.id.tvAnswer);
        Button btnCalculate = rootView.findViewById(R.id.btnCalculate);
        btnCalculate.setText(getResources().getString(R.string.solve));
        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }
}
