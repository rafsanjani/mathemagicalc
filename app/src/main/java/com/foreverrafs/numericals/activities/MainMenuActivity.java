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

//    private void loadMenuFragment() {
//        FragmentMainMenu fragment = new FragmentMainMenu();
//        Utilities.loadFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer);
//    }
//
//    private void initControls() {
//        Toolbar toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        TextView toolbarAppTitle = findViewById(R.id.toolbarAppTitle);
//        toolbarAppTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/philosopher_bold.ttf"));
//
//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//        Log.i(Utilities.LOG_TAG, f.toString() + " is the active fragment now");
//
//        if (!(f instanceof FragmentMainMenu)) {
//            //transition between the open fragment and mainmenu
//            Utilities.replaceFragment(new FragmentMainMenu(), getSupportFragmentManager(), R.id.fragmentContainer, true);
//            return;
//        }
//
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//        System.out.println("key input detected");
//        return true;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//        }
//    }
//
//    public void onGoToOperation(MenuItem item) {
//        OperationListDialog dialog = new OperationListDialog();
//        dialog.show(getFragmentManager(), "Fragment dialog");
//
//    }
//
//    public void onAbout(MenuItem item) {
//        //Fragment fragment = new About();
//       // Utilities.replaceFragment(fragment, getSupportFragmentManager(), R.id.fragmentContainer, false);
}
