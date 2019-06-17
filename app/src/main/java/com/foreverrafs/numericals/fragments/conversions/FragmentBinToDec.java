package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.NotABinaryException;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBinToDec extends ConversionsBase {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initControls();
    }

    @Override
    protected void initControls() {
        super.initControls();

        setHeader(getString(R.string.bin_to_decimal));
        setDescription(getString(R.string.bin_to_dec_desc));
        setInputHint(getString(R.string.binary_input_hint));
        setMethodName("bintodec");
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
            decimal = String.valueOf(Numericals.BinaryToDecimal(binary));
            tvAnswer.setText(decimal);
            Utilities.animateAnswer(tvAnswer,
                    rootView, Utilities.DisplayMode.SHOW);

        } catch (NotABinaryException ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
            showErrorMessage(ex.getMessage(), true);
        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        }
//        } finally {
//            Utilities.hideKeyboard(etInput);
//        }
    }
}
