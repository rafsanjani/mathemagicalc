package com.foreverrafs.numericals.ui.location_of_roots;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.core.LocationOfRootResult;
import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.databinding.FragmentLocOfRootsNewtonBinding;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import timber.log.Timber;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentNewtonRaphson extends FragmentRootBase implements TextWatcher {
    private static final String TAG = "FragmentNewtonRaphson";
    TextInputLayout tilX0;

    TextInputLayout tilEquation;

    TextInputLayout tilIterations;

    TextView tvAnswer;

    Button btnCalculate;

    FragmentLocOfRootsNewtonBinding binding;


    private EditText etIterations, etX0, etEquation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocOfRootsNewtonBinding.inflate(inflater);
        btnCalculate = binding.btnCalculate;
        tvAnswer = binding.tvAnswer;
        tilIterations = binding.tilIterations;
        tilEquation = binding.tilUserInput;
        tilX0 = binding.tilX0;
        rootView = binding.getRoot();
        return binding.getRoot();
    }


    public void initControls() {
        //initialize EditTexts
        etEquation = tilEquation.getEditText();
        etIterations = tilIterations.getEditText();
        etX0 = tilX0.getEditText();

        registerOnKeyListener(tilIterations, tilEquation, tilX0);

        etEquation.addTextChangedListener(this);

        btnCalculate.setOnClickListener(v -> onCalculateClicked(btnCalculate));

        binding.btnShowAlgo.setOnClickListener(v -> onShowAlgoClicked());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initControls();
    }

    void onCalculateClicked(Button button) {
        onCalculate(button.getText().toString());
    }

    void onShowAlgoClicked() {
        showAlgorithm("newtonraphson");
    }

    protected void onCalculate(@NonNull final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!validateInput(tilEquation, tilX0, tilIterations)) {
            return;
        }

        String eqn;
        float x0;
        int iter;

        try {
            eqn = etEquation.getText().toString().toLowerCase();
            x0 = Float.parseFloat(etX0.getText().toString());
            iter = Integer.parseInt(etIterations.getText().toString());
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Timber.i("Error parsing one or more of the expressions");
            return;
        }

        //are we displaying all answers or just the last iteration
        if (buttonText.equals(getResources().getString(R.string.calculate))) {
            try {
                roots = Numericals.newtonRaphsonAll(eqn, x0, iter);
            } catch (Exception ex) {
                tilEquation.setErrorEnabled(true);
                tilEquation.setError(ex.getMessage());
                return;
            }

            //get the last item from the roots and display in single mode to the user
            double root = roots.get(roots.size() - 1).getX1();

            tvAnswer.setText(String.valueOf(root));

            //animate the answer into view
            Utilities.animateAnswer(tvAnswer, rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
            List<LocationOfRootResult> roots = Numericals.newtonRaphsonAll(eqn, x0, iter);

            navController.navigate(FragmentNewtonRaphsonDirections.fragmentNewtonRaphsonResults(eqn, x0, iter,
                    roots.toArray(new LocationOfRootResult[0])));
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
