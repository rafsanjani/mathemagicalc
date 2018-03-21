package com.example.azizrafsanjani.numericals.fragments;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentCreditsMenu extends Fragment implements AdapterView.OnItemClickListener {


    View rootView;
    ArrayAdapter<String> adapter;
    ListView items;
    boolean itemSelected = false;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_credits_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.group_members));

        return rootView;
    }

    private void initControls() {
        items = rootView.findViewById(R.id.listItems);

        items.setOnItemClickListener(this);


        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeuibold.ttf");


        Button backButton = rootView.findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBack();
            }
        });
        populateItemList();
        performSomeMagic();
    }

    private void performSomeMagic(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                 items.smoothScrollToPosition(items.getCount() - 1);

            }
        }, 2000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                items.smoothScrollToPosition(0);

            }
        }, 4000);
    }
    private void onBack(){
        Utilities.replaceFragment(this, new FragmentMainMenu(), getFragmentManager(), R.id.fragmentContainer);
    }

    private void populateItemList() {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setSelected(true);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(2);
            }
        } catch (NullPointerException ex) {

        }

        int selectedItem = position;
        itemSelected = true;

        Log.i(Utilities.Log, "Item " + selectedItem + " selected");
    }

}
