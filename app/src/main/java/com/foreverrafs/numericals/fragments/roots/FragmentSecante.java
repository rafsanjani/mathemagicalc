package com.foreverrafs.numericals.fragments.roots;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.core.LocationOfRootResult;
import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.fragments.FragmentShowAlgorithm;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecante extends FragmentRootBase implements TextWatcher {

    @BindView(R.id.btnCalculate)
    Button btnCalculate;

    @BindView(R.id.tvAnswer)
    TextView tvAnswer;

    @BindView(R.id.til_x0)
    TextInputLayout tilX0;

    @BindView(R.id.til_x1)
    TextInputLayout tilX1;

    @BindView(R.id.til_iterations)
    TextInputLayout tilIterations;

    @BindView(R.id.til_user_input)
    TextInputLayout tilEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_secante, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void initControls() {
        //initialize EditTexts
        Bundle secanteArgs = getArguments();

        if (secanteArgs != null) {
            tilEquation.getEditText().setText(secanteArgs.getString("equation"));
            tilX0.getEditText().setText(String.valueOf(secanteArgs.getDouble("x0")));
            tilX1.getEditText().setText(String.valueOf(secanteArgs.getDouble("x1")));

            tilIterations.getEditText().setText(String.valueOf(secanteArgs.getInt("iterations")));
        }

        registerOnKeyListener(tilEquation, tilIterations, tilX0, tilX1);


        tilEquation.getEditText().addTextChangedListener(this);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //initialize views
        initControls();
    }

    @OnClick(R.id.btnCalculate)
    void onCalculateClicked(Button button) {
        onCalculate(button.getText().toString());
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithmClicked() {
        onShowAlgorithm();
    }


    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", "secant");
        startActivity(new Intent(getContext(), FragmentShowAlgorithm.class).putExtras(bundle));
    }

    protected void onCalculate(@NonNull final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!validateInput(tilEquation, tilIterations, tilX0, tilX1)) {
            return;
        }

        String eqn;
        double x0, x1;
        int iter;

        try {
            eqn = tilEquation.getEditText().getText().toString().toLowerCase();
            x0 = Double.valueOf(tilX0.getEditText().getText().toString());
            x1 = Double.valueOf(tilX1.getEditText().getText().toString());
            iter = Integer.valueOf(tilIterations.getEditText().getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(Utilities.LOG_TAG, "Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration


        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            double root = Numericals.secante(eqn, x0, x1, iter);

            tvAnswer.setText(String.valueOf(root));

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
            List<LocationOfRootResult> roots = Numericals.secanteAll(eqn, x0, x1, iter);
            FragmentSecanteResults resultPane = new FragmentSecanteResults();

            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putInt("iterations", iter);
            eqnArgs.putDouble("x1", x1);

            resultPane.setArguments(eqnArgs);
            resultPane.setResults(roots);

            Utilities.replaceFragment(resultPane, getFragmentManager(), R.id.fragmentContainer);
        }
        btnCalculate.setText(getResources().getString(R.string.show_iterations));
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
