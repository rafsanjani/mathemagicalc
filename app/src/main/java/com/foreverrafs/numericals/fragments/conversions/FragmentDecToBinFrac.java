package com.foreverrafs.numericals.fragments.conversions;

import android.util.Log;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBinFrac extends ConversionsBase {
    private static final String TAG = "FragmentDecToBinFrac";

    private String rawBinary;


    @Override
    protected void initControls() {
        super.initControls();

        setHeader(getString(R.string.dec_to_bin_fractions));
        setDescription(getString(R.string.dec_to_bin_frac_desc));
        setInputHint(getString(R.string.decimal_frac_input_hint));
        setMethodName("dectobinfrac");
    }


    @Override
    protected void onCalculate() {

        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false);
            return;
        }

        try {
            double decDouble = Double.parseDouble(decimal);

            if (decDouble >= 1) {
                showErrorMessage("Must be less than 1", false);
                return;
            }
            if (decDouble == 0) {
                showErrorMessage("Cannot be zero", false);
                return;
            }

            //keep a reference in case user wants to display all
            rawBinary = Numericals.decimalFractionToBinary(decDouble);

            tvAnswer.setText(rawBinary);

            displayAnswer();

        } catch (NumberFormatException ex) {
            Log.e(TAG, "cannot parse " + decimal + " to a double value");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
