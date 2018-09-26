package com.foreverrafs.numericals.fragments.conversions;

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
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBinToDec extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;
    TextInputLayout inputLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bin_to_dec, container, false);
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
        Button btnShowAlgo = rootView.findViewById(R.id.buttonShowAlgo);
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);

        Utilities.setTypeFace(tvAnswer, getContext(), Utilities.TypeFaceName.fallingsky);
        Utilities.setTypeFace(rootView.findViewById(R.id.headerText), getContext(), Utilities.TypeFaceName.lobster_regular);

//        rootView.findViewById(R.id.show_all).setOnClickListener(this);

        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //remove the error message from the input layout if any
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
            case R.id.buttonBack:
                Utilities.replaceFragment(new FragmentConversionsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
                break;

            case R.id.buttonCalculate:
                onCalculate();
                break;
            case R.id.buttonShowAlgo:
                onShowAlgorithm();
                break;

        }
    }

    private void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), "");
    }

    private void onCalculate() {
        String decimal = "00";

        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);
        ExpandableTextView tvAnswer = rootView.findViewById(R.id.expand_text_view);

        String binary = etInput.getText().toString();
        if (binary.isEmpty()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Cannot be empty");
            //Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if(Integer.parseInt(binary) <= 0){
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Must be greater than 0");
            //Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (binary.length() >= 24) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Below 24 bits please :)");
            //Toast.makeText(getContext(), "Lets keep it below 24 bits, shall we", Toast.LENGTH_SHORT).show();
        }

        try {
            decimal = String.valueOf(Numericals.BinaryToDecimal(binary));
            tvAnswer.setText(decimal);

            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Log.e(Utilities.Log, ex.getMessage());
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("Binary only please :)");
            //Toast.makeText(getContext(), "Binary only please", Toast.LENGTH_SHORT).show();
            etInput.setText(null);
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
