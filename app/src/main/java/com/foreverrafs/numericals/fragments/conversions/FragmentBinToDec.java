package com.foreverrafs.numericals.fragments.conversions;

import android.util.Log;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.core.exceptions.NotABinaryException;
import com.foreverrafs.numericals.R;

import butterknife.OnClick;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBinToDec extends ConversionsBase {

    private static final String TAG = "FragmentBinToDec";

    @Override
    protected void initControls() {
        super.initControls();
        setHeader(getString(R.string.bin_to_decimal));
        setDescription(getString(R.string.bin_to_dec_desc));
        setInputHint(getString(R.string.binary_input_hint));
        setMethodName("bintodec");
    }

    @OnClick(R.id.btnShowAlgo)
    void showAlgo() {
        parentActivity.showAlgorithm(navController, "bintodec");
    }

    @Override
    protected void onCalculate() {
        String decimal = "00";

        String binary = etInput.getText().toString();
        if (binary.isEmpty()) {
            showErrorMessage("Cannot be empty", false);
            return;
        }


        if (Double.parseDouble(binary) <= 0) {
            showErrorMessage("Must be greater than 0!", false);
            return;
        }

        if (binary.length() >= 24) {
            showErrorMessage("Below 24 bits please (::)", false);
        }

        try {
            decimal = String.valueOf(Numericals.binaryToDecimal(binary));
            tvAnswer.setText(decimal);

            displayAnswer();

        } catch (NotABinaryException ex) {
            Log.e(TAG, ex.getMessage());
            showErrorMessage(ex.getMessage(), true);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
