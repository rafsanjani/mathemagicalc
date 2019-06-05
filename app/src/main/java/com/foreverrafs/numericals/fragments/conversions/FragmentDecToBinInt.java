package com.foreverrafs.numericals.fragments.conversions;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBinInt extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    TextInputLayout inputLayout;
    String rawBinary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dec_to_bin_int, container, false);
        //("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        //Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FallingSky.otf");

        TextView tvAnswer = rootView.findViewById(R.id.text_answer_binary);
        inputLayout = rootView.findViewById(R.id.til_user_input);
        //tvAnswer.setTypeface(typeface);

        Utilities.setTypeFace(tvAnswer, getContext(), Utilities.TypeFaceName.falling_sky);

        Button btnBack = rootView.findViewById(R.id.button_back);
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);
        EditText etInput = rootView.findViewById(R.id.text_user_input);

        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);


        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                inputLayout.setErrorEnabled(false);
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    onCalculate();
                    return true;
                }
                return false;
            }
        });

        etInput.addTextChangedListener(this);

        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
        rootView.findViewById(R.id.button_show_algo).setOnClickListener(this);
    }

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", "dectobinint");
        startActivity(new Intent(getContext(), ShowAlgorithm.class).putExtras(bundle));
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
                Utilities.replaceFragment(new FragmentConversionsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.button_calculate:
                onCalculate();
                break;

            case R.id.button_show_algo:
                onShowAlgorithm();
                break;
        }
    }

    private void onCalculate() {
        //boolean isAnswerTruncated = false;
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);
        TextView tvAnswer = rootView.findViewById(R.id.text_answer_hexadecimal);

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Input cannot be empty!");
            //Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            int decInt = Integer.parseInt(decimal);

            if (decInt < 1) {
                inputLayout.setErrorEnabled(true);
                inputLayout.setError("Should be equal to or greater than 1!");
                //Toast.makeText(getContext(), "Number should be equal to or greater than 1", Toast.LENGTH_LONG).show();
                return;
            }

            String binary = Numericals.DecimalIntToBinary(decInt);
            Log.i("TAG", "" + binary.length());

            //keep a reference in case user wants to display all
            rawBinary = binary;

            tvAnswer.setText(rawBinary);

            Utilities.animateAnswer(rootView.findViewById(R.id.layout_answer_area),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

            //rootView.findViewById(R.id.show_all).setVisibility(isAnswerTruncated ? View.VISIBLE : View.GONE);


        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to a double value");
            //Toast.makeText(getContext(), "Number entered isn't an integer", Toast.LENGTH_SHORT).show();
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Input isn't an Integer");

            Utilities.animateAnswer(rootView.findViewById(R.id.layout_answer_area),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);

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
                    rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}
