package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.custom_views.RafsTextView;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rafsanjani on 6/16/2019
 */
public abstract class ConversionsBase extends Fragment {


    protected View rootView;

    @BindView(R.id.til_user_input)
    TextInputLayout inputLayout;
    @BindView(R.id.text_header)
    RafsTextView txtHeader;
    @BindView(R.id.text_description)
    TextView txtDescription;
    @BindView(R.id.text_user_input)
    TextInputEditText etInput;
    @BindView(R.id.text_answer)
    TextView tvAnswer;
    private String methodName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversions_layout, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected void setHeader(String header) {
        txtHeader.setText(header);
    }

    protected void setDescription(String description) {
        txtDescription.setText(description);
    }

    protected void showErrorMessage(String errorMessage, boolean clearInput) {
        inputLayout.setErrorEnabled(true);
        inputLayout.setError(errorMessage);

        if (clearInput)
            etInput.setText(null);
    }

    protected void hideErrorMessage() {
        inputLayout.setErrorEnabled(false);
    }

    private String getMethodName() {
        return methodName;
    }

    protected void setMethodName(String value) {
        methodName = value;
    }

    protected void setInputHint(String hint) {
        inputLayout.setHint(hint);
    }

    @OnClick(R.id.button_show_algo)
    public void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), getMethodName());
    }

    @OnClick(R.id.button_back)
    void onBackPressed() {

    }

    @OnClick(R.id.button_calculate)
    public void onCalculateClicked() {
        onCalculate();
    }


    protected void initControls() {
        etInput.setOnKeyListener((view, i, keyEvent) -> {
            //remove the error message from the input layout if any
            hideErrorMessage();
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                onCalculate();
                return true;
            }
            return false;
        });


        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    Utilities.animateAnswer(rootView.findViewById(R.id.text_answer),
                            rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
                }
            }
        });

    }


    protected abstract void onCalculate();
}
