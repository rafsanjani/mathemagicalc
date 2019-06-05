package com.foreverrafs.numericals.fragments.roots;

import android.content.Intent;
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
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecante extends FragmentRootBase implements View.OnClickListener, TextWatcher {
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


        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);
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

        registerOnKeyListener(tilEquation, tilIterations, tilX0, tilX1);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);
        etEquation.addTextChangedListener(this);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        ////("Location of Roots", "Secante Method");
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
                Log.i(Utilities.LOG_TAG, "performing Secate calculation");
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

    protected void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput()) {
            return;
        }

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.button_calculate);


        String eqn;
        double x0, x1;
        int iter;

        try {
            eqn = etEquation.getText().toString().toLowerCase();
            x0 = Double.valueOf(etX0.getText().toString());
            x1 = Double.valueOf(etX1.getText().toString());
            iter = Integer.valueOf(etIterations.getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(Utilities.LOG_TAG, "Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration


        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            double root = Numericals.Secante(eqn, x0, x1, iter);

            tvAnswer.setText(String.valueOf(root));

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
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
