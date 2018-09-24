package com.foreverrafs.numericals.fragments.ordinary_differential_eqns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.OdeResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentEuler extends Fragment implements View.OnClickListener, TextWatcher {

    private View rootView;
    private ViewGroup viewGroup;

    List<OdeResult> eulerResults = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ode_euler, container, false);

        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnShowAlgorithm = rootView.findViewById(R.id.buttonShowAlgo);

        //  Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bitter-Italic.ttf");
        Utilities.setTypeFace(rootView.findViewById(R.id.headerText), getContext(), Utilities.TypeFaceName.lobster_regular);
        Utilities.setTypeFace(rootView.findViewById(R.id.text_equation), getContext(), Utilities.TypeFaceName.bitter_italic);


        EditText etEquation = rootView.findViewById(R.id.text_equation);
        // etEquation.setTypeface(typeface);
        final EditText h = rootView.findViewById(R.id.text_h);
        final EditText initY = rootView.findViewById(R.id.text_initial_y);

        final EditText etX0 = rootView.findViewById(R.id.x0);
        final EditText etX1 = rootView.findViewById(R.id.x1);

        Bundle eulerArgs = getArguments();

        if (eulerArgs != null) {
            etEquation.setText(eulerArgs.getString("equation"));
            etX0.setText(String.valueOf(eulerArgs.getDouble("x0")));
            etX1.setText(String.valueOf(eulerArgs.getDouble("x1")));
            initY.setText(String.valueOf(eulerArgs.getDouble("initY")));
            h.setText(String.valueOf(eulerArgs.getInt("h")));
        }

        View.OnKeyListener myKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return false;

                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate(btnCalculate.getText().toString());
                    return true;
                }
                return false;
            }
        };

        h.setOnKeyListener(myKeyListener);
        initY.setOnKeyListener(myKeyListener);


        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnShowAlgorithm.setOnClickListener(this);

        etEquation.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("Location of Roots", "Bisection Method");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //repopulate textview entries with values. this usually happens when we are transitioning back from
        //the resultspane fragment
        initControls();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentOdeMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                Button btn = (Button) view;
                Log.i(Utilities.Log, "performing Euler's Forward calculation");
                onCalculate(btn.getText().toString());
                break;
            case R.id.buttonShowAlgo:
                 Utilities.showAlgorithmScreen(getContext(), "euler");
                break;

        }
    }

    private void onCalculate(final String buttonText) {
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        EditText etX1 = rootView.findViewById(R.id.x1);
        EditText etH = rootView.findViewById(R.id.text_h);
        EditText etInitY = rootView.findViewById(R.id.text_initial_y);


        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.buttonCalculate);

        try {
            String eqn = etEquation.getText().toString();
            Double x0 = Double.valueOf(etX0.getText().toString());
            Double x1 = Double.valueOf(etX1.getText().toString());

            double[] interval = new double[]{x0, x1};

            Double h = Double.valueOf(etH.getText().toString());
            int initY = Integer.valueOf(etInitY.getText().toString());

            if (eqn.isEmpty()) {
                Toast.makeText(getContext(), "No equation provided", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Equation is empty");
                return;
            }
            //are we displaying all answers or just the last iteration


            if (buttonText == getResources().getString(R.string.solve)) {
                eulerResults = Numericals.SolveOdeByEulersMethod(eqn, h, interval, initY);
                double answerAtLastIterate = eulerResults.get(eulerResults.size() - 1).getY();

                if (Double.isNaN(answerAtLastIterate) || Double.isInfinite(answerAtLastIterate)) {
                    Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                    Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                    return;
                }

                tvAnswer.setText(String.valueOf(answerAtLastIterate));

                //for transitions sake
                Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
                Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
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

                Utilities.replaceFragment(resultPane, getFragmentManager(), R.id.fragmentContainer, false);
            }
            calculateButton.setText(getResources().getString(R.string.show_iterations));

        } catch (NumberFormatException ex) {
            Toast.makeText(getContext(), "One or more of the input expressions are invalid", Toast.LENGTH_LONG).show();
            Log.i(Utilities.Log, "Error parsing one or more of the expressions");
        } finally {
            MainActivity.hideKeyboard(etEquation);
        }
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
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        btnCalculate.setText(getResources().getString(R.string.solve));
        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }
}
