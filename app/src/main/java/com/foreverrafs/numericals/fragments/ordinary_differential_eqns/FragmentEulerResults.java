package com.foreverrafs.numericals.fragments.ordinary_differential_eqns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverrafs.core.Numericals;
import com.foreverrafs.core.OdeResult;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.MainActivity;
import com.foreverrafs.numericals.adapter.OdeResultsAdapter;

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

public class FragmentEulerResults extends Fragment {

    private List<OdeResult> results;
    private String eqn;
    private double x0, x1, h, initY;

    @BindView(R.id.equation)
    MathView mvEquation;

    @BindView(R.id.btnBackToEuler)
    Button btnBack;

    @BindView(R.id.btnShowAlgo)
    Button btnShowAlgo;

    @BindView(R.id.resultList)
    RecyclerView rvResults;

    @BindView(R.id.interval)
    TextView tvInterval;

    @BindView(R.id.initY)
    TextView tvInitY;

    @BindView(R.id.h)
    TextView tvH;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getInitY() {
        return String.format(Locale.US, "[%.2f]", initY);
    }

    private String getInitH() {
        return String.format(Locale.US, "[%.2f]", h);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ode_euler_results, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null)
            return;
        FragmentEulerResultsArgs eulerResultsArgs = FragmentEulerResultsArgs.fromBundle(getArguments());
        this.eqn = eulerResultsArgs.getEquation();
        this.x0 = eulerResultsArgs.getX0();
        this.x1 = eulerResultsArgs.getX1();
        this.initY = eulerResultsArgs.getInitY();
        this.results = Arrays.asList(eulerResultsArgs.getResultlist());
        this.h = eulerResultsArgs.getH();

        initControls();
    }

    @OnClick(R.id.btnBackToEuler)
    void backToEuler() {
        if (getActivity() != null)
            getActivity().onBackPressed();
    }

    @OnClick(R.id.btnShowAlgo)
    void showAlgorithm(Button button) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).showAlgorithm(Navigation.findNavController(button), "euler");
    }

    public void initControls() {
        mvEquation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        OdeResultsAdapter adapter = new OdeResultsAdapter(results);

        rvResults.setAdapter(adapter);

        tvInterval.setText(getInterval());
        tvInitY.setText(getInitY());
        tvH.setText(getInitH());
    }
}
