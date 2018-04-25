package com.example.azizrafsanjani.numericals.dialog;

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
import com.example.azizrafsanjani.numericals.fragments.FragmentBisection;
import com.example.azizrafsanjani.numericals.fragments.FragmentDecToBin;
import com.example.azizrafsanjani.numericals.fragments.FragmentDecToBinFrac;
import com.example.azizrafsanjani.numericals.fragments.FragmentDecToBinInt;
import com.example.azizrafsanjani.numericals.fragments.FragmentNewtonRaphson;
import com.example.azizrafsanjani.numericals.utils.Utilities;

public class OperationListDialog extends DialogFragment implements ListView.OnItemSelectedListener, AdapterView.OnItemClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize all view items

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
        ArrayAdapter<String> operationListAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item,
                operationStringList);

        operationList.setAdapter(operationListAdapter);

        operationList.setOnItemClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Utilities.replaceFragment(new FragmentDecToBinFrac(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;


            case 2:

                break;

            case 3:

                break;

            case 4:

                break;


            case 5:
                break;

            case 6:

                break;


            case 7:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
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

            case 6:
                Utilities.replaceFragment(new FragmentBisection(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;

            case 7:
                Utilities.replaceFragment(new FragmentNewtonRaphson(), myContext.getSupportFragmentManager(), R.id.fragmentContainer);
                break;
        }
        dismiss();
    }
}
