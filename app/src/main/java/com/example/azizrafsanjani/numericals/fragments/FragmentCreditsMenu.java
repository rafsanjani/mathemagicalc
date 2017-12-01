package com.example.azizrafsanjani.numericals.fragments;


import android.app.Dialog;
import android.content.res.Resources;
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
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentCreditsMenu extends Fragment implements AdapterView.OnItemClickListener {


    View rootView;
    ArrayAdapter<String> adapter;
    boolean itemSelected = false;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_credits_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.group_members));

        return rootView;
    }

    private void initControls() {
        ListView items = (ListView) rootView.findViewById(R.id.listItems);

        items.setOnItemClickListener(this);


        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeuibold.ttf");


        Button backButton = (Button)rootView.findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBack();
            }
        });
        populateItemList();
    }
    private void onBack(){
        Utilities.replaceFragment(this, new FragmentMainMenu(), getFragmentManager(), R.id.fragmentContainer);
    }

    private void populateItemList() {
        ListView listView = (ListView) rootView.findViewById(R.id.listItems);

        String[] operationList = getOperationList();
        adapter = new ArrayAdapter<>(getContext(), R.layout.listview_item, operationList);
        listView.setAdapter(adapter);
    }

    private String[] getOperationList() {
        Resources res = getResources();
        String[] opList = {res.getString(R.string.rafs),
                res.getString(R.string.eugene),
                res.getString(R.string.shari),
                res.getString(R.string.crispin),
                res.getString(R.string.kenneth),
                res.getString(R.string.nsafoa),
                res.getString(R.string.essel),
                res.getString(R.string.adom),
                res.getString(R.string.christy),
                res.getString(R.string.bayarno)};

        return opList;

    }

    private int selectedItem;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setSelected(true);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(2);
            }
        } catch (NullPointerException ex) {

        }

        selectedItem = position;
        itemSelected = true;

        Log.i(Utilities.Log, "Item " + selectedItem + " selected");
    }

}
