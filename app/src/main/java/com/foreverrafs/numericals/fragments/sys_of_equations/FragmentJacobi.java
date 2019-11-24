package com.foreverrafs.numericals.fragments.sys_of_equations;

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

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.Locale;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentJacobi extends FragmentSystemOfEquationsBase implements View.OnClickListener, TextWatcher, View.OnKeyListener {

    private static final String TAG = "FragmentJacobi";
    private View mRootView;
    private ViewGroup mViewGroup;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
            btnCalculate.setText(getString(R.string.calculate));

            boolean success = msg.getData().getBoolean("success");
            if (!success) {
                Toast.makeText(getContext(), msg.getData().getString("exception"), Toast.LENGTH_LONG).show();

                return false;
            }
            double[] solution = msg.getData().getDoubleArray("results");
            if (solution == null)
                return false;

            TextView tvAnswer = mRootView.findViewById(R.id.tvAnswer);

            String answer = String.format(Locale.US, "[%.2f, %.2f, %.2f]", solution[0], solution[1], solution[2]);
//            tvAnswer.setText("[ " +
//                    Precision.round(solution[0], 2) + ", "
//                    + Precision.round(solution[1], 2) + ", " +
//                    Precision.round(solution[2], 2) +
//                    " ]");
            tvAnswer.setText(answer);

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, mViewGroup, Utilities.DisplayMode.SHOW);
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_jacobi, container, false);
        initControls();
        return mRootView;
    }

    public void initControls() {
        Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
        Button btnBack = mRootView.findViewById(R.id.btnBackToMainMenu);


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
            case R.id.btnBackToMainMenu:
                goToMainmenu((Button) view);
                break;

            case R.id.btnCalculate:
                Log.i(TAG, "performing jacobi's calculation");
                onCalculate();
                break;

            case R.id.btnShowAlgo:
                showAlgorithm("jacobi");
                break;
        }
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


        try {
            final String[] equations = new String[3];
            final double[] initGuess = new double[3];
            final double epsilon = Double.parseDouble(etEpsilon.getText().toString());

            for (int i = 0; i < etEqn.length; i++) {
                equations[i] = etEqn[i].getText().toString();
                initGuess[i] = Double.parseDouble(etx0[i].getText().toString());
            }

            Button btnCalculate = mRootView.findViewById(R.id.btnCalculate);
            btnCalculate.setText(getString(R.string.calculating));


            Runnable runnable = () -> {
                double[] solution;
                Message message = new Message();

                try {
                    solution = Numericals.jacobi(equations, initGuess, epsilon);
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
