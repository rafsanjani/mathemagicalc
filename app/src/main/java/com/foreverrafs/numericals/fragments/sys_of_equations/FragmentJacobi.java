package com.foreverrafs.numericals.fragments.sys_of_equations;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;

import org.apache.commons.math3.util.Precision;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentJacobi extends Fragment implements View.OnClickListener, TextWatcher, View.OnKeyListener {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Button btnCalculate = rootView.findViewById(R.id.button_calculate);
            btnCalculate.setText("CALCULATE");

            boolean success = msg.getData().getBoolean("success");
            if (!success) {
                Toast.makeText(getContext(), msg.getData().getString("exception"), Toast.LENGTH_LONG).show();

                return false;
            }
            double solution[] = msg.getData().getDoubleArray("results");
            TextView tvAnswer = rootView.findViewById(R.id.textview_answer);


            tvAnswer.setText(String.valueOf("[ " +
                    Precision.round(solution[0], 2) + ", "
                    + Precision.round(solution[1], 2) + ", " +
                    Precision.round(solution[2], 2) +
                    " ]"));


            //for transitions sake
            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            return true;
        }
    });

    View rootView;
    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_jacobi, container, false);
        initControls();
        return rootView;
    }

    public void initControls() {
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        Button btnBack = rootView.findViewById(R.id.button_back);

        Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFaceName.lobster_regular);

        EditText etEqn[] = new EditText[3];
        etEqn[0] = rootView.findViewById(R.id.text_equationx1);
        etEqn[1] = rootView.findViewById(R.id.text_equationx2);
        etEqn[2] = rootView.findViewById(R.id.text_equationx3);

        for (int i = 0; i < etEqn.length; i++) {
            etEqn[i].addTextChangedListener(this);
            etEqn[i].setOnKeyListener(this);
        }

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);


        viewGroup = (LinearLayout) rootView.findViewById(R.id.parentContainer);
        MainActivity.setToolBarInfo("System of Equations", "Jacobi's Method");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                Utilities.replaceFragment(new FragmentSystemOfEquationsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.button_calculate:
                Log.i(Utilities.Log, "performing Jacobi's calculation");
                onCalculate();
                break;
            case R.id.button_show_algo:
                onShowAlgorithm();
                break;
        }
    }

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name","jacobi");
        startActivity(new Intent(getContext(), ShowAlgorithm.class).putExtras(bundle));
    }

    private void onCalculate() {
        EditText etEqn[] = new EditText[3];
        etEqn[0] = rootView.findViewById(R.id.text_equationx1);
        etEqn[1] = rootView.findViewById(R.id.text_equationx2);
        etEqn[2] = rootView.findViewById(R.id.text_equationx3);

        EditText etx0[] = new EditText[3];
        etx0[0] = rootView.findViewById(R.id.x1);
        etx0[1] = rootView.findViewById(R.id.x2);
        etx0[2] = rootView.findViewById(R.id.x3);

        EditText etEpsilon = rootView.findViewById(R.id.text_epsilon);

        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);

        try {
            final String equations[] = new String[3];
            final double initGuess[] = new double[3];
            final double epsilon = Double.valueOf(etEpsilon.getText().toString());

            for (int i = 0; i < etEqn.length; i++) {
                equations[i] = etEqn[i].getText().toString();
                initGuess[i] = Double.valueOf(etx0[i].getText().toString());
            }

            Button btnCalculate = rootView.findViewById(R.id.button_calculate);
            btnCalculate.setText("CALCULATING....");


            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    double[] solution;
                    Message message = new Message();

                    try {
                        solution = Numericals.Jacobi(equations, initGuess, epsilon);
                        message.getData().putDoubleArray("results", solution);
                        message.getData().putBoolean("success", true);
                    } catch (Exception ex) {
                        message.getData().putBoolean("success", false);
                        message.getData().putString("exception", ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                    handler.sendMessage(message);
                }

            };

            Thread thread = new Thread(runnable);
            thread.start();


        } catch (IllegalArgumentException ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();

        } finally {
            MainActivity.hideKeyboard(etEpsilon);
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

        Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.HIDE);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onCalculate();
        }
        return true;
    }
}
