package com.foreverrafs.numericals.fragments.roots;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.adapter.RootResultsAdapter;
import com.foreverrafs.numericals.core.LocationOfRootType;
import com.foreverrafs.numericals.core.Numericals;
import com.foreverrafs.numericals.model.LocationOfRootResult;
import com.foreverrafs.numericals.utils.Utilities;

import java.util.List;
import java.util.Locale;

import katex.hourglass.in.mathlib.MathView;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public class FragmentSecanteResults extends Fragment {

    private List<LocationOfRootResult> results;
    private View rootView;
    private String eqn;
    private double x0, x1, difference;
    private int iterations;

    private String getInterval() {
        return String.format(Locale.US, "[%.2f, %.2f]", x0, x1);
    }

    private String getIteration() {
        return String.format(Locale.US, "[%d]", iterations);
    }

    private String getDifference() {
        return String.format(Locale.US, "[%.7f]", difference);
    }


    public void setResults(List<LocationOfRootResult> results) {
        this.results = results;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loc_of_roots_secante_result, container, false);

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
        }

        initControls();
    }

    public void initControls() {
        Button btnBack = rootView.findViewById(R.id.button_back);

        MathView equation;

        equation = rootView.findViewById(R.id.equation);
        equation.setDisplayText(Numericals.generateTexEquation(this.eqn));

        RootResultsAdapter adapter = new RootResultsAdapter(results, getContext(), LocationOfRootType.SECANTE);
        RecyclerView resultView = rootView.findViewById(R.id.resultList);
        resultView.setLayoutManager(new LinearLayoutManager(getContext()));

        resultView.setAdapter(adapter);

        TextView tvInterval = rootView.findViewById(R.id.initGuess);
        TextView tvIterations = rootView.findViewById(R.id.iterations);


        tvInterval.setText(getInterval());
        tvIterations.setText(getIteration());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentSecante();
                Bundle eqnArgs = new Bundle();

                //pass eqn and it's paramenters back to the calling fragment
                eqnArgs.putDouble("x0", x0);
                eqnArgs.putString("equation", eqn);
                eqnArgs.putDouble("x1", x1);
                eqnArgs.putInt("iterations", iterations);

                fragment.setArguments(eqnArgs);
                //Utilities.replaceFragment(fragment, getFragmentManager(), R.id.fragmentContainer, true);
            }
        });
        //Utilities.setTypeFace(rootView.findViewById(R.id.text_header), getContext(), Utilities.TypeFacename.raleway_bold);
    }
}
