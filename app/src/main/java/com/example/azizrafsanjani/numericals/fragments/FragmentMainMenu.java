package com.example.azizrafsanjani.numericals.fragments;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.fragments.conversions.FragmentConversionsMenu;
import com.example.azizrafsanjani.numericals.fragments.roots.FragmentLocationOfRootsMenu;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    private View rootView;
    ArrayAdapter<String> adapter;
    boolean itemSelected = false;
    static TextView header;
    private ListView items;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();
        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initControls() {
        //items = rootView.findViewById(R.id.listItems);
        //items.setOnItemClickListener(this);

        //items.setLayoutManager(new LinearLayoutManager(getContext()));

        header = rootView.findViewById(R.id.Header);
        header.setVisibility(View.VISIBLE);

        //click listeners for all the buttons from the main menu
        rootView.findViewById(R.id.btn_number_conversion).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loc_of_roots).setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lobster-Regular.ttf");
        header.setTypeface(typeface);
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
        //Button btn = rootView.findViewById(R.id.buttonCompute);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                view.setElevation(2);

            /*switch (selectedItem) {
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
            }*/

        } catch (NullPointerException ex) {

        }
        Log.i(Utilities.Log, "Item " + selectedItem + " selected");
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btn_number_conversion:
                fragment = new FragmentConversionsMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;

            case R.id.btn_loc_of_roots:
                fragment = new FragmentLocationOfRootsMenu();
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, false);
                break;
        }

    }

    public void onCompute() {
        Fragment fragment;

        switch (selectedItem) {
            /*case 0:
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
                break;*/
        }
    }

    public void onSource() {
        /*final Dialog dialog = new Dialog(getActivity(), R.style.SourceDialog);
        dialog.setContentView(R.layout.dialog_source_code);
        dialog.setTitle("source code");

        String src = getResources().getString(R.string.jacobi);
        TextView sourceCode = dialog.findViewById(R.id.sourceCode);
        sourceCode.setText(src);

        dialog.show();*/
    }
}
