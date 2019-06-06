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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainMenuActivity;
import com.foreverrafs.numericals.core.NotABinaryException;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBinToDec extends Fragment implements View.OnClickListener, TextWatcher {

    private View rootView;
    private TextInputLayout inputLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bin_to_dec, container, false);
        //("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        TextView tvAnswer = rootView.findViewById(R.id.text_answer_binary);
        inputLayout = rootView.findViewById(R.id.til_user_input);

        Button btnBack = rootView.findViewById(R.id.button_back);
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        Button btnShowAlgo = rootView.findViewById(R.id.button_show_algo);
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);

        Utilities.setTypeFace(tvAnswer, getContext(), Utilities.TypeFaceName.falling_sky);


        etInput.setOnKeyListener((view, i, keyEvent) -> {
            //remove the error message from the input layout if any
            inputLayout.setErrorEnabled(false);
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                onCalculate();
                return true;
            }
            return false;
        });

        etInput.addTextChangedListener(this);

        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
        btnShowAlgo.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                //Utilities.replaceFragment(new FragmentConversionsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.button_calculate:
                onCalculate();
                break;
            case R.id.button_show_algo:
                onShowAlgorithm();
                break;

        }
    }

    private void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), "bintodec");
    }

    private void onCalculate() {
        String decimal = "00";

        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);
        TextView tvAnswer = rootView.findViewById(R.id.text_answer_binary);

        String binary = etInput.getText().toString();
        if (binary.isEmpty()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Cannot be empty!");
            return;
        }


        if (Double.parseDouble(binary) <= 0) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Must be greater than 0!");
            return;
        }

        if (binary.length() >= 24) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Below 24 bits please (::)");
        }

        try {
            decimal = String.valueOf(Numericals.BinaryToDecimal(binary));
            tvAnswer.setText(decimal);
            Utilities.animateAnswer(rootView.findViewById(R.id.layout_answer_area),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

        } catch (NotABinaryException ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(ex.getMessage());
            etInput.setText(null);
        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        } finally {
            Utilities.hideKeyboard(etInput);
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
            Utilities.animateAnswer(rootView.findViewById(R.id.text_answer_binary),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}
