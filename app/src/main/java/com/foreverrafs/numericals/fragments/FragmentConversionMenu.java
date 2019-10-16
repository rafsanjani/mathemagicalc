package com.foreverrafs.numericals.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.fragments.conversions.FragmentAllInOne;
import com.foreverrafs.numericals.fragments.conversions.FragmentBinToDec;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBin;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToBinInt;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToHexadecimal;
import com.foreverrafs.numericals.fragments.conversions.FragmentDecToOctal;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentConversionMenu extends Fragment implements OperationsMenuAdapter.MenuItemClickListenener {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mainMenuItems.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Dec to Bin(Fraction)", R.drawable.button_normal_nc_bintodecimal, Constants.CONVERSION_DEC_TO_BIN_FRACTION));
        operations.add(new OperationMenu("Dec to Bin(Integer)", R.drawable.button_normal_nc_bintodecimal, Constants.CONVERSION_DEC_TO_BIN_INT));
        operations.add(new OperationMenu("Dec to Bin(Any)", R.drawable.button_normal_nc_bintodecimal, Constants.CONVERSION_DEC_TO_BIN_ALL));
        operations.add(new OperationMenu("Bin to Decimal", R.drawable.button_normal_nc_bintodecimal, Constants.CONVERSION_BIN_TO_DEC));
        operations.add(new OperationMenu("Dec to Hexa", R.drawable.button_normal_nc_decitohexadecimal, Constants.CONVERSION_DEC_TO_HEXA));
        operations.add(new OperationMenu("Dec to Octal", R.drawable.button_normal_nc_decitooctal, Constants.CONVERSION_DEC_TO_OCTAL));
        operations.add(new OperationMenu("All in One Conversion", R.drawable.button_normal_nc, Constants.CONVERSION_ALL_IN_ONE));


        header.setText(getString(R.string.number_conversions));

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        adapter.setOnItemClickListenener(this);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_main_menu);
//
//        ButterKnife.bind(this);
//
//        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));
//
//        List<OperationMenu> operations = new ArrayList<>();
//        operations.add(new OperationMenu("Dec to Bin(Fraction)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_FRACTION));
//        operations.add(new OperationMenu("Dec to Bin(Integer)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_INT));
//        operations.add(new OperationMenu("Dec to Bin(Any)", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_BIN_ALL));
//        operations.add(new OperationMenu("Bin to Decimal", R.drawable.button_location_of_roots, Constants.CONVERSION_BIN_TO_DEC));
//        operations.add(new OperationMenu("Dec to Hexa", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_HEXA));
//        operations.add(new OperationMenu("Dec to Octal", R.drawable.button_location_of_roots, Constants.CONVERSION_DEC_TO_OCTAL));
//        operations.add(new OperationMenu("All in One Conversion", R.drawable.button_location_of_roots, Constants.CONVERSION_ALL_IN_ONE));
//
//
//        header.setText(getString(R.string.number_conversions));
//
//        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);
//
//        adapter.setOnItemClickListenener(this);
//
//        mainMenuItems.setHasFixedSize(true);
//        mainMenuItems.setAdapter(adapter);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void OnMenuItemClicked(int menuItemType) {

        Fragment fragment = null;
        switch (menuItemType) {
            case Constants.CONVERSION_DEC_TO_BIN_FRACTION:
                fragment = new FragmentDecToBin();
                break;

            case Constants.CONVERSION_DEC_TO_BIN_INT:
                fragment = new FragmentDecToBinInt();
                break;

            case Constants.CONVERSION_ALL_IN_ONE:
                fragment = new FragmentAllInOne();
                break;

            case Constants.CONVERSION_DEC_TO_HEXA:
                fragment = new FragmentDecToHexadecimal();
                break;

            case Constants.CONVERSION_DEC_TO_OCTAL:
                fragment = new FragmentDecToOctal();
                break;
            case Constants.CONVERSION_BIN_TO_DEC:
                fragment = new FragmentBinToDec();
                break;
        }
//        if (fragment != null)
//            Utilities.replaceFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
}
