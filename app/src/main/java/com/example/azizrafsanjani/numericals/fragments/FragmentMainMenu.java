package com.example.azizrafsanjani.numericals.fragments;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.azizrafsanjani.numericals.adapter.TopicAdapter;
import com.example.azizrafsanjani.numericals.model.Topic;
import com.example.azizrafsanjani.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    private View rootView;
    ArrayAdapter<String> adapter;
    boolean itemSelected = false;
    static TextView header;
    private ListView  items;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));
        return rootView;
    }

    private void initControls() {
        items = rootView.findViewById(R.id.listItems);
        items.setOnItemClickListener(this);

        //items.setLayoutManager(new LinearLayoutManager(getContext()));

        header = rootView.findViewById(R.id.text_header);
        header.setVisibility(View.VISIBLE);

        Button computeButton = rootView.findViewById(R.id.buttonCompute);
        Button sourceButton = rootView.findViewById(R.id.buttonSource);

        computeButton.setOnClickListener(this);
        sourceButton.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeuibold.ttf");
        header.setTypeface(typeface);


        populateItemList();
        //performSomeMagic();

    }



    private void populateItemList() {
        ListView listView = rootView.findViewById(R.id.listItems);

        String[] operationList = getOperationList();
        adapter = new ArrayAdapter<>(getContext(), R.layout.list_item, operationList);
        listView.setAdapter(adapter);/*
        RecyclerView recyclerView = rootView.findViewById(R.id.listItems);

        List<Topic> topicList = new ArrayList<>();
        topicList.add(
                new Topic("Decimal to Binary (Fractions)", " Converts fractional numbers to binary")
        );

        topicList.add(
                new Topic("Decimal to Binary (Integers)", " Converts Whole Numbers to Binary")
        );
        topicList.add(
                new Topic("Decimal to Binary (Fractions)", " Converts fractional numbers to binary")
        );

        topicList.add(
                new Topic("Decimal to Binary (Integers)", " Converts Whole Numbers to Binary")
        );
        topicList.add(
                new Topic("Decimal to Binary (Fractions)", " Converts fractional numbers to binary")
        );

        topicList.add(
                new Topic("Decimal to Binary (Integers)", " Converts Whole Numbers to Binary")
        );

        recyclerView.setAdapter(new TopicAdapter( getContext(), topicList));
        */

    }

    private String[] getOperationList() {
        Resources res = getResources();

        return res.getStringArray(R.array.main_menu_legacy);
    }

    private int selectedItem;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.i("TAG", "Item Selected");
        view.setSelected(true);
        selectedItem = position;
        itemSelected = true;
        Button btn = rootView.findViewById(R.id.buttonCompute);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                view.setElevation(2);

            switch (selectedItem) {
                case 8:
                    Fragment fragment = new FragmentCreditsMenu();
                    Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                    break;
                case 7:
                    btn.setText(R.string.proceed);
                    break;
                default:
                    btn.setText(R.string.calculate);
                    break;
            }

        } catch (NullPointerException ex) {

        }
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
                fragment = new FragmentDecToBinFrac();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 1:
                fragment = new FragmentDecToBinInt();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 2:
                fragment = new FragmentDecToBin();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 3:
                fragment = new FragmentBisection();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 4:
                fragment = new FragmentNewtonRaphson();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 5:
                fragment = new FragmentRegulaFalsi();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 6:
                fragment = new FragmentSecante();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 7:
                fragment = new FragmentEquationsMenu();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            default:
                Log.i(Utilities.Log, "No menu item selected");
                break;
        }
    }

    public void onSource() {
        final Dialog dialog = new Dialog(getActivity(), R.style.SourceDialog);
        dialog.setContentView(R.layout.dialog_source_code);
        dialog.setTitle("source code");

        String src = getResources().getString(R.string.jacobi);
        TextView sourceCode = dialog.findViewById(R.id.sourceCode);
        sourceCode.setText(src);

        dialog.show();
    }
}
