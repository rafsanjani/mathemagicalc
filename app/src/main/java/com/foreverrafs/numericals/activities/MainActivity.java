package com.foreverrafs.numericals.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.foreverrafs.numericals.BuildConfig;
import com.foreverrafs.numericals.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.bottom_sheet)
    ConstraintLayout bottomSheet;

    //this is contained on the bottomsheet
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navController);

        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        tvVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
        enableStrictMode();

    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        else
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void goToMainMenu(Button button) {
        NavController controller = Navigation.findNavController(button);
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_left)
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.nav_graph, true)
                .setExitAnim(R.anim.slide_out_right)
                .build();

        controller.navigate(R.id.fragment_main_menu, null, navOptions, null);
    }

    @Override
    public void onBackPressed() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            toggleBottomSheet();
        else
            super.onBackPressed();
    }

    //tvWebsite is a textview on the bottomsheet
    @OnClick(R.id.tvWebsite)
    void onWebsiteClicked(TextView url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url.getText().toString()));
        startActivity(intent);
    }

    @OnClick(R.id.btnAboutClose)
    void onAboutClose() {
        toggleBottomSheet();
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();

            StrictMode.setThreadPolicy(policy);

            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();
            StrictMode.setVmPolicy(vmPolicy);
        }

    }
}
