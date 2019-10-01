package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentAllInOne extends Fragment implements View.OnClickListener, TextWatcher {

    @BindView(R.id.layout_answer_area)
    ConstraintLayout layoutAnswerArea;


    @BindView(R.id.btnCalculate)
    Button btnCalculate;

    @BindView(R.id.btnBack)
    Button btnBack;

    @BindView(R.id.til_user_input)
    TextInputLayout tilUserInput;

    @BindView(R.id.tvAnswerBinary)
    TextView tvBinary;

    @BindView(R.id.tvAnswerOctal)
    TextView tvOctal;

    @BindView(R.id.tvAnswerHexadecimal)
    TextView tvHexadecimal;


    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversions_allinone, container, false);

        ButterKnife.bind(this, rootView);
        initControls();
        return rootView;
    }

    private void initControls() {

        EditText etInput = tilUserInput.getEditText();

        etInput.setOnKeyListener((view, i, keyEvent) -> {
            //take the error message away
            tilUserInput.setErrorEnabled(false);

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                onCalculate();
                return true;
            }
            return false;
        });

        etInput.addTextChangedListener(this);

        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                break;

            case R.id.btnCalculate:
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);
        String binary, octal, hexadecimal;

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            tilUserInput.setError("Input field is empty");
            return;
        }

        try {
            double decLong = Double.parseDouble(decimal);

            if (decLong <= 0) {
                tilUserInput.setError("Number should be greater than 0");
                return;
            }

            binary = Numericals.decimalToBinary(decLong);
            octal = Numericals.decimalToOctal(decimal);
            hexadecimal = Numericals.decimalToHexadecimal(decimal);

            tvBinary.setText(binary);
            tvOctal.setText(octal);
            tvHexadecimal.setText(hexadecimal);

            Utilities.animateAnswer(layoutAnswerArea,
                    rootView, Utilities.DisplayMode.SHOW);


        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to an integer value");
        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            Utilities.animateAnswer(layoutAnswerArea,
                    rootView, Utilities.DisplayMode.HIDE);
        }
    }
}
