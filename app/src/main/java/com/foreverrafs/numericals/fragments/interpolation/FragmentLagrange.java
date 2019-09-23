package com.foreverrafs.numericals.fragments.interpolation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentLagrange extends Fragment {
    private static final String TAG = "FragmentLagrange";

    @BindView(R.id.etKnownX)
    EditText etKnownX;

    @BindView(R.id.tRowX)
    TableRow tableRowX;

    @BindView(R.id.tRowY)
    TableRow tableRowY;

    @BindView(R.id.tvUnknownY)
    TextView tvUnknownY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interpolation_lagrange, container, false);
        ButterKnife.bind(this, view);
        initControls();

        return view;
    }

    public void initControls() {
        etKnownX.addTextChangedListener(new TextWatcher() {
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
                    Log.d(TAG, "afterTextChanged: " + editable.toString());
                    if (editable.length() != 0)
                        onCalculate(Double.parseDouble(editable.toString()));
                    textChanged = false;
                }
            }
        });
    }

    @OnClick(R.id.btnBack)
    void onBackButton() {
        Activity parentActivity = getActivity();
        if (parentActivity != null)
            parentActivity.finish();
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
                    double value = Double.valueOf(((EditText) tableRowX.getChildAt(x)).getText().toString());
                    listOfX.add(value);
                } catch (NumberFormatException exception) {

                }
            }
        }

        for (int y = 0; y < length; y++) {
            if (tableRowY.getChildAt(y) instanceof EditText) {
                try {
                    double value = Double.valueOf(((EditText) tableRowY.getChildAt(y)).getText().toString());
                    listOfY.add(value);
                } catch (NumberFormatException exception) {

                }
            }
        }

        double answer = Numericals.interpolate(Utilities.toPrimitiveDouble(listOfX),
                Utilities.toPrimitiveDouble(listOfY), knownX);

        tvUnknownY.setText(String.valueOf(answer));
    }


}
