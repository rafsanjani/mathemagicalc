package com.foreverrafs.numericals.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter;
import com.foreverrafs.numericals.model.OperationMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.list_main_menu)
    RecyclerView mainMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);

        mainMenuItems.setLayoutManager(new GridLayoutManager(this, 2));

        List<OperationMenu> operations = new ArrayList<>();
        operations.add(new OperationMenu("Conversion", R.drawable.button_number_conversion));
        operations.add(new OperationMenu("Location of Roots", R.drawable.button_location_of_roots));
        operations.add(new OperationMenu("Sys. of Eqns", R.drawable.button_system_of_eqns));
        operations.add(new OperationMenu("Ord. Diff. Eqns", R.drawable.button_ordinary_differential_eqns));
        operations.add(new OperationMenu("Algorithms", R.drawable.button_algorithms));
        operations.add(new OperationMenu("About", R.drawable.button_about));


        OperationsMenuAdapter adapter = new OperationsMenuAdapter(operations);

        mainMenuItems.setHasFixedSize(true);
        mainMenuItems.setAdapter(adapter);

    }
}
