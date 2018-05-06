package com.example.azizrafsanjani.numericals.fragments.roots;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azizrafsanjani.numericals.R;
import com.example.azizrafsanjani.numericals.adapter.BisectionAdapter;
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

    private BisectionAdapter adapter;
    List<Double> results;
    private String equation;
    private double x1, x2;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x1, x2);
    }



    public void setResults(List<Double> results, String equation, double x1, double x2) {
        this.results = results;
        this.x1 = x1;
        this.x2 = x2;
        this.equation = equation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_bisection_results, container, false);
        initControls();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new BisectionAdapter(getActivity(), R.layout.list_item, results);
        ListView listView = rootView.findViewById(R.id.resultList);
        TextView textView = rootView.findViewById(R.id.interval);
        textView.setText(getInterval());

        listView.setAdapter(adapter);
    }

    public void initControls() {
        Button btnBack = rootView.findViewById(R.id.buttonBack);

        MathView formula_two;
       // String tex = " $$f(x) = 3x^3 + 2x - 5$$";

        formula_two = (MathView) rootView.findViewById(R.id.equation);
        formula_two.setDisplayText(Numericals.generateTexEquation(equation));


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.replaceFragment(new FragmentBisection(), getFragmentManager(), R.id.fragmentContainer, true);
            }
        });

        Utilities.setLobsterTypeface(rootView.findViewById(R.id.headerText), getContext());


        ListView listView = rootView.findViewById(R.id.resultList);
        listView.setAdapter(adapter);
    }
}
