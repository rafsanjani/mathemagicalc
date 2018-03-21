package com.example.azizrafsanjani.numericals.fragments;


import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.CustomDialog;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentEquationsMenu extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    View rootView;
    ArrayAdapter<String> adapter;
    boolean itemSelected = false;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_equations_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo("Numerial Methods", "System of Equations");

        return rootView;
    }

    private void initControls() {
        ListView items = rootView.findViewById(R.id.listItems);

        items.setOnItemClickListener(this);
        Button computeButton = rootView.findViewById(R.id.buttonCompute);
        Button sourceButton = rootView.findViewById(R.id.buttonSource);
        // linearLayout = rootView.findViewById(R.id.linearLayout);

        computeButton.setOnClickListener(this);
        sourceButton.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeuibold.ttf");
        TextView tv = rootView.findViewById(R.id.text_header);
        tv.setTypeface(typeface);


        populateItemList();

    }

    private void populateItemList() {
        ListView listView = rootView.findViewById(R.id.listItems);

        String[] operationList = getOperationList();
        adapter = new ArrayAdapter<>(getContext(), R.layout.list_item, operationList);
        listView.setAdapter(adapter);
    }

    private String[] getOperationList() {
        String[] opList = {
                "1. Gaussian Elim. Partial Piv. ( 3 x 3) ",
                "2. Gaussian Elim. Complete Piv. (3 x 3)",
                "3. Jacobi's Iterative Method",
                "4. Gauss Seidel's Iterative Method",
                "5. Gauss Seidel's Method with SOR"
        };

        return opList;
    }

    private int selectedItem;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setSelected(true);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(20);
            }
        } catch (NullPointerException ex) {

        }

        selectedItem = position;
        itemSelected = true;

        Log.i(Utilities.Log, "Item " + selectedItem + " selected");
    }

    @Override
    public void onClick(View view) {
        if (!itemSelected) {
            Log.i(Utilities.Log, "No item selected in the listview");
            return;
        }

        switch (view.getId()) {
            case R.id.buttonCompute:
                onCompute();
                break;

            case R.id.buttonSource:
                onSource();
        }
    }

    public void onCompute() {
        Fragment fragment;

        switch (selectedItem) {
            case 0:
                fragment = new FragmentGaussianPartial3x3();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 1:
                fragment = new FragmentGaussianComplete3x3();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 2:
                fragment = new FragmentJacobi();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 3:
                fragment = new FragmentGaussSeidel();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 4:
                fragment = new FragmentGaussSeidelWithSOR();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;


        }
    }

    public void onSource() {
        CustomDialog dialog = new CustomDialog();

        String msg = "";
        dialog.setMessage(msg);
        dialog.show(getActivity().getFragmentManager(), "NoticeDialogFragment");
    }
}
