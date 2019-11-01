package com.foreverrafs.numericals.fragments.roots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.core.LocationOfRootResult;
import com.foreverrafs.core.LocationOfRootType;
import com.foreverrafs.core.Numericals;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.adapter.RootResultsAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import katex.hourglass.in.mathlib.MathView;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentBisectionResults extends Fragment {

    private List<LocationOfRootResult> results;
    private View rootView;
    private String eqn;
    private double x0, x1, tolerance;
    private int iterations;

    @BindView(R.id.interval)
    TextView tvInterval;

    @BindView(R.id.tolerance)
    TextView tvTolerance;

    @BindView(R.id.iterations)
    TextView tvIterations;

    @BindView(R.id.equation)
    MathView mvEquation;

    @BindView(R.id.resultList)
    RecyclerView rvResultsList;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    private String getTolerance() {
        return String.format(Locale.US, "[%.13f]", tolerance);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_bisection_results, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;

        FragmentBisectionResultsArgs bisectionArgs = FragmentBisectionResultsArgs.fromBundle(getArguments());
        this.eqn = bisectionArgs.getEquation();
        this.x0 = bisectionArgs.getX0();
        this.x1 = bisectionArgs.getX1();
        this.iterations = bisectionArgs.getIterations();
        this.tolerance = bisectionArgs.getTolerance();
        results = Arrays.asList(bisectionArgs.getResultlist());

        initControls();
    }

    public void initControls() {
        mvEquation.setDisplayText(Numericals.generateTexEquation(this.eqn));
        tvInterval.setText(getInterval());
        tvIterations.setText(getIteration());
        tvTolerance.setText(getTolerance());

        RootResultsAdapter adapter = new RootResultsAdapter(results, LocationOfRootType.BISECTION);

        rvResultsList.setAdapter(adapter);
    }

    @OnClick(R.id.btnBackToBisection)
    void onBackPressed() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }

    @OnClick(R.id.btnShowAlgo)
    void onShowAlgorithm() {
        NavController navController = Navigation.findNavController(rootView);
        if (getActivity() != null)
            ((MainActivity) getActivity()).showAlgorithm(navController, "bisection");
    }
}
