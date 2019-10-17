package com.foreverrafs.numericals.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
