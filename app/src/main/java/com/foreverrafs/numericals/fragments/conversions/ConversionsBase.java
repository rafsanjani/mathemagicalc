package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.foreverrafs.numericals.R;
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
    private static final String TAG = "ConversionsBase";
    protected View rootView;

    @BindView(R.id.til_user_input)
    TextInputLayout inputLayout;
    @BindView(R.id.tvHeader)
    TextView txtHeader;
    @BindView(R.id.tvDescription)
    TextView txtDescription;
    @BindView(R.id.text_user_input)
    TextInputEditText etInput;
    @BindView(R.id.tvAnswer)
    TextView tvAnswer;

    private String methodName;
    protected NavController navController = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversions_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        initControls();
    }

    protected void setHeader(String header) {
        txtHeader.setText(header);
    }

    @OnClick(R.id.btnBack)
    void onBackPressed(Button button) {
        goToMainMenu(button);
    }

    //Navigate to the main menu when the button is clicked
    private void goToMainMenu(Button button) {
        NavController controller = Navigation.findNavController(button);
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_left)
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.nav_graph, true)
                .setExitAnim(R.anim.slide_out_right)
                .build();

        controller.navigate(R.id.fragment_main_menu, null, navOptions, null);
    }

    protected void setDescription(String description) {
        txtDescription.setText(description);
    }

    void showErrorMessage(String errorMessage, boolean clearInput) {
        inputLayout.setErrorEnabled(true);
        inputLayout.setError(errorMessage);

        if (clearInput)
            etInput.setText(null);
    }

    private void hideErrorMessage() {
        inputLayout.setErrorEnabled(false);
    }

    private String getMethodName() {
        return methodName;
    }

    void setMethodName(String value) {
        methodName = value;
    }

    void setInputHint(String hint) {
        inputLayout.setHint(hint);
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithm() {
        Utilities.showAlgorithmScreen(getContext(), getMethodName());
    }

    @OnClick(R.id.btnCalculate)
    void onCalculateClicked() {
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
                    Utilities.animateAnswer(rootView.findViewById(R.id.tvAnswer),
                            rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
                }
            }
        });
    }

    void displayAnswer() {
        Utilities.animateAnswer(tvAnswer,
                rootView, Utilities.DisplayMode.SHOW);
    }


    protected abstract void onCalculate();
}
