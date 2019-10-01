package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBinInt extends ConversionsBase {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initControls();
    }

    @Override
    protected void initControls() {
        super.initControls();

        setHeader(getString(R.string.dec_to_bin_integers));
        setDescription(getString(R.string.dec_to_bin_int_desc));
        setInputHint(getString(R.string.decimal_int_input_hint));
        setMethodName("dectobinint");
    }

    @Override
    protected void onCalculate() {
        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false);
            return;
        }

        try {
            int decInt = Integer.parseInt(decimal);

            if (decInt < 1) {
                showErrorMessage("Should be equal to or greater than 1", false);
                return;
            }

            String binary = Numericals.decimalIntToBinary(decInt);
            Log.i(Utilities.LOG_TAG, "" + binary.length());

            tvAnswer.setText(binary);

            Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.SHOW);


        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to a double value");
            showErrorMessage("Input isn't an integer", false);
            displayAnswer();

        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        }
    }
}
