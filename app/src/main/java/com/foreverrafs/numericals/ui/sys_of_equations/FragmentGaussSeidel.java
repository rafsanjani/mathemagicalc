package com.foreverrafs.numericals.ui.sys_of_equations;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.databinding.FragmentGaussseidelBinding;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.Locale;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussSeidel extends FragmentSystemOfEquationsBase implements TextWatcher, View.OnKeyListener {


    private ViewGroup viewGroup;

    private FragmentGaussseidelBinding binding;
    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            binding.btnCalculate.setText(getString(R.string.calculate));

            boolean success = msg.getData().getBoolean("success");
            if (!success) {
                Toast.makeText(getContext(), msg.getData().getString("exception"), Toast.LENGTH_LONG).show();
                return false;
            }
            double[] solution = msg.getData().getDoubleArray("results");
            if (solution == null)
                return false;

            TextView tvAnswer = binding.tvAnswer;


            //todo: Perform regression test on this section
            String answer = String.format(Locale.US, "[%.2f, %.2f, %.2f]", solution[0], solution[1], solution[2]);
            tvAnswer.setText(answer);

            //for transitions sake
            Utilities.animateAnswer(tvAnswer, viewGroup, Utilities.DisplayMode.SHOW);

            return true;
        }
    });

    public FragmentGaussSeidel() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGaussseidelBinding.inflate(inflater);
        viewGroup = binding.parentContainer;

        initControls();

        return binding.getRoot();
    }


    public void initControls() {
        EditText[] etEqn = new EditText[3];
        etEqn[0] = binding.textEquationx1;
        etEqn[1] = binding.textEquationx2;
        etEqn[2] = binding.textEquationx3;

        for (EditText editText : etEqn) {
            editText.addTextChangedListener(this);
            editText.setOnKeyListener(this);
        }

        binding.btnBackToMainMenu.setOnClickListener(v -> onBackClicked());
        binding.btnCalculate.setOnClickListener(v -> onCalculateClicked());
        binding.btnShowAlgo.setOnClickListener(v -> onShowAlgoClicked());
    }


    void onBackClicked() {
        goToMainmenu();
    }

    void onCalculateClicked() {
        onCalculate();
    }

    void onShowAlgoClicked() {
        showAlgorithm("gaussseidel");
    }

    private void onCalculate() {
        EditText[] etEqn = new EditText[3];
        etEqn[0] = binding.textEquationx1;
        etEqn[1] = binding.textEquationx2;
        etEqn[2] = binding.textEquationx3;

        EditText[] etx0 = new EditText[3];
        etx0[0] = binding.x1;
        etx0[1] = binding.x2;
        etx0[2] = binding.x3;

        EditText etEpsilon = binding.etTolerance;


        try {
            final String[] equations = new String[3];
            final double[] initGuess = new double[3];
            final double epsilon = Double.parseDouble(etEpsilon.getText().toString());

            for (int i = 0; i < etEqn.length; i++) {
                equations[i] = etEqn[i].getText().toString();
                initGuess[i] = Double.parseDouble(etx0[i].getText().toString());
            }

            binding.btnCalculate.setText(getString(R.string.calculating));


            Runnable runnable = () -> {
                double[] solution;
                Message message = new Message();

                try {
                    solution = Numericals.gaussSeidel(equations, initGuess, epsilon);
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
        TextView tvAnswer = binding.tvAnswer;

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
