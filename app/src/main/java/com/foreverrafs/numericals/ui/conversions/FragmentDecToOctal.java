package com.foreverrafs.numericals.ui.conversions;

import android.util.Log;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
//import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToOctal extends ConversionsBase {
    private static final String TAG = "FragmentDecToOctal";

    @Override
    protected void initControls() {
        super.initControls();

        setHeader(getString(R.string.dec_to_octal));
        setDescription(getString(R.string.decimal_to_octal_desc));
        setInputHint(getString(R.string.decimal_int_input_hint));
        setMethodName("dectooctal");
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

            String octalDecimal = Numericals.decimalToOctal(decimal);

            tvAnswer.setText(octalDecimal);

            displayAnswer();

        } catch (NumberFormatException ex) {
            Log.e(TAG, "cannot parse " + decimal + " to an integer value");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
