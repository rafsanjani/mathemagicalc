package com.foreverrafs.numericals.fragments.roots;

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
import com.foreverrafs.numericals.R;
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

public class FragmentFalsePositionResults extends Fragment {

    private List<LocationOfRootResult> results;
    private String eqn;
    private double x0, x1, tolerance;
    private int iterations;

    @BindView(R.id.interval)
    TextView tvInterval;

    @BindView(R.id.iterations)
    TextView tvIterations;

    @BindView(R.id.tolerance)
    TextView tvTolerance;

    @BindView(R.id.resultList)
    RecyclerView rvResultList;

    @BindView(R.id.equation)
    MathView mvEquation;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    private String getTolerance() {
        return String.format(Locale.US, "[%.7f]", tolerance);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loc_of_roots_falseposition_results, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;
        FragmentFalsePositionResultsArgs falsePositonArgs = FragmentFalsePositionResultsArgs.fromBundle(getArguments());

        this.eqn = falsePositonArgs.getEquation();
        this.iterations = falsePositonArgs.getIterations();
        this.tolerance = falsePositonArgs.getTolerance();
        this.results = Arrays.asList(falsePositonArgs.getResultlist());
        this.x0 = falsePositonArgs.getX0();
        this.x1 = falsePositonArgs.getX1();

        initControls();
    }

    public void initControls() {
        mvEquation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        RootResultsAdapter adapter = new RootResultsAdapter(results, LocationOfRootType.FALSE_POSITION);
        rvResultList.setAdapter(adapter);

        tvInterval.setText(getInterval());
        tvIterations.setText(getIteration());
        tvTolerance.setText(getTolerance());
    }

    @OnClick(R.id.btnBackToFalsePos)
    void onBackPressed() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }
}
