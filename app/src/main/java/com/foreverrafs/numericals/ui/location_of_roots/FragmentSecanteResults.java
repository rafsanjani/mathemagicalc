package com.foreverrafs.numericals.ui.location_of_roots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.core.LocationOfRootResult;
import com.foreverrafs.core.LocationOfRootType;
import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.databinding.FragmentLocOfRootsSecanteResultBinding;

import java.util.List;
import java.util.Locale;

import katex.hourglass.in.mathlib.MathView;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecanteResults extends Fragment {

    private List<LocationOfRootResult> results;
    private String eqn;
    private double x0, x1;
    private int iterations;

    MathView mvEquation;

    TextView tvIterations;

    TextView tvInterval;

    RecyclerView rvResults;

    FragmentLocOfRootsSecanteResultBinding binding;


    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    public void setResults(List<LocationOfRootResult> results) {
        this.results = results;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocOfRootsSecanteResultBinding.inflate(inflater);
        rvResults = binding.resultList;
        tvInterval = binding.initGuess;
        tvIterations = binding.iterations;
        mvEquation = binding.equation;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;

        FragmentSecanteResultsArgs secanteArgs = FragmentSecanteResultsArgs.fromBundle(getArguments());
        this.eqn = secanteArgs.getEquation();
        this.x0 = secanteArgs.getX0();
        this.x1 = secanteArgs.getX1();
        this.iterations = secanteArgs.getIterations();

        initControls();
    }

    public void initControls() {
        mvEquation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        RootResultsAdapter adapter = new RootResultsAdapter(results, LocationOfRootType.SECANTE);
        rvResults.setAdapter(adapter);

        tvInterval.setText(getInterval());
        tvIterations.setText(getIteration());

        binding.btnBackToSecant.setOnClickListener(v -> onBackToSecant());
    }

    void onBackToSecant() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }
}
