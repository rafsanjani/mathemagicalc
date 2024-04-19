package com.foreverrafs.numericals.ui.interpolation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.databinding.InterpolationLagrangeBinding;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentLagrange extends Fragment {
    private static final String TAG = "FragmentLagrange";

    EditText etKnownX;

    TableRow tableRowX;

    TableRow tableRowY;

    TextView tvUnknownY;

    private TextWatcher mWatcher = null;

    InterpolationLagrangeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InterpolationLagrangeBinding.inflate(inflater);
        tvUnknownY = binding.tvUnknownY;
        tableRowX = binding.tRowX;
        tableRowY = binding.tRowY;
        etKnownX = binding.etKnownX;

        initControls();

        return binding.getRoot();
    }

    public void initControls() {
        mWatcher = new TextWatcher() {
            boolean textChanged = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textChanged) {
                    //do some work here
                    Timber.d("afterTextChanged: %s", editable.toString());
                    if (editable.length() != 0)
                        onCalculate(Double.parseDouble(editable.toString()));
                    textChanged = false;
                }
            }
        };

        etKnownX.addTextChangedListener(mWatcher);
        binding.btnBackToMainMenu.setOnClickListener(v -> onBackButton());
    }


    void onBackButton() {
        Activity parentActivity = getActivity();
        if (parentActivity != null)
            ((MainActivity) parentActivity).goToMainMenu();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //lets initialize all our views, shall we?
        initControls();
    }

    private void onCalculate(double knownX) {
        List<Double> listOfX = new ArrayList<>();
        List<Double> listOfY = new ArrayList<>();

        int length = tableRowX.getChildCount();

        for (int x = 0; x < length; x++) {
            if (tableRowX.getChildAt(x) instanceof EditText) {
                try {
                    double value = Double.parseDouble(((EditText) tableRowX.getChildAt(x)).getText().toString());
                    listOfX.add(value);
                } catch (NumberFormatException ignored) {

                }
            }
        }

        for (int y = 0; y < length; y++) {
            if (tableRowY.getChildAt(y) instanceof EditText) {
                try {
                    double value = Double.parseDouble(((EditText) tableRowY.getChildAt(y)).getText().toString());
                    listOfY.add(value);
                } catch (NumberFormatException ignored) {

                }
            }
        }

        double answer = Numericals.interpolate(Utilities.toPrimitiveDouble(listOfX),
                Utilities.toPrimitiveDouble(listOfY), knownX);

        tvUnknownY.setText(String.valueOf(answer));
    }
}
