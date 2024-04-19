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
import com.foreverrafs.numericals.databinding.FragmentLocOfRootsNewtonResultsBinding;

import java.util.List;
import java.util.Locale;

import katex.hourglass.in.mathlib.MathView;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentNewtonRaphsonResults extends Fragment {

    private List<LocationOfRootResult> results;
    private String eqn;
    private double x0;
    private int iterations;

    TextView tvIterations;

    TextView tvInitGuess;

    MathView mvEquation;

    RecyclerView rvResults;

    FragmentLocOfRootsNewtonResultsBinding binding;

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    private String getInitialGuess() {
        return String.format(Locale.US, "[%f", x0);
    }

    public void setResults(List<LocationOfRootResult> results) {
        this.results = results;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocOfRootsNewtonResultsBinding.inflate(inflater);
        tvIterations = binding.iterations;
        tvInitGuess = binding.initGuess;
        mvEquation = binding.equation;
        rvResults = binding.resultList;
        return binding.root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;

        FragmentNewtonRaphsonResultsArgs newtonArgs = FragmentNewtonRaphsonResultsArgs.fromBundle(getArguments());

        this.eqn = newtonArgs.getEquation();
        this.x0 = newtonArgs.getX0();
        this.iterations = newtonArgs.getIterations();

        binding.btnBackToNewton.setOnClickListener(v -> backToNewton());

        initControls();
    }

    public void initControls() {
        mvEquation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        RootResultsAdapter adapter = new RootResultsAdapter(results, LocationOfRootType.NEWTON_RAPHSON);
        rvResults.setAdapter(adapter);

        tvInitGuess.setText(getInitialGuess());
        tvIterations.setText(getIteration());
    }

    void backToNewton() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }
}
