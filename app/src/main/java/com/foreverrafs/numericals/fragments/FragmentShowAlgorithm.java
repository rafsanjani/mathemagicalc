package com.foreverrafs.numericals.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.dialog.OperationListDialog;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentShowAlgorithm extends Fragment {
    private static final String TAG = "FragmentShowAlgorithm";
    @BindView(R.id.webView)
    WebView mWebView;

    public void onGoToOperation(MenuItem item) {
        OperationListDialog dialog = new OperationListDialog();
        if (getActivity() != null)
            dialog.show(getActivity().getFragmentManager(), "Fragment dialog");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_algorithm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //TODO: Find something better to do about this region
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bundle bundle = getArguments();
        String algoName = "";
        if (bundle != null) {
            algoName = bundle.getString("algorithm_name");
        } else {
            //load index page if no file is provided
            Log.i(TAG, "no file provided, loading index page");
            algoName = "index";
        }

        loadAlgorithm(algoName);
    }

    @OnClick(R.id.btnBack)
    void onBackPressed() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }

    //the algoName is part of an html string so this name is just concatenated to an html file path to load the corresponding html
    //file into the webview
    @SuppressLint("SetJavaScriptEnabled")
    private void loadAlgorithm(String algoName) {
        algoName = "file:///android_asset/algorithms/algo_" + algoName + ".html";
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(algoName);
    }
}
