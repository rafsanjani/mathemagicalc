package com.foreverrafs.numericals.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.dialog.OperationListDialog;
import com.foreverrafs.numericals.utils.Utilities;

import java.lang.reflect.Method;

public class ShowAlgoActivity extends AppCompatActivity {

    private WebView mWebView;

    public void onGoToOperation(MenuItem item) {
        OperationListDialog dialog = new OperationListDialog();
        dialog.show(getFragmentManager(), "Fragment dialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_algorithm);
        Bundle bundle = getIntent().getExtras();

        Toolbar myToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //set the typeface of the text which appears on the toolbar
        TextView toolbarAppTitle = findViewById(R.id.toolbarAppTitle);
        toolbarAppTitle.setText("Algorithm");

        //TODO: Find something better to do about this region
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String algoName = "";
        if (bundle != null) {
            algoName = bundle.getString("algorithm_name");
        } else {
            //load index page if no file is provided
            Log.i(Utilities.LOG_TAG, "no file provided, loading index page");
            algoName = "index";
        }

        loadAlgorithm(algoName);
    }

    //the algoName is part of an html string so this name is just concatenated to an html file path to load the corresponding html
    //file into the webview
    private void loadAlgorithm(String algoName) {
        algoName = "file:///android_asset/algorithms/algo_" + algoName + ".html";
        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(algoName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mWebView.canGoBack())
            mWebView.goBack();
        else
            onBackPressed();

        return true;
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
