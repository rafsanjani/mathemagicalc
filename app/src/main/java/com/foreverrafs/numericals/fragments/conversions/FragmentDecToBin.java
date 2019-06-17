package com.foreverrafs.numericals.fragments.conversions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentDecToBin extends ConversionsBase {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initControls();
    }

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
        TextInputEditText etInput = rootView.findViewById(R.id.text_user_input);
        TextView tvAnswer = rootView.findViewById(R.id.text_answer);

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

            String rawBinary = Numericals.DecimalToBinary(decLong);

            tvAnswer.setText(rawBinary);

            Utilities.animateAnswer(tvAnswer,
                    rootView, Utilities.DisplayMode.SHOW);

        } catch (NumberFormatException ex) {
            Log.e(Utilities.LOG_TAG, "cannot parse " + decimal + " to an integer value");
        } catch (Exception ex) {
            Log.e(Utilities.LOG_TAG, ex.getMessage());
        }// finally {
           // Utilities.hideKeyboard(etInput);
//        }
    }


}
