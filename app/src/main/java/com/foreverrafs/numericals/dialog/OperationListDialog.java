package com.foreverrafs.numericals.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
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

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.adapter.MyListAdapter;
import com.foreverrafs.numericals.fragments.conversions.FragmentBinToDec;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBin;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBinFrac;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBinInt;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToHexadecimal;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToOctal;
import com.foreverrafs.numericals.fragments.ordinary_differential_eqns.FragmentOdeMenu;
import com.foreverrafs.numericals.fragments.roots.FragmentBisection;
import com.foreverrafs.numericals.fragments.roots.FragmentFalsePosition;
import com.foreverrafs.numericals.fragments.roots.FragmentNewtonRaphson;
import com.foreverrafs.numericals.fragments.roots.FragmentSecante;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussSeidel;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussSeidelWithSOR;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianComplete3x3;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianComplete4x4;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianPartial3x3;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianPartial4x4;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentJacobi;
import com.foreverrafs.numericals.utils.Utilities;


public class OperationListDialog extends DialogFragment implements AdapterView.OnItemClickListener {


    private FragmentActivity myContext;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                fragment = new FragmentFalsePosition();
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
                fragment = new FragmentOdeMenu();
                break;
        }

        //Utilities.replaceFragment(myContext, fragment, myContext.getSupportFragmentManager(), R.id.fragmentContainer);

        //startActivity(new Intent(myContext, MainActivity.class));
        Utilities.replaceFragment(fragment, myContext.getSupportFragmentManager(), R.id.fragmentContainer);

        dismiss();
    }
}
