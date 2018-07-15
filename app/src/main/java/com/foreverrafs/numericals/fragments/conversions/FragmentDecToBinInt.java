package com.foreverrafs.numericals.fragments.conversions;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.activities.ShowAlgorithm;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBinInt extends Fragment implements View.OnClickListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dec_to_bin_int, container, false);
        MainActivity.setToolBarInfo("Decimal Calculator", "Convert decimals to binary");

        initControls();
        return rootView;
    }

    private void initControls() {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/FallingSky.otf");
        TextView tvAnswer = rootView.findViewById(R.id.expandable_text);
        tvAnswer.setTypeface(typeface);

        Button btnBack = rootView.findViewById(R.id.buttonBack);
        Button btnCalculate = rootView.findViewById(R.id.buttonCalculate);
        EditText etInput = rootView.findViewById(R.id.text_user_input);

        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());


        etInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
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

    private void onShowAlgorithm() {
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name","dectobinwhole");

       // Intent intent = new Intent(getContext(), ShowAlgorithm.class);
        //intent.putExtras(bundle);
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

    String rawBinary;

    private void onCalculate() {
        boolean isAnswerTruncated = false;
        EditText etInput = rootView.findViewById(R.id.text_user_input);
        ExpandableTextView tvAnswer = rootView.findViewById(R.id.expand_text_view);

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            Toast.makeText(getContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            int decInt = Integer.parseInt(decimal);

            if (decInt < 1) {
                Toast.makeText(getContext(), "Number should be equal to or greater than 1", Toast.LENGTH_LONG).show();
                return;
            }

            String binary = Numericals.DecimalIntToBinary(decInt);
            Log.i("TAG", "" + binary.length());

            //keep a reference in case user wants to display all
            rawBinary = binary;

           /* if (binary.length() >= 20) {
                binary = binary.substring(0, 20);
                Toast.makeText(getContext(), "Answer truncated to 20 significant figures", Toast.LENGTH_LONG).show();
                isAnswerTruncated = true;
            }*/

            tvAnswer.setText(rawBinary);

            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);

            //rootView.findViewById(R.id.show_all).setVisibility(isAnswerTruncated ? View.VISIBLE : View.GONE);


        } catch (NumberFormatException ex) {
            Log.i(Utilities.Log, "cannot parse " + decimal + " to a double value");
            Toast.makeText(getContext(), "Number entered isn't an integer", Toast.LENGTH_SHORT).show();

            Utilities.animateAnswer(rootView.findViewById(R.id.answerArea),
                    (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);

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
