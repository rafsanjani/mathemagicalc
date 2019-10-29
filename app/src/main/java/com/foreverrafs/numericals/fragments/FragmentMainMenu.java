package com.foreverrafs.numericals.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentMainMenu extends Fragment {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

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

        mainMenuItems.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Conversion", R.drawable.button_normal_nc, Constants.MENU_NUMBER_CONVERSION));
        operations.add(new OperationMenu("Location of Roots", R.drawable.button_normal_locationofroots, Constants.MENU_LOCATION_OF_ROOTS));
        operations.add(new OperationMenu("Sys. of Eqns", R.drawable.button_normal_system_of_eqns_gaussian3x3, Constants.MENU_SYSTEM_OF_EQUATIONS));
        operations.add(new OperationMenu("Ord. Diff. Eqns", R.drawable.button_normal_ordinary_diff_eqns, Constants.MENU_ODE));
        operations.add(new OperationMenu("Interpolation", R.drawable.button_normal_interpolation, Constants.MENU_INTERPOLATION));
        operations.add(new OperationMenu("Algorithms", R.drawable.button_normal_algorithms, Constants.MENU_ALGORITHMS));
        operations.add(new OperationMenu("About", R.drawable.button_normal_about, Constants.MENU_ABOUT));

        header.setText(R.string.operations);

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        adapter.setOnItemClickListenener(menuItemType -> {
            NavDirections directions = null;
            switch (menuItemType) {
                case Constants.MENU_NUMBER_CONVERSION:
                    directions = FragmentMainMenuDirections.fragmentConversionMenu();
                    break;

                case Constants.MENU_LOCATION_OF_ROOTS:
                    directions = FragmentMainMenuDirections.fragmentLocationOfRootsMenu();
                    break;

                case Constants.MENU_ALGORITHMS:
                    directions = FragmentMainMenuDirections.fragmentShowAlgorithm();
                    break;

                case Constants.MENU_ABOUT:
                    //toggle bottom sheet here
                    ((MainActivity) Objects.requireNonNull(getActivity())).toggleBottomSheet();
                    return;

                case Constants.MENU_ODE:
                    directions = FragmentMainMenuDirections.fragmentODEMenu();
                    break;

                case Constants.MENU_SYSTEM_OF_EQUATIONS:
                    directions = FragmentMainMenuDirections.fragmentSysOfEquationsMenu();
                    break;

                case Constants.MENU_INTERPOLATION:
                    directions = FragmentMainMenuDirections.fragmentInterpolationMenu();
                    break;
            }

            if (directions != null)
                navController.navigate(directions);
        });
    }
}
