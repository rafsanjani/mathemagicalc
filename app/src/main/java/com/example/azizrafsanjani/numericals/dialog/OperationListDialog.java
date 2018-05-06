package com.example.azizrafsanjani.numericals.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.adapter.MyListAdapter;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentBisection;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBin;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBinFrac;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentDecToBinInt;
import com.example.azizrafsanjani.numericals.fragments.sys_of_equations.FragmentGaussianComplete3x3;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentNewtonRaphson;
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
        switch (position+1) {
            case 1:
                Utilities.replaceFragment(new FragmentDecToBinFrac(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case 2:
                Utilities.replaceFragment(new FragmentDecToBinInt(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;


            case 3:
                Utilities.replaceFragment(new FragmentDecToBin(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;


            case 4:

                break;

            case 5:

                break;

            case 7:
                Utilities.replaceFragment(new FragmentBisection(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case 8:
                Utilities.replaceFragment(new FragmentNewtonRaphson(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;


            case 12:
                Utilities.replaceFragment(new FragmentGaussianComplete3x3(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;
        }
        dismiss();
    }
}
