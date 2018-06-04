package com.example.azizrafsanjani.numericals.fragments.roots;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.adapter.RootResultsAdapter;
import com.example.azizrafsanjani.numericals.model.LocationOfRootResult;
import com.example.azizrafsanjani.numericals.model.LocationOfRootType;
import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;

import java.util.List;
import java.util.Locale;

import katex.hourglass.in.mathlib.MathView;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBisectionResults extends Fragment {

    private View rootView;

    private RootResultsAdapter adapter;
    List<LocationOfRootResult> results;
    private String eqn;
    private double x0, x1, tolerance;
    private int iterations;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    private String getTolerance() {
        return String.format(Locale.US, "[%.7f]", tolerance);
    }


    public void setResults(List<LocationOfRootResult> results) {
        this.results = results;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_bisection_results, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle eqnArgs = getArguments();
        if (eqnArgs != null) {
            this.eqn = eqnArgs.getString("equation");
            this.x0 = eqnArgs.getDouble("x0");
            this.x1 = eqnArgs.getDouble("x1");
            this.iterations = eqnArgs.getInt("iterations");
            this.tolerance = eqnArgs.getDouble("tolerance");
        }

        initControls();
    }

    public void initControls() {
        Button btnBack = rootView.findViewById(R.id.buttonBack);

        MathView equation;
        // String tex = " $$f(x) = 3x^3 + 2x - 5$$";

        equation = rootView.findViewById(R.id.equation);
        equation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        adapter = new RootResultsAdapter(results, getContext(), LocationOfRootType.BISECTION);
        RecyclerView resultView = rootView.findViewById(R.id.resultList);
        resultView.setLayoutManager(new LinearLayoutManager(getContext()));

        resultView.setAdapter(adapter);

        TextView tvInterval = rootView.findViewById(R.id.interval);
        TextView tvIterations = rootView.findViewById(R.id.iterations);
        TextView tvTolerance = rootView.findViewById(R.id.tolerance);


        tvInterval.setText(getInterval());
        tvIterations.setText(getIteration());
        tvTolerance.setText(getTolerance());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentBisection();
                Bundle eqnArgs = new Bundle();

                //pass eqn and it's paramenters back to the calling fragment
                eqnArgs.putDouble("x0", x0);
                eqnArgs.putString("equation", eqn);
                eqnArgs.putDouble("x1", x1);
                eqnArgs.putInt("iterations", iterations);
                eqnArgs.putDouble("tolerance", tolerance);

                fragment.setArguments(eqnArgs);
                Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, true);
            }
        });
        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());
    }
}
