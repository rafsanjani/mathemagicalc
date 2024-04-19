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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;

import java.util.ArrayList;
import java.util.List;



public class FragmentODEMenu extends Fragment implements OperationsMenuAdapter.MenuItemClickListenener {

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

        mainMenuItems.setLayoutManager(new LinearLayoutManager(getContext()));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Euler's Forward Method", R.drawable.button_normal_ode_euler, Constants.ODE_EULER));


        header.setText(getString(R.string.ode_header));

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        adapter.setOnItemClickListenener(this);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);
    }

    @Override
    public void onMenuItemClicked(int menuItemType) {
        if (menuItemType == Constants.ODE_EULER) {
            navController.navigate(FragmentODEMenuDirections.actionOdeMenuToFragmentEuler());
        }
    }
}
