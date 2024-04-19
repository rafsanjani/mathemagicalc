package com.foreverrafs.numericals.ui.menus;


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
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class FragmentLocationOfRootsMenu extends Fragment {


    RecyclerView mainMenuItems;

    TextView header;

    com.foreverrafs.numericals.databinding.FragmentMainMenuBinding binding;

    private NavController navController = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = com.foreverrafs.numericals.databinding.FragmentMainMenuBinding.inflate(inflater);
        mainMenuItems = binding.contentMenu.listMainMenu;
        header = binding.contentMenu.tvHeader;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initControls();

        mainMenuItems.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Bisection", R.drawable.button_normal_locationofroots, Constants.LOCATION_OF_ROOTS_BISECTION));
        operations.add(new OperationMenu("Newton Raphson", R.drawable.button_normal_locationofroots, Constants.LOCATION_OF_ROOTS_NEWTON_RAPHSON));
        operations.add(new OperationMenu("False Position", R.drawable.button_normal_locationofroots, Constants.LOCATION_OF_ROOTS_FALSE_POSITION));
        operations.add(new OperationMenu("Secant", R.drawable.button_normal_locationofroots, Constants.LOCATION_OF_ROOTS_SECANT));


        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        adapter.setOnItemClickListenener(menuItemType -> {
            NavDirections directions = null;
            switch (menuItemType) {
                case Constants.LOCATION_OF_ROOTS_BISECTION:
                    directions = FragmentLocationOfRootsMenuDirections.actionLocationOfRootsMenuToFragmentBisection();
                    break;
                case Constants.LOCATION_OF_ROOTS_NEWTON_RAPHSON:
                    directions = FragmentLocationOfRootsMenuDirections.actionLocationOfRootsMenuToFragmentNewtonRaphson();
                    break;
                case Constants.LOCATION_OF_ROOTS_FALSE_POSITION:
                    directions = FragmentLocationOfRootsMenuDirections.actionLocationOfRootsMenuToFragmentFalsePosition();
                    break;
                case Constants.LOCATION_OF_ROOTS_SECANT:
                    directions = FragmentLocationOfRootsMenuDirections.actionLocationOfRootsMenuToFragmentSecante();
                    break;
            }
            if (directions != null)
                navController.navigate(directions);
        });
    }

    private void initControls() {
        header.setText(R.string.loc_of_rooots);
    }
}
