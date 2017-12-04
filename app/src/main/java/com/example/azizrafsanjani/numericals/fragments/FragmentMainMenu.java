package com.example.azizrafsanjani.numericals.fragments;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.CustomDialog;
import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.utils.Utilities;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.w3c.dom.Text;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    View rootView;
    ArrayAdapter<String> adapter;
    boolean itemSelected = false;
    static TextView header;

    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));
        return rootView;
    }
    ListView items;
    private void initControls() {
        items = (ListView) rootView.findViewById(R.id.listItems);
        header = (TextView) rootView.findViewById(R.id.text_header);
        header.setVisibility(View.VISIBLE);
        items.setOnItemClickListener(this);
        Button computeButton = (Button) rootView.findViewById(R.id.buttonCompute);
        Button sourceButton = (Button) rootView.findViewById(R.id.buttonSource);
        // linearLayout = rootView.findViewById(R.id.linearLayout);

        computeButton.setOnClickListener(this);
        sourceButton.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeuibold.ttf");
        TextView tv = (TextView) rootView.findViewById(R.id.text_header);
        tv.setTypeface(typeface);


        populateItemList();
        performSomeMagic();

    }

    private void performSomeMagic(){
        int waitTime = 2000;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                items.smoothScrollToPosition(items.getCount() - 1);

            }
        }, waitTime);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                items.smoothScrollToPosition(0);

            }
        }, waitTime + 500);
    }

    private void populateItemList() {
        ListView listView = (ListView) rootView.findViewById(R.id.listItems);

        String[] operationList = getOperationList();
        adapter = new ArrayAdapter<>(getContext(), R.layout.listview_item, operationList);
        listView.setAdapter(adapter);
    }

    private String[] getOperationList() {
        Resources res = getResources();
        String[] opList = {res.getString(R.string.dec_frac_tobinary),
                res.getString(R.string.dec_int_tobinary),
                res.getString(R.string.dec_tobinary),
                res.getString(R.string.rootlocation_bisection),
                res.getString(R.string.rootlocation_newtonraphson),
                res.getString(R.string.rootlocation_falseposition),
                res.getString(R.string.rootlocation_secante),
                res.getString(R.string.system_of_equations),
                res.getString(R.string.credits_group_members)};

        return opList;

    }

    private int selectedItem;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
            case 8:
                fragment = new FragmentCreditsMenu();
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
