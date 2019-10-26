package com.foreverrafs.numericals.fragments.roots;

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
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentFalsePosition extends FragmentRootBase implements TextWatcher {
    private static final String TAG = "FragmentFalsePosition";
    @BindView(R.id.til_x0)
    TextInputLayout tilX0;

    @BindView(R.id.til_x1)
    TextInputLayout tilX1;

    @BindView(R.id.til_iterations)
    TextInputLayout tilIterations;

    @BindView(R.id.til_tolerance)
    TextInputLayout tilTolerance;

    @BindView(R.id.til_user_input)
    TextInputLayout tilEquation;

    @BindView(R.id.tvAnswer)
    TextView tvAnswer;

    @BindView(R.id.btnCalculate)
    Button btnCalculate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_falseposition, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void initControls() {
        Bundle falsePositionArgs = getArguments();

        if (falsePositionArgs != null) {
            tilEquation.getEditText().setText(falsePositionArgs.getString("equation"));
            tilX0.getEditText().setText(String.valueOf(falsePositionArgs.getDouble("x0")));
            tilX1.getEditText().setText(String.valueOf(falsePositionArgs.getDouble("x1")));
            tilTolerance.getEditText().setText(String.valueOf(falsePositionArgs.getDouble("tolerance")));
            tilIterations.getEditText().setText(String.valueOf(falsePositionArgs.getInt("iterations")));
        }

        registerOnKeyListener(tilEquation, tilTolerance, tilIterations, tilX0, tilX1);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Initialize all controls
        initControls();
    }


    @OnClick(R.id.btnCalculate)
    void onCalculateClicked(Button button) {
        onCalculate(button.getText().toString());
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithmClicked() {
        showAlgorithm("falseposition");
    }

    protected void onCalculate(@NonNull final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!validateInput(tilEquation, tilIterations, tilX0, tilX1)) {
            return;
        }

        if (tilTolerance.getEditText().getText() == null)
            tilTolerance.getEditText().setText("0.00");

        String eqn;
        double x0, x1, tol;
        int iter;

        try {
            eqn = tilEquation.getEditText().getText().toString().toLowerCase();
            x0 = Double.valueOf(tilX0.getEditText().getText().toString());
            x1 = Double.valueOf(tilX1.getEditText().getText().toString());
            tol = Double.valueOf(tilTolerance.getEditText().getText().toString());
            iter = Integer.valueOf(tilIterations.getEditText().getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Log.i(TAG, "Error parsing one or more of the expressions");
            return;
        }

        double root;
        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            try {
                root = Numericals.falsePosition(eqn, x0, x1, iter, tol);
            } catch (Exception ex) {
                tilEquation.setErrorEnabled(true);
                tilEquation.setError(ex.getMessage());
                return;
            }

            tvAnswer.setText(String.valueOf(root));
            //for transitions sake
            Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
            List<LocationOfRootResult> roots = Numericals.falsePositionAll(eqn, x0, x1, iter, tol);
            FragmentFalsePositionResults resultPane = new FragmentFalsePositionResults();
            Bundle eqnArgs = new Bundle();

            eqnArgs.putString("equation", eqn);
            eqnArgs.putDouble("x0", x0);
            eqnArgs.putInt("iterations", iter);
            eqnArgs.putDouble("tolerance", tol);
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
