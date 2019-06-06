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
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentAllInOne extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    private TextInputLayout inputLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_allinone, container, false);
        //("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
//        Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFaceName.philosopher_bold);
        inputLayout = rootView.findViewById(R.id.til_user_input);
        inputLayout.setErrorEnabled(true);

        Button btnBack = rootView.findViewById(R.id.button_back);
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);

        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);


        etInput.setOnKeyListener((view, i, keyEvent) -> {
            //take the error message away
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
        }
    }

    private void onCalculate() {
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);

        //answer textviews
        TextView tvBinary = rootView.findViewById(R.id.text_answer_binary);
        TextView tvOctal = rootView.findViewById(R.id.text_answer_octal);
        TextView tvHexadecimal = rootView.findViewById(R.id.text_answer_hexadecimal);


        String binary, octal, hexadecimal;

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            inputLayout.setError("Input field is empty");

            //Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Double decLong = Double.parseDouble(decimal);

            if (decLong <= 0) {
                inputLayout.setError("Number should be greater than 0");
                //Toast.makeText(getContext(), "Number should be greater than 0", Toast.LENGTH_LONG).show();
                return;
            }

            binary = Numericals.DecimalToBinary(decLong);
            octal = Numericals.DecimalToOctal(decimal);
            hexadecimal = Numericals.DecimalToHexadecimal(decimal);

            tvBinary.setText(binary);
            tvOctal.setText(octal);
            tvHexadecimal.setText(hexadecimal);

            Utilities.animateAnswer(rootView.findViewById(R.id.layout_answer_area),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

            //rootView.findViewById(R.id.show_all).setVisibility(isAnswerTruncated ? View.VISIBLE : View.GONE);


        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to an integer value");
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
            Utilities.animateAnswer(rootView.findViewById(R.id.layout_answer_area),
                    rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}
