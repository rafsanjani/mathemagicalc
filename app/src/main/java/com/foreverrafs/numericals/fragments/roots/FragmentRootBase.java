package com.foreverrafs.numericals.fragments.roots;


import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

//All fragments which will solve a location of root problem must extend from this Fragment and assign
//appropriate values to the protected field members else a NullPointerException will be thrown when
//any of the protected methods attempts to execute in the subclasses
public abstract class FragmentRootBase extends Fragment {
    protected List<LocationOfRootResult> roots = null;
    protected View rootView;
    protected ViewGroup parentContainer;
    private View.OnKeyListener myKeyListener;
    private Button btnCalculate;

    protected void onEquationChanged() {
        TextView tvAnswer = rootView.findViewById(R.id.textview_answer);
        btnCalculate = rootView.findViewById(R.id.button_calculate);
        btnCalculate.setText(getResources().getString(R.string.calculate));
        Utilities.animateAnswer(tvAnswer, parentContainer, Utilities.DisplayMode.HIDE);
    }

    protected void registerOnKeyListener(final TextInputLayout... inputLayouts) throws RuntimeException {
        if (inputLayouts.length == 0) {
            Log.e(Utilities.LOG_TAG, "At least one inputLayout must be supplied to registerOnKeyListener");
            return;
        }
        myKeyListener = (view, i, keyEvent) -> {
            onEquationChanged();
            for (TextInputLayout inputLayout : inputLayouts) {
                inputLayout.setErrorEnabled(false);
            }

            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return false;

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                onCalculate(btnCalculate.getText().toString());
                return true;
            }
            return false;
        };

        for (TextInputLayout inputLayout : inputLayouts) {
            inputLayout.getEditText().setOnKeyListener(myKeyListener);
        }
    }

    protected void unRegisterOnKeyListeners(@NonNull final TextInputLayout... inputLayouts) throws RuntimeException {
        if (myKeyListener == null) {
            throw new RuntimeException("You must call register before calling unregister");
        }
        for (TextInputLayout inputLayout : inputLayouts) {
            inputLayout.getEditText().setOnKeyListener(null);
        }
    }

    protected boolean checkForEmptyInput(@NonNull final TextInputLayout... inputLayouts) {
        if (inputLayouts.length == 0) {
            Log.e(Utilities.LOG_TAG, "At least one inputLayout must be supplied to registerOnKeyListener");
            return false;
        }

        boolean validated = true;
        for (TextInputLayout inputLayout : inputLayouts) {
            if (inputLayout.getEditText().getText().toString().isEmpty()) {
                inputLayout.setErrorEnabled(true);
                switch (inputLayout.getId()) {
                    case R.id.til_user_input:
                        inputLayout.setError("Cannot be empty!");
                        break;
                    default:
                        inputLayout.setError("???");
                        break;

                }
                validated = false;
            }
        }

        return validated;
    }

    protected abstract void onCalculate(@NonNull final String buttonText);
}
