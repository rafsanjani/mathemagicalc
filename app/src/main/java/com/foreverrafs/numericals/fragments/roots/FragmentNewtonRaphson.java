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

public class FragmentNewtonRaphson extends FragmentRootBase implements View.OnClickListener, TextWatcher {
    private TextInputLayout tilX0, tilIterations, tilEquation;
    private TextInputEditText etIterations, etX0, etEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_newton, container, false);
        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        Button btnBack = rootView.findViewById(R.id.button_back);

        //Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bitter-Italic.ttf");
        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);
        Utilities.setTypeFace(rootView.findViewById(R.id.text_equation), getContext(), Utilities.TypeFaceName.bitter_italic);

        //initialize TextInputLayouts
        tilX0 = rootView.findViewById(R.id.til_x0);
        tilIterations = rootView.findViewById(R.id.til_iterations);
        tilEquation = rootView.findViewById(R.id.til_user_input);

        //initialize EditTexts
        etEquation = rootView.findViewById(R.id.text_equation);
        etIterations = rootView.findViewById(R.id.text_iterations);
        etX0 = rootView.findViewById(R.id.x0);


        Bundle newtonRaphsonArgs = getArguments();

        if (newtonRaphsonArgs != null) {
            etEquation.setText(newtonRaphsonArgs.getString("equation"));
            etX0.setText(String.valueOf(newtonRaphsonArgs.getDouble("x0")));
            etIterations.setText(String.valueOf(newtonRaphsonArgs.getInt("iterations")));
        }

        registerOnKeyListener(tilIterations, tilEquation, tilX0);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);

        etEquation.addTextChangedListener(this);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        //("Location of Roots", "NR Method");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                Log.i(Utilities.LOG_TAG, "performing Newton Raphson calculation");
                onCalculate(btn.getText().toString());
                break;
            case R.id.button_show_algo:
                onShowAlgorithm();
                break;
        }
    }

    private void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), "newtonraphson");
    }

    protected void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput(tilEquation, tilX0, tilIterations)) {
            return;
        }

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.button_calculate);

        String eqn;
        double x0;
        int iter;

        try {
            eqn = etEquation.getText().toString().toLowerCase();
            x0 = Double.valueOf(etX0.getText().toString());
            iter = Integer.valueOf(etIterations.getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(Utilities.LOG_TAG, "Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration
        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            try {
                roots = Numericals.NewtonRaphsonAll(eqn, x0, iter);
            } catch (Exception ex) {
                tilEquation.setErrorEnabled(true);
                tilEquation.setError(ex.getMessage());
                return;
            }

            //get the last item from the roots and display in single mode to the user
            double root = roots.get(roots.size() - 1).getX1();

            tvAnswer.setText(String.valueOf(root));

            //animate the answer into view
            Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
            List<LocationOfRootResult> roots = Numericals.NewtonRaphsonAll(eqn, x0, iter);
            FragmentNewtonRaphsonResults resultPane = new FragmentNewtonRaphsonResults();
            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putInt("iterations", iter);

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
