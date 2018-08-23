package com.foreverrafs.numericals.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;

import java.lang.reflect.Method;

public class ShowAlgorithm extends AppCompatActivity {

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
        Utilities.setToolbarTypeface(this, toolbarAppTitle);

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
            Log.i(Utilities.Log, "no file provided, loading index page");
            algoName = "index";
        }

        loadAlgorithm(algoName);
    }

    //the algoName is part of an html string so this name is just concatenated to an html file path to load the corresponding html
    //file into the webview
    private void loadAlgorithm(String algoName) {
        algoName = "file:///android_asset/algorithms/algo_" + algoName + ".html";
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(algoName);
    }

    public void onBack(View view) {
        finish();
    }
}
