package com.foreverrafs.numericals.ui.menus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.foreverrafs.numericals.databinding.FragmentShowAlgorithmBinding;

import java.lang.reflect.Method;

import timber.log.Timber;

public class FragmentShowAlgorithm extends Fragment {
    public static final String EXTRA_METHOD_NAME = "algorithm";
    private static final String TAG = "FragmentShowAlgorithm";

    private FragmentShowAlgorithmBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShowAlgorithmBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: Find something better to do about this region
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = getArguments();
        String algoName;

        if (bundle != null) {
            algoName = bundle.getString(EXTRA_METHOD_NAME);
        } else {
            //load index page if no file is provided
            Timber.i("no file provided, loading index page");
            algoName = "index";
        }

        binding.btnBackToMainMenu.setOnClickListener(v -> {
            onBackPressed();
        });
        loadAlgorithm(algoName);
    }

    void onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack();

        else if (getActivity() != null)
            getActivity().onBackPressed();
    }

    //the algoName is part of an html string so this name is just concatenated to an html file path to load the corresponding html
    //file into the webview
    @SuppressLint("SetJavaScriptEnabled")
    private void loadAlgorithm(String algoName) {
        algoName = "file:///android_asset/algorithms/algo_" + algoName + ".html";
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(algoName);
    }
}
