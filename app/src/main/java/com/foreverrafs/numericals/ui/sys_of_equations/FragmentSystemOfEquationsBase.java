package com.foreverrafs.numericals.ui.sys_of_equations;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.ui.menus.FragmentShowAlgorithm;

abstract class FragmentSystemOfEquationsBase extends Fragment {
    protected NavController navController = null;

    protected void goToMainmenu(Button button) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).goToMainMenu(button);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
    }

    protected void showAlgorithm(String methodName) {
        NavOptions navOptions = new NavOptions.Builder()
                .build();

        Bundle bundle = new Bundle();
        bundle.putString(FragmentShowAlgorithm.EXTRA_METHOD_NAME, methodName);
        navController.navigate(R.id.show_algorithm, bundle, navOptions, null);
    }
}
