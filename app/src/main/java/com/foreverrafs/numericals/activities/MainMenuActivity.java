package com.foreverrafs.numericals.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.custom_views.RafsTextView;
import com.foreverrafs.numericals.model.OperationMenu;
import com.foreverrafs.numericals.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @BindView(R.id.headerTextView)
    RafsTextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Conversion", R.drawable.button_location_of_roots, Constants.MENU_NUMBER_CONVERSION));
        operations.add(new OperationMenu("Location of Roots", R.drawable.button_location_of_roots, Constants.MENU_LOCATION_OF_ROOTS));
        operations.add(new OperationMenu("Sys. of Eqns", R.drawable.button_location_of_roots, Constants.MENU_SYSTEM_OF_EQUATIONS));
        operations.add(new OperationMenu("Ord. Diff. Eqns", R.drawable.button_location_of_roots, Constants.MENU_ODE));
        operations.add(new OperationMenu("Algorithms", R.drawable.button_location_of_roots, Constants.MENU_ALGORITHMS));
        operations.add(new OperationMenu("About", R.drawable.button_location_of_roots, Constants.MENU_ABOUT));

        header.setText(R.string.operations);

        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);


        adapter.setOnItemClickListenener(menuItemType -> {
            Intent intent = null;
            switch (menuItemType) {
                case Constants.MENU_NUMBER_CONVERSION:
                    intent = new Intent(this, ConversionMenuActivity.class);
                    break;

                case Constants.MENU_LOCATION_OF_ROOTS:
                    intent = new Intent(this, LocationOfRootsMenu.class);
                    break;
                case Constants.MENU_ALGORITHMS:
                    intent = new Intent(this, ConversionMenuActivity.class);
                    break;

                case Constants.MENU_ABOUT:
                    intent = new Intent(this, ConversionMenuActivity.class);
                    break;
                case Constants.MENU_ODE:
                    intent = new Intent(this, ConversionMenuActivity.class);
                    break;

                case Constants.MENU_SYSTEM_OF_EQUATIONS:
                    intent = new Intent(this, ConversionMenuActivity.class);
                    break;

            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}
