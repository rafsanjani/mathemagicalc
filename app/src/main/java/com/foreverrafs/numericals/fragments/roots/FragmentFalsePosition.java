package com.foreverrafs.numericals.fragments.roots;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentFalsePosition extends FragmentRootBase implements View.OnClickListener, TextWatcher {
    //private TextWatcher etToleranceTextWatcher = null;
    //private TextWatcher etIterationsTextWatcher = null;

    private TextInputLayout tilX0, tilX1, tilIterations, tilTolerance, tilEquation;
    private TextInputEditText etIterations, etX0, etX1, etTolerance, etEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_falseposition, container, false);
        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        Button btnBack = rootView.findViewById(R.id.button_back);

        //  Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bitter-Italic.ttf");
        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);
        Utilities.setTypeFace(rootView.findViewById(R.id.text_equation), getContext(), Utilities.TypeFaceName.bitter_italic);

        //initialize TextInputLayouts
        tilX0 = rootView.findViewById(R.id.til_x0);
        tilX1 = rootView.findViewById(R.id.til_x1);
        tilIterations = rootView.findViewById(R.id.til_iterations);
        tilTolerance = rootView.findViewById(R.id.til_tolerance);
        tilEquation = rootView.findViewById(R.id.til_user_input);

        //initialize EditTexts
        etEquation = rootView.findViewById(R.id.text_equation);
        etTolerance = rootView.findViewById(R.id.text_tolerance);
        etIterations = rootView.findViewById(R.id.text_iterations);
        etX0 = rootView.findViewById(R.id.x0);
        etX1 = rootView.findViewById(R.id.x1);

        Bundle falsePositionArgs = getArguments();

        if (falsePositionArgs != null) {
            etEquation.setText(falsePositionArgs.getString("equation"));
            etX0.setText(String.valueOf(falsePositionArgs.getDouble("x0")));
            etX1.setText(String.valueOf(falsePositionArgs.getDouble("x1")));
            etTolerance.setText(String.valueOf(falsePositionArgs.getDouble("tolerance")));
            etIterations.setText(String.valueOf(falsePositionArgs.getInt("iterations")));
        }

        registerOnKeyListener(tilEquation, tilTolerance, tilIterations, tilX0, tilX1);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Initialize all controls
        initControls();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                Utilities.replaceFragment(new FragmentLocationOfRootsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.button_calculate:
                Button btn = (Button) view;
                Log.i(Utilities.LOG_TAG, "performing False Position calculation");
                onCalculate(btn.getText().toString());
                break;

            case R.id.button_show_algo:
                onShowAlgorithm();
                break;
        }
    }

    private void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), "falseposition");
    }

    protected void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput(tilEquation, tilIterations, tilX0, tilX1)) {
            return;
        }

        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etEpsilon = rootView.findViewById(R.id.text_tolerance);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);

        if (etEpsilon.getText() == null)
            etEpsilon.setText("0.00");

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.button_calculate);

        String eqn;
        double x0, x1, tol;
        int iter;

        try {
            eqn = etEquation.getText().toString().toLowerCase();
            x0 = Double.valueOf(etX0.getText().toString());
            x1 = Double.valueOf(etX1.getText().toString());
            tol = Double.valueOf(etTolerance.getText().toString());
            iter = Integer.valueOf(etIterations.getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(Utilities.LOG_TAG, "Error parsing one or more of the expressions");
            return;
        }

        double root;
        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            try {
                root = Numericals.FalsePosition(eqn, x0, x1, iter, tol);
            } catch (Exception ex) {
                tilEquation.setErrorEnabled(true);
                tilEquation.setError(ex.getMessage());
                return;
            }

            tvAnswer.setText(String.valueOf(root));
            //for transitions sake
            Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
            List<LocationOfRootResult> roots = Numericals.FalsePositionAll(eqn, x0, x1, iter, tol);
            FragmentFalsePositionResults resultPane = new FragmentFalsePositionResults();
            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putInt("iterations", iter);
            eqnArgs.putDouble("tolerance", tol);
            eqnArgs.putDouble("x1", x1);

            resultPane.setArguments(eqnArgs);
            resultPane.setResults(roots);

            Utilities.replaceFragment(resultPane, getFragmentManager(), R.id.fragmentContainer, false);
        }
        calculateButton.setText(getResources().getString(R.string.show_iterations));

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
}
