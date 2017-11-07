package com.example.azizrafsanjani.numericals.fragments;


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


import com.example.azizrafsanjani.numericals.activities.MainActivity;
import com.example.azizrafsanjani.numericals.utils.CustomDialog;
import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.utils.Utilities;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentMainMenu extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    View rootView;
    ArrayAdapter<String> adapter;
    ViewGroup linearLayout;
    boolean itemSelected = false;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initControls();

        MainActivity.setToolBarInfo(getResources().getString(R.string.app_name), getResources().getString(R.string.app_description));

        return rootView;
    }

    private void initControls(){
        ListView items = (ListView)rootView.findViewById(R.id.listItems);

        items.setOnItemClickListener(this);
        Button computeButton = (Button)rootView.findViewById(R.id.buttonCompute);
        Button sourceButton = (Button)rootView.findViewById(R.id.buttonSource);
       // linearLayout = rootView.findViewById(R.id.linearLayout);

        computeButton.setOnClickListener(this);
        sourceButton.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/darkandblack.otf");
        TextView tv = (TextView)rootView.findViewById(R.id.text_header);
        tv.setTypeface(typeface);


        populateItemList();

    }

    private void populateItemList(){
        ListView listView = (ListView)rootView.findViewById(R.id.listItems);

        String []operationList = getOperationList();
        adapter = new ArrayAdapter<>(getContext(), R.layout.listview_item, operationList);
        listView.setAdapter(adapter);
    }

    private String [] getOperationList()
    {
        Resources res = getResources();
        String []opList = {res.getString(R.string.dec_frac_tobinary), res.getString(R.string.dec_int_tobinary), res.getString(R.string.dec_tobinary),
                res.getString(R.string.rootlocation_bisection), res.getString(R.string.rootlocation_newtonraphson),
                res.getString(R.string.rootlocation_falseposition), res.getString(R.string.rootlocation_secante),
                res.getString(R.string.systemofequations_partial_pivoting), res.getString(R.string.systemofequations_complete_pivoting)};

        return opList;

    }

    private int selectedItem;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setSelected(true);

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(20);
            }
        }catch(NullPointerException ex){

        }

        selectedItem = position;
        itemSelected = true;

        Log.i(Utilities.Log ,"Item "+selectedItem+" selected");
    }

    @Override
    public void onClick(View view) {
        if(!itemSelected){
            Log.i(Utilities.Log, "No item selected in the listview");
            return;
        }

        switch(view.getId()){
            case R.id.buttonCompute:
               onCompute();
            break;

            case R.id.buttonSource:
              onSource();
        }
    }

    public void onCompute(){
        Fragment fragment;

        switch(selectedItem){
            case 0:
                fragment = new FragmentDecToBinFrac();
                Utilities.replaceFragment(this,fragment, getFragmentManager(), R.id.fragmentContainer);

                break;

            case 1:
                fragment = new FragmentDecToBinInt();
                Utilities.replaceFragment(this,fragment, getFragmentManager(), R.id.fragmentContainer);

                break;

            case 2:
                fragment = new FragmentDecToBin();
                Utilities.replaceFragment(this,fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 3:
                fragment = new FragmentBisection();
                Utilities.replaceFragment(this,fragment, getFragmentManager(), R.id.fragmentContainer);
                break;

            case 4:
                fragment  = new FragmentNewtonRaphson();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            case 5:
                fragment  = new FragmentRegulaFalsi();
                Utilities.replaceFragment(this, fragment, getFragmentManager(), R.id.fragmentContainer);
                break;
            default:

                break;
        }
    }

    public void onSource(){
        CustomDialog dialog = new CustomDialog();

        String msg = "The source code fo ";
        dialog.setMessage(msg);
        dialog.show(getActivity().getFragmentManager(), "NoticeDialogFragment");
    }
}
