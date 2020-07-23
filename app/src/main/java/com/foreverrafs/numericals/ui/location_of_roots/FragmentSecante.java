package com.foreverrafs.numericals.ui.location_of_roots;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import timber.log.Timber;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecante extends FragmentRootBase implements TextWatcher {
    private static final String TAG = "FragmentSecante";

    private List<LocationOfRootResult> roots = null;
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
        registerOnKeyListener(tilEquation, tilIterations, tilX0, tilX1);
        tilEquation.getEditText().addTextChangedListener(this);

        parentContainer = (LinearLayout) rootView.findViewById(R.id.parentContainer);
    }

    @OnClick(R.id.btnCalculate)
    void onCalculateClicked(Button button) {
        onCalculate(button.getText().toString());
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithmClicked() {
        showAlgorithm("secant");
    }

    protected void onCalculate(@NonNull final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!validateInput(tilEquation, tilIterations, tilX0, tilX1)) {
            return;
        }

        String eqn;
        float x0;
        float x1;
        int iter;

        try {
            eqn = tilEquation.getEditText().getText().toString().toLowerCase();
            x0 = Float.parseFloat(tilX0.getEditText().getText().toString());
            x1 = Float.parseFloat(tilX1.getEditText().getText().toString());
            iter = Integer.parseInt(tilIterations.getEditText().getText().toString());

            roots = Numericals.secanteAll(eqn, x0, x1, iter);
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Timber.i("Error parsing one or more of the expressions");
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

            navController.navigate(FragmentSecanteDirections.fragmentSecanteResults(eqn, x0, x1, iter,
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
