package com.example.azizrafsanjani.numericals.fragments.roots;

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

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.model.LocationOfRootResult;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;

import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentNewtonRaphson extends Fragment implements View.OnClickListener, TextWatcher {

    private View rootView;
    private ViewGroup viewGroup;

    private TextWatcher etToleranceTextWatcher = null;
    private TextWatcher etIterationsTextWatcher = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_newton, container, false);

        return rootView;
    }


    public void initControls() {
        final Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        Button btnBack = rootView.findViewById(R.id.buttonBack);

        //  Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Bitter-Italic.ttf");
        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());
        Utilities.setItalicTypeface(rootView.findViewById(R.id.text_equation), getContext());

        EditText etEquation = rootView.findViewById(R.id.text_equation);
        // etEquation.setTypeface(typeface);
        final EditText etIterations = rootView.findViewById(R.id.text_iterations);
        //final EditText etTolerance = rootView.findViewById(R.id.text_epsilon);

        final EditText etX0 = rootView.findViewById(R.id.x0);
        //final EditText etX1 = rootView.findViewById(R.id.x1);

        Bundle newtonRaphsonArgs = getArguments();

        if (newtonRaphsonArgs != null) {
            etEquation.setText(newtonRaphsonArgs.getString("equation"));
            etX0.setText(String.valueOf(newtonRaphsonArgs.getDouble("x0")));
            // etX1.setText(String.valueOf(newtonRaphsonArgs.getDouble("x1")));
            // etTolerance.setText(String.valueOf(bisectionArgs.getDouble("tolerance")));
            etIterations.setText(String.valueOf(newtonRaphsonArgs.getInt("iterations")));
        }

        etIterations.setOnKeyListener(new View.OnKeyListener() {
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
        });

        // etTolerance.addTextChangedListener(etToleranceTextWatcher);

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        etEquation.addTextChangedListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("Location of Roots", "NR Method");
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
                Utilities.replaceFragment(new FragmentLocationOfRootsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                Button btn = (Button) view;
                Log.i(Utilities.Log, "performing bisection calculation");
                onCalculate(btn.getText().toString());
                break;

        }
    }

    private void onCalculate(final String buttonText) {
        EditText etEquation = rootView.findViewById(R.id.text_equation);
        EditText etX0 = rootView.findViewById(R.id.x0);
        // EditText etX1 = rootView.findViewById(R.id.x1);
        // EditText etEpsilon = rootView.findViewById(R.id.text_epsilon);
        EditText etIterations = rootView.findViewById(R.id.text_iterations);


        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        Button calculateButton = rootView.findViewById(R.id.buttonCalculate);

        try {
            String eqn = etEquation.getText().toString();
            Double x0 = Double.valueOf(etX0.getText().toString());
            // Double x1 = Double.valueOf(etX1.getText().toString());
            //Double tol = Double.valueOf(etEpsilon.getText().toString());
            int iter = Integer.valueOf(etIterations.getText().toString());

            if (eqn.isEmpty()) {
                Toast.makeText(getContext(), "No equation provided", Toast.LENGTH_LONG).show();
                Log.i(Utilities.Log, "Equation is empty");
                return;

            }
            //are we displaying all answers or just the last iteration


            if (buttonText == getResources().getString(R.string.calculate)) {
                double root = Numericals.NewtonRaphson(eqn, x0, iter);

                if (Double.isNaN(root) || Double.isInfinite(root)) {
                    Toast.makeText(getContext(), "Syntax Error: Please check equation", Toast.LENGTH_LONG).show();
                    Log.i(Utilities.Log, "Syntax error, unable to evaluate expression");
                    return;
                }
                tvAnswer.setText(String.valueOf(root));

                //for transitions sake
                Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
                Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            } else if (buttonText == getResources().getString(R.string.show_iterations)) {
                List<LocationOfRootResult> roots = Numericals.NewtonRaphsonAll(eqn, x0, iter);
                FragmentNewtonRaphsonResults resultPane = new FragmentNewtonRaphsonResults();
                Bundle eqnArgs = new Bundle();

                eqnArgs.putString("equation", eqn);
                eqnArgs.putDouble("x0", x0);
                eqnArgs.putInt("iterations", iter);
                // eqnArgs.putDouble("tolerance", tol);
                //eqnArgs.putDouble("x1", x1);

                resultPane.setArguments(eqnArgs);
                resultPane.setResults(roots);

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
        btnCalculate.setText(getResources().getString(R.string.calculate));
        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }


}
