package com.foreverrafs.numericals.activities;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.fragments.roots.FragmentBisection;
import com.foreverrafs.numericals.fragments.roots.FragmentFalsePosition;
import com.foreverrafs.numericals.fragments.roots.FragmentNewtonRaphson;
import com.foreverrafs.numericals.fragments.roots.FragmentSecante;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aziz Rafsanjani on 11/3/2017.
 */

public class LocationOfRootsMenuActivity extends AppCompatActivity {


    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.tvHeader)
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

        initControls();

        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Bisection", R.drawable.button_location_of_roots, Constants.LOCATION_OF_ROOTS_BISECTION));
        operations.add(new OperationMenu("Newton Raphson", R.drawable.button_location_of_roots, Constants.LOCATION_OF_ROOTS_NEWTON_RAPHSON));
        operations.add(new OperationMenu("False Position", R.drawable.button_location_of_roots, Constants.LOCATION_OF_ROOTS_FALSE_POSITION));
        operations.add(new OperationMenu("Secant", R.drawable.button_location_of_roots, Constants.LOCATION_OF_ROOTS_SECANT));


        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

        adapter.setOnItemClickListenener(menuItemType -> {
            Fragment fragment = null;
            switch (menuItemType) {
                case Constants.LOCATION_OF_ROOTS_BISECTION:
                    fragment = new FragmentBisection();
                    break;
                case Constants.LOCATION_OF_ROOTS_NEWTON_RAPHSON:
                    fragment = new FragmentNewtonRaphson();
                    break;
                case Constants.LOCATION_OF_ROOTS_FALSE_POSITION:
                    fragment = new FragmentFalsePosition();
                    break;
                case Constants.LOCATION_OF_ROOTS_SECANT:
                    fragment = new FragmentSecante();
                    break;
            }
            if (fragment != null)
                Utilities.replaceFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);

        });

    }

    private void initControls() {
        header.setText(R.string.loc_of_rooots);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
