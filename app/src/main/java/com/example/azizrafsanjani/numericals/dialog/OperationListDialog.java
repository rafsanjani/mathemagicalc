package com.example.azizrafsanjani.numericals.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.adapter.MyListAdapter;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentBinToDec;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBin;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBinFrac;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBinInt;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToHexadecimal;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToOctal;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentBisection;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentNewtonRaphson;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentRegulaFalsi;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentSecante;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussSeidel;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussSeidelWithSOR;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussianComplete4x4;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussianPartial4x4;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussianComplete3x3;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussianPartial3x3;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentJacobi;
import com.example.azizrafsanjani.numericals.utils.Utilities;

public class OperationListDialog extends DialogFragment implements AdapterView.OnItemClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize all view items

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private FragmentActivity myContext;
    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_goto_operation, null);
        onInit();
        return rootView;
    }

    private void onInit() {
        String[] operationStringList = getActivity().getResources().getStringArray(R.array.main_menu_legacy);
        ListView operationList = rootView.findViewById(R.id.operationList);
        ArrayAdapter<String> operationListAdapter = new MyListAdapter(getActivity(), R.layout.list_item,
                operationStringList);

        operationList.setAdapter(operationListAdapter);

        operationList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        switch (position + 1) {
            case 1:
                fragment = new FragmentDecToBinFrac();
                break;

            case 2:
                fragment = new FragmentDecToBinInt();
                break;

            case 3:
                fragment = new FragmentDecToBin();
                break;

            case 4:
                fragment = new FragmentBinToDec();
                break;

            case 5:
                fragment = new FragmentDecToHexadecimal();
                break;

            case 6:
                fragment = new FragmentDecToOctal();
                break;

            case 7:
                fragment = new FragmentBisection();
                break;

            case 8:
                fragment = new FragmentNewtonRaphson();
                break;

            case 9:
                fragment = new FragmentRegulaFalsi();
                break;
            case 10:
                fragment = new FragmentSecante();
                break;

            case 11:
                fragment = new FragmentGaussianPartial3x3();
                break;
            case 12:
                fragment = new FragmentGaussianComplete3x3();
                break;
            case 13:
                fragment = new FragmentGaussianPartial4x4();
                break;

            case 14:
                fragment = new FragmentGaussianComplete4x4();
                break;

            case 15:
                fragment = new FragmentGaussSeidel();
                break;

            case 16:
                fragment = new FragmentGaussSeidelWithSOR();
                break;

            case 17:
                fragment = new FragmentJacobi();
                break;
            case 18:
                break;
        }
        if (fragment != null)
            Utilities.replaceFragment(fragment, myContext.getSupportFragmentManager(), R.id.fragmentContainer);

        dismiss();
    }
}
