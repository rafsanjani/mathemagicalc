package com.foreverrafs.numericals.fragments.roots;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecante extends Fragment implements View.OnClickListener, TextWatcher {

    private View rootView;
    private ViewGroup viewGroup;

    private TextInputLayout tilX0, tilX1, tilIterations, tilEquation;
    private TextInputEditText etIterations, etX0, etX1, etEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_secante, container, false);

        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        Button btnBack = rootView.findViewById(R.id.button_back);


        Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFaceName.lobster_regular);
        Utilities.setTypeFace(rootView.findViewById(R.id.text_equation), getContext(), Utilities.TypeFaceName.bitter_italic);

        //Initialize TextInputLayouts
        tilX0 = rootView.findViewById(R.id.til_x0);
        tilX1 = rootView.findViewById(R.id.til_x1);
        tilIterations = rootView.findViewById(R.id.til_iterations);
        tilEquation = rootView.findViewById(R.id.til_user_input);

        //initialize EditTexts
        etEquation = rootView.findViewById(R.id.text_equation);
        etIterations = rootView.findViewById(R.id.text_iterations);
        etX0 = rootView.findViewById(R.id.x0);
        etX1 = rootView.findViewById(R.id.x1);

        Bundle secanteArgs = getArguments();

        if (secanteArgs != null) {
            etEquation.setText(secanteArgs.getString("equation"));
            etX0.setText(String.valueOf(secanteArgs.getDouble("x0")));
            etX1.setText(String.valueOf(secanteArgs.getDouble("x1")));

            etIterations.setText(String.valueOf(secanteArgs.getInt("iterations")));
        }

        View.OnKeyListener myKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                tilEquation.setErrorEnabled(false);
                tilIterations.setErrorEnabled(false);
                tilX0.setErrorEnabled(false);
                tilX1.setErrorEnabled(false);
                //tilTolerance.setErrorEnabled(false);
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return false;

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate(btnCalculate.getText().toString());
                    return true;
                }
                return false;
            }
        };

        etIterations.setOnKeyListener(myKeyListener);
        etEquation.setOnKeyListener(myKeyListener);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);
        etEquation.addTextChangedListener(this);

        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        //MainActivity.setToolBarInfo("Location of Roots", "Secante Method");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //initialize views
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
                Log.i(Utilities.Log, "performing Secate calculation");
                onCalculate(btn.getText().toString());
                break;
            case R.id.button_show_algo:
                onShowAlgorithm();
                break;


        }
    }

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", "secant");
        startActivity(new Intent(getContext(), ShowAlgorithm.class).putExtras(bundle));
    }

    private void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput()) {
            return;
        }

        /*EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);*/

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.button_calculate);


        String eqn;
        double x0, x1, tol;
        int iter;

        try {
            eqn = etEquation.getText().toString().toLowerCase();
            x0 = Double.valueOf(etX0.getText().toString());
            x1 = Double.valueOf(etX1.getText().toString());
            iter = Integer.valueOf(etIterations.getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(Utilities.Log, "Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration


        if (buttonText == getResources().getString(R.string.calculate)) {
            double root = Numericals.Secante(eqn, x0, x1, iter);

            /*if (Double.isNaN(root) || Double.isInfinite(root)) {
                Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                return;
            }*/

            tvAnswer.setText(String.valueOf(root));

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText == getResources().getString(R.string.show_iterations)) {
            List<LocationOfRootResult> roots = Numericals.SecanteAll(eqn, x0, x1, iter);
            FragmentSecanteResults resultPane = new FragmentSecanteResults();

            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putInt("iterations", iter);
            eqnArgs.putDouble("x1", x1);

            resultPane.setArguments(eqnArgs);
            resultPane.setResults(roots);

            Utilities.replaceFragment(resultPane, getFragmentManager(), R.id.fragmentContainer, false);
        }
        calculateButton.setText(getResources().getString(R.string.show_iterations));
    }

    private boolean checkForEmptyInput() {
        boolean validated = true;
        if (etEquation.getText().toString().isEmpty()) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("Cannot be empty");
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

        if (etIterations.getText().toString().isEmpty()) {
            tilIterations.setErrorEnabled(true);
            tilIterations.setError("error");
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
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        btnCalculate.setText(getResources().getString(R.string.calculate));
        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }


}
