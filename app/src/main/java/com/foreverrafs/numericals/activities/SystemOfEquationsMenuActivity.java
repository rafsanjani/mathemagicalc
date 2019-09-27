package com.foreverrafs.numericals.activities;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussSeidel;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussSeidelWithSOR;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianComplete3x3;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianComplete4x4;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianPartial3x3;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentGaussianPartial4x4;
import com.foreverrafs.numericals.fragments.sys_of_equations.FragmentJacobi;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SystemOfEquationsMenuActivity extends AppCompatActivity implements OperationsMenuAdapter.MenuItemClickListenener {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Gaussian (Partial) 3 x 3", R.drawable.button_system_of_eqns_3x3, Constants.SYS_OF_EQN_GAUSSIAN_PART_3X3));
        operations.add(new OperationMenu("Gaussian (Complete) 3 x 3", R.drawable.button_system_of_eqns_3x3, Constants.SYS_OF_EQN_GAUSSIAN_COMPLETE_3X3));
        operations.add(new OperationMenu("Gaussian (Partial ) 4 x 4", R.drawable.button_system_of_eqns_4x4, Constants.SYS_OF_EQN_GAUSSIAN_PART_4X4));
        operations.add(new OperationMenu("Gaussian (Complete) 4 x 4", R.drawable.button_system_of_eqns_4x4, Constants.SYS_OF_EQN_GAUSSIAN_COMPLETE_4X4));
        operations.add(new OperationMenu("jacobi", R.drawable.button_system_of_eqns_jacobi, Constants.SYS_OF_EQN_JACOBI));
        operations.add(new OperationMenu("Gauss Seidel", R.drawable.button_system_of_eqns_gaussseidel, Constants.SYS_OF_EQN_GAUSS_SEIDEL));
        operations.add(new OperationMenu("Gauss Seidel (SOR)", R.drawable.button_system_of_eqns_gaussseidel, Constants.SYS_OF_EQN_GAUSS_SEIDEL_SOR));


        header.setText(getString(R.string.system_of_equations));

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        adapter.setOnItemClickListenener(this);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void OnMenuItemClicked(int menuItemType) {

        Fragment fragment = null;
        switch (menuItemType) {
            case Constants.SYS_OF_EQN_GAUSSIAN_PART_3X3:
                fragment = new FragmentGaussianPartial3x3();
                break;

            case Constants.SYS_OF_EQN_GAUSSIAN_COMPLETE_3X3:
                fragment = new FragmentGaussianComplete3x3();
                break;

            case Constants.SYS_OF_EQN_GAUSSIAN_PART_4X4:
                fragment = new FragmentGaussianPartial4x4();
                break;

            case Constants.SYS_OF_EQN_JACOBI:
                fragment = new FragmentJacobi();
                break;

            case Constants.SYS_OF_EQN_GAUSS_SEIDEL:
                fragment = new FragmentGaussSeidel();
                break;
            case Constants.SYS_OF_EQN_GAUSS_SEIDEL_SOR:
                fragment = new FragmentGaussSeidelWithSOR();
                break;
            case Constants.SYS_OF_EQN_GAUSSIAN_COMPLETE_4X4:
                fragment = new FragmentGaussianComplete4x4();
                break;
        }

        if (fragment != null)
            Utilities.replaceFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
