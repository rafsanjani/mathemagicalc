package com.foreverrafs.numericals.ui.ordinary_differential_eqns;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.core.OdeResult;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentEuler extends Fragment implements TextWatcher {
    private static final String TAG = "FragmentEuler";

    private View rootView;
    @BindView(R.id.til_x0)
    TextInputLayout tilX0;
    @BindView(R.id.til_x1)
    TextInputLayout tilX1;
    @BindView(R.id.til_step_size)
    TextInputLayout tilH;
    @BindView(R.id.til_initY)
    TextInputLayout tilInitY;
    @BindView(R.id.til_user_input)
    TextInputLayout tilEquation;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;
    @BindView(R.id.btnShowAlgo)
    Button btnShowAlgo;
    @BindView(R.id.tvAnswer)
    TextView tvAnswer;
    private EditText etH, etX0, etX1, etInitY, etEquation;
    private NavController mNavController = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ode_euler, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void initControls() {
        //initialize EditTexts
        etEquation = tilEquation.getEditText();
        etH = tilH.getEditText();
        etInitY = tilInitY.getEditText();
        etX0 = tilX0.getEditText();
        etX1 = tilX1.getEditText();

        View.OnKeyListener myKeyListener = (view, i, keyEvent) -> {
            tilEquation.setErrorEnabled(false);
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
        };

        etH.setOnKeyListener(myKeyListener);
        etInitY.setOnKeyListener(myKeyListener);
        etEquation.setOnKeyListener(myKeyListener);
        etEquation.addTextChangedListener(this);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mNavController = Navigation.findNavController(view);
        //lets initialize all our views, shall we?
        initControls();
    }

    @OnClick(R.id.btnBackToMainMenu)
    void backToMainMenu(Button button) {
        Activity parentActivity = getActivity();
        if (parentActivity != null)
            ((MainActivity) parentActivity).goToMainMenu(button);
    }

    @OnClick(R.id.btnCalculate)
    void onCalculate(Button button) {
        Timber.i("performing Euler's Forward calculation");
        onCalculate(button.getText().toString());
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithm() {
        if (getActivity() != null)
            ((MainActivity) getActivity()).showAlgorithm(mNavController,
                    "euler");
    }

    private void onCalculate(final String buttonText) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput()) {
            return;
        }

        String eqn;
        float x0, x1, h;
        double[] interval;
        int initY;

        try {
            eqn = etEquation.getText().toString();
            x0 = Float.parseFloat(etX0.getText().toString());
            x1 = Float.parseFloat(etX1.getText().toString());

            interval = new double[]{x0, x1};

            h = Float.parseFloat(etH.getText().toString());
            initY = Integer.parseInt(etInitY.getText().toString());


            double answerAtLastIterate;
            List<OdeResult> eulerResults = Numericals.solveOdeByEulersMethod(eqn, h, interval, initY);

            //are we displaying all answers or just the last iteration
            if (buttonText.equals(getResources().getString(R.string.solve))) {
                answerAtLastIterate = eulerResults.get(eulerResults.size() - 1).getY();

                tvAnswer.setText(String.valueOf(answerAtLastIterate));

                //display the answer
                Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.SHOW);
                Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.SHOW);
            } else if (buttonText.equals(getResources().getString(R.string.show_iterations))) {
                mNavController.navigate(FragmentEulerDirections.fragmentEulerResults(eqn, x0, x1, h, initY,
                        eulerResults.toArray(new OdeResult[0])));
            }
            //replace the calculate button with show iterations so that clicking will show the iteration steps rather
            btnCalculate.setText(getResources().getString(R.string.show_iterations));
        } catch (NumberFormatException ex) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError("One or more of the input expressions are invalid!");
            Timber.i("Error parsing one or more of the expressions");
        } catch (Exception exception) {
            tilEquation.setErrorEnabled(true);
            tilEquation.setError(exception.getMessage());
        }
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
        btnCalculate.setText(getResources().getString(R.string.solve));
        Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.HIDE);
    }
}
