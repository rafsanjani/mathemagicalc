package com.foreverrafs.numericals.fragments.conversions;

import android.util.Log;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBin extends ConversionsBase {
    private static final String TAG = "FragmentDecToBin";

    @Override
    protected void initControls() {
        super.initControls();
        setHeader(getString(R.string.dec_to_bin_any_number));
        setDescription(getString(R.string.decimal_to_bin_desc));
        setInputHint(getString(R.string.enter_decimal));
        setMethodName("dectobin");
    }



    @Override
    protected void onCalculate() {
        String decimal = etInput.getText().toString();
        if (decimal.isEmpty()) {
            showErrorMessage(getString(R.string.errormsg_empty_input), false);
            return;
        }

        try {
            double decLong = Double.parseDouble(decimal);

            if (decLong <= 0) {
                showErrorMessage(getString(R.string.errormsg_greater_than_zero), false);
                return;
            }

            String rawBinary = Numericals.decimalToBinary(decLong);

            tvAnswer.setText(rawBinary);

            displayAnswer();

        } catch (NumberFormatException ex) {
            Log.e(TAG, "cannot parse " + decimal + " to an integer value");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


}
