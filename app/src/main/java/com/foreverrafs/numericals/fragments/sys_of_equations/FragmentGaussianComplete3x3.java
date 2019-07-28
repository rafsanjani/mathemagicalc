package com.foreverrafs.numericals.fragments.sys_of_equations;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainMenuActivity;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentGaussianComplete3x3 extends Fragment implements View.OnClickListener, View.OnKeyListener, TextWatcher {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gaussian_complete3x3, container, false);
        //("System of Equations", "Gaussian Elimination (Partial Pivoting)");

        initControls();
        return rootView;
    }

    private void initControls() {

        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);

        Button btnBack = rootView.findViewById(R.id.button_back);
        Button btnCalculate = rootView.findViewById(R.id.button_calculate);


        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.button_back:
//                Utilities.replaceFragment(new FragmentSystemOfEquationsMenu(), getFragmentManager(), R.id.fragmentContainer, true);
//                break;

            case R.id.button_calculate:
                Log.i(Utilities.LOG_TAG, "solving the system using gaussian with partial pivoting");
                onCalculate();
                break;
        }
    }

    private void onCalculate() {
        if (getMatrices()) {
            // LinearLayout solutionMatrix = rootView.findViewById(R.id.solutionMatrix);
            //LinearLayout solutionMatrix2 = rootView.findViewById(R.id.solutionMatrix2);
            Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(rootView.findViewById(R.id.solHeader1), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
            Utilities.animateAnswer(rootView.findViewById(R.id.solHeader2), (ViewGroup) rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.SHOW);
        } else {
            Toast.makeText(getContext(), "Error with input", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean getMatrices() {
        EditText[][] etA = new EditText[3][3];
        double[][] a = new double[3][3];

        EditText[] etB = new EditText[3];
        double b[] = new double[3];

        EditText[] etX = new EditText[3];

        etA[0][0] = rootView.findViewById(R.id.a11);
        etA[0][1] = rootView.findViewById(R.id.a12);
        etA[0][2] = rootView.findViewById(R.id.a13);
        etA[1][0] = rootView.findViewById(R.id.a21);
        etA[1][1] = rootView.findViewById(R.id.a22);
        etA[1][2] = rootView.findViewById(R.id.a23);
        etA[2][0] = rootView.findViewById(R.id.a31);
        etA[2][1] = rootView.findViewById(R.id.a32);
        etA[2][2] = rootView.findViewById(R.id.a33);

        etB[0] = rootView.findViewById(R.id.b1);
        etB[1] = rootView.findViewById(R.id.b2);
        etB[2] = rootView.findViewById(R.id.b3);

        etX[0] = rootView.findViewById(R.id.x1);


        Utilities.hideKeyboard(etA[0][0]);

        for (int i = 0; i < etA.length; i++) {
            for (int j = 0; j < etA.length; j++) {
                etA[i][j].addTextChangedListener(this);
                try {
                    a[i][j] = Double.parseDouble(etA[i][j].getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
            try {
                etB[i].addTextChangedListener(this);
                b[i] = Double.parseDouble(etB[i].getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        //get the solution matrix
        double[] solution = Numericals.GaussianWithCompletePivoting(a, b);

        //our previous matrices have been mutated so we can represent them on the textviews
        TextView[][] tvA = new TextView[3][3];
        tvA[0][0] = rootView.findViewById(R.id.sa11);
        tvA[0][1] = rootView.findViewById(R.id.sa12);
        tvA[0][2] = rootView.findViewById(R.id.sa13);
        tvA[1][0] = rootView.findViewById(R.id.sa21);
        tvA[1][1] = rootView.findViewById(R.id.sa22);
        tvA[1][2] = rootView.findViewById(R.id.sa23);
        tvA[2][0] = rootView.findViewById(R.id.sa31);
        tvA[2][1] = rootView.findViewById(R.id.sa32);
        tvA[2][2] = rootView.findViewById(R.id.sa33);


        TextView[] tvX = new TextView[3];
        TextView[] tvB = new TextView[3];

        tvB[0] = rootView.findViewById(R.id.sab1);
        tvB[1] = rootView.findViewById(R.id.sab2);
        tvB[2] = rootView.findViewById(R.id.sab3);

        tvX[0] = rootView.findViewById(R.id.sax1);
        tvX[1] = rootView.findViewById(R.id.sax2);
        tvX[2] = rootView.findViewById(R.id.sax3);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                tvA[i][j].setText(String.valueOf(a[i][j]));
            }
            tvX[i].setText(String.valueOf(solution[i]));
            tvB[i].setText(String.valueOf(b[i]));
        }

        return true;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix), rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solutionMatrix2), rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader1), rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
        Utilities.animateAnswer(rootView.findViewById(R.id.solHeader2), rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
