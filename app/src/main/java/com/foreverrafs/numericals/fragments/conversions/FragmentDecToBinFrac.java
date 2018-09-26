package com.foreverrafs.numericals.fragments.conversions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBinFrac extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    TextInputLayout inputLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dec_to_bin_frac, container, false);
        MainActivity.setToolBarInfo("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        // Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FallingSky.otf");
        TextView tvAnswer = rootView.findViewById(R.id.expandable_text);
        inputLayout = rootView.findViewById(R.id.til_user_input);
        //tvAnswer.setTypeface(typeface);

        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);

        Utilities.setTypeFace(tvAnswer, getContext(), Utilities.TypeFaceName.fallingsky);
        Utilities.setTypeFace(rootView.findViewById(R.id.headerText), getContext(), Utilities.TypeFaceName.lobster_regular);

//        rootView.findViewById(R.id.show_all).setOnClickListener(this);

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
        rootView.findViewById(R.id.buttonShowAlgo).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentConversionsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                onCalculate();
                break;

            case R.id.buttonShowAlgo:
                onShowAlgorithm();
        }
    }

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", "dectobinfrac");

        startActivity(new Intent(getContext(), ShowAlgorithm.class).putExtras(bundle));
    }

    String rawBinary;

    private void onCalculate() {
        EditText etInput = rootView.findViewById(R.id.text_user_input);
        ExpandableTextView tvAnswer = rootView.findViewById(R.id.expand_text_view);

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Input cannot be empty!");
            //Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            double decDouble = Double.parseDouble(decimal);

            if (decDouble >= 1) {
                inputLayout.setErrorEnabled(true);
                inputLayout.setError("Cannot be greater than 1!");
                //Toast.makeText(getContext(), "Number should be less than 1", Toast.LENGTH_LONG).show();
                return;
            }
            if (decDouble == 0) {
                inputLayout.setErrorEnabled(true);
                inputLayout.setError("Cannot be zero");
                return;
            }

            String binary = Numericals.DecimalFractionToBinary(decDouble);

            //keep a reference in case user wants to display all
            rawBinary = binary;


            tvAnswer.setText(rawBinary);

            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Log.e(Utilities.Log, "cannot parse " + decimal + " to a double value");
        } catch (Exception ex) {
            Log.e(Utilities.Log, ex.getMessage());
        } finally {
            MainActivity.hideKeyboard(etInput);
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
            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        }
    }
}
