package com.foreverrafs.numericals.fragments.sys_of_equations;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.fragments.FragmentShowAlgorithm;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.math3.util.Precision;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussSeidelWithSOR extends Fragment implements View.OnClickListener, TextWatcher, View.OnKeyListener {

    private View mRootView;
    private ViewGroup mViewGroup;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
            btnCalculate.setText(getString(R.string.calculating));

            boolean success = msg.getData().getBoolean("success");
            if (!success) {
                Toast.makeText(getContext(), msg.getData().getString("exception"), Toast.LENGTH_LONG).show();
                return false;
            }

            double[] solution = msg.getData().getDoubleArray("results");
            TextView tvAnswer = mRootView.findViewById(R.id.tvAnswer);

            tvAnswer.setText("[ " +
                    Precision.round(solution[0], 2) + ", "
                    + Precision.round(solution[1], 2) + ", " +
                    Precision.round(solution[2], 2) +
                    " ]");


            //for transitions sake
            Utilities.animateAnswer(tvAnswer, mViewGroup, Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(tvAnswer, mRootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_gaussseidelsor, container, false);
        initControls();
        return mRootView;
    }

    public void initControls() {
        Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
        Button btnBack = mRootView.findViewById(R.id.btnBack);

        TextInputLayout til = mRootView.findViewById(R.id.omega_textInputLayout);

        String omega = "\u03A9";
        til.setHint(omega.toLowerCase());


        EditText[] etEqn = new EditText[3];
        etEqn[0] = mRootView.findViewById(R.id.text_equationx1);
        etEqn[1] = mRootView.findViewById(R.id.text_equationx2);
        etEqn[2] = mRootView.findViewById(R.id.text_equationx3);

        for (EditText editText : etEqn) {
            editText.addTextChangedListener(this);
            editText.setOnKeyListener(this);
        }

        btnCalculate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mRootView.findViewById(R.id.btnShowAlgo).setOnClickListener(this);


        mViewGroup = (LinearLayout) mRootView.findViewById(R.id.parentContainer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                if (getActivity() != null)
                    getActivity().finish();
                break;

            case R.id.btnCalculate:
                Log.i(Utilities.LOG_TAG, "performing jacobi's calculation");
                onCalculate();
                break;
            case R.id.btnShowAlgo:
                onShowAlgorithm();
                break;

        }
    }

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", "gaussseidelwithsor");
        startActivity(new Intent(getContext(), FragmentShowAlgorithm.class).putExtras(bundle));
    }

    private void onCalculate() {
        EditText[] etEqn = new EditText[3];
        etEqn[0] = mRootView.findViewById(R.id.text_equationx1);
        etEqn[1] = mRootView.findViewById(R.id.text_equationx2);
        etEqn[2] = mRootView.findViewById(R.id.text_equationx3);

        EditText[] etx0 = new EditText[3];
        etx0[0] = mRootView.findViewById(R.id.x1);
        etx0[1] = mRootView.findViewById(R.id.x2);
        etx0[2] = mRootView.findViewById(R.id.x3);

        EditText etEpsilon = mRootView.findViewById(R.id.etTolerance);
        EditText etOmega = mRootView.findViewById(R.id.text_omega);

        try {
            final String[] equations = new String[3];
            final double[] initGuess = new double[3];
            final double epsilon = Double.valueOf(etEpsilon.getText().toString());
            final double omega = Double.valueOf(etOmega.getText().toString());
            for (int i = 0; i < etEqn.length; i++) {
                equations[i] = etEqn[i].getText().toString();
                initGuess[i] = Double.valueOf(etx0[i].getText().toString());
            }

            Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
            btnCalculate.setText(getString(R.string.calculating));


            Runnable runnable = () -> {
                double[] solution;
                Message message = new Message();

                try {
                    solution = Numericals.gaussSeidelWithSOR(equations, initGuess, epsilon, omega);
                    message.getData().putDoubleArray("results", solution);
                    message.getData().putBoolean("success", true);
                } catch (Exception ex) {
                    message.getData().putBoolean("success", false);
                    message.getData().putString("exception", ex.getMessage());
                }
                mHandler.sendMessage(message);
            };

            Thread thread = new Thread(runnable);
            thread.start();


        } catch (IllegalArgumentException ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();

        } finally {
            Utilities.hideKeyboard(etEpsilon);
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
        TextView tvAnswer = mRootView.findViewById(R.id.tvAnswer);

        Utilities.animateAnswer(tvAnswer, mViewGroup, Utilities.DisplayMode.HIDE);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onCalculate();
        }
        return true;
    }
}
