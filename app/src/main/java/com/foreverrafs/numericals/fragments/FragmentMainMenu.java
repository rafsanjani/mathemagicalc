package com.foreverrafs.numericals.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.BuildConfig;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentMainMenu extends Fragment {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

    @BindView(R.id.bottom_sheet)
    ConstraintLayout bottomSheet;

    @BindView(R.id.tvVersion)
    TextView tvVersion;

    private BottomSheetBehavior sheetBehavior;
    private NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(view);

        tvVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));

        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mainMenuItems.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Conversion", R.drawable.button_number_conversion, Constants.MENU_NUMBER_CONVERSION));
        operations.add(new OperationMenu("Location of Roots", R.drawable.button_location_of_roots, Constants.MENU_LOCATION_OF_ROOTS));
        operations.add(new OperationMenu("Sys. of Eqns", R.drawable.button_system_of_eqns_3x3, Constants.MENU_SYSTEM_OF_EQUATIONS));
        operations.add(new OperationMenu("Ord. Diff. Eqns", R.drawable.button_ordinary_differential_eqns, Constants.MENU_ODE));
        operations.add(new OperationMenu("Interpolation", R.drawable.button_interpolation, Constants.MENU_INTERPOLATION));
        operations.add(new OperationMenu("Algorithms", R.drawable.button_algorithms, Constants.MENU_ALGORITHMS));
        operations.add(new OperationMenu("About", R.drawable.button_about, Constants.MENU_ABOUT));

        header.setText(R.string.operations);

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        adapter.setOnItemClickListenener(menuItemType -> {
            switch (menuItemType) {
                case Constants.MENU_NUMBER_CONVERSION:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentConversionMenu);
                    break;

                case Constants.MENU_LOCATION_OF_ROOTS:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentLocationOfRootsMenu);
                    break;
                case Constants.MENU_ALGORITHMS:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentShowAlgorithm);
                    break;

                case Constants.MENU_ABOUT:
                    //toggle bottom sheet here
                    toggleBottomSheet();
                    return;

                case Constants.MENU_ODE:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentODEMenu);
                    break;

                case Constants.MENU_SYSTEM_OF_EQUATIONS:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentSysOfEquationsMenu);
                    break;

                case Constants.MENU_INTERPOLATION:
                    navController.navigate(R.id.action_fragmentMainMenu_to_fragmentInterpolationMenu);
                    break;
            }
        });
    }

    private void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        else
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @OnClick(R.id.btnAboutClose)
    void onAboutClose() {
        toggleBottomSheet();
    }

    @OnClick(R.id.tvWebsite)
    void onWebsiteClicked(TextView url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url.getText().toString()));
        startActivity(intent);
    }
}
