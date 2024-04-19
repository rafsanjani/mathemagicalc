package com.foreverrafs.numericals.ui.ordinary_differential_eqns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.foreverrafs.core.Numericals
import com.foreverrafs.core.OdeResult
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.databinding.FragmentOdeEulerResultsBinding
import java.util.Arrays
import java.util.Locale

/** Created by Aziz Rafsanjani on 11/4/2017. */
class FragmentEulerResults : Fragment() {
    private var results: List<OdeResult>? = null
    private var eqn: String? = null
    private var x0 = 0.0
    private var x1 = 0.0
    private var h = 0.0
    private var initY = 0.0

    private lateinit var binding: FragmentOdeEulerResultsBinding

    private val interval: String
        get() = String.format(Locale.US, "[%.2f, %.2f]", x0, x1)

    private fun getInitY(): String {
        return String.format(Locale.US, "[%.2f]", initY)
    }

    private val initH: String
        get() = String.format(Locale.US, "[%.2f]", h)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOdeEulerResultsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val eulerResultsArgs: FragmentEulerResultsArgs = FragmentEulerResultsArgs.fromBundle(requireArguments())
        eqn = eulerResultsArgs.getEquation()
        x0 = eulerResultsArgs.getX0().toDouble()
        x1 = eulerResultsArgs.getX1().toDouble()
        initY = eulerResultsArgs.getInitY().toDouble()
        results = eulerResultsArgs.getResultlist().toList()
        h = eulerResultsArgs.getH().toDouble()
        initControls()
    }

    private fun backToEuler() {
        requireActivity().onBackPressed()
    }

    fun showAlgorithm() {
        if (activity != null) (activity as MainActivity?)?.showAlgorithm(findNavController(), "euler")
    }

    fun initControls() {
        binding.equation.setDisplayText(Numericals.generateTexEquation(eqn))
        val adapter = OdeResultsAdapter(results!!)
        binding.resultList.setAdapter(adapter)
        binding.interval.text = interval
        binding.initY.text = getInitY()
        binding.h.text = initH

        binding.btnShowAlgo.setOnClickListener {
            showAlgorithm()
        }

        binding.btnBackToEuler.setOnClickListener {
            backToEuler()
        }
    }
}
