package com.foreverrafs.numericals.ui.ordinary_differential_eqns

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.databinding.FragmentOdeEulerBinding
import com.foreverrafs.numericals.utils.Utilities
import timber.log.Timber

/** Created by Aziz Rafsanjani on 11/4/2017. */
class FragmentEuler : Fragment(), TextWatcher {

    private lateinit var binding: FragmentOdeEulerBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOdeEulerBinding.inflate(inflater)
        return binding.root
    }

    fun initControls() {
        val myKeyListener = View.OnKeyListener { _: View?, i: Int, keyEvent: KeyEvent ->
            binding.tilUserInput.isErrorEnabled = false
            binding.tilInitY.isErrorEnabled = false
            binding.tilX0.isErrorEnabled = false
            binding.tilX1.isErrorEnabled = false
            binding.tilInitY.isErrorEnabled = false
            if (keyEvent.action != KeyEvent.ACTION_DOWN) return@OnKeyListener false
            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                onCalculate(binding.btnCalculate.getText().toString())
                return@OnKeyListener true
            }
            false
        }
        binding.textH.setOnKeyListener(myKeyListener)
        binding.tilStepSize.setOnKeyListener(myKeyListener)
        binding.textEquation.setOnKeyListener(myKeyListener)
        binding.textEquation.addTextChangedListener(this)

        binding.btnBackToMainMenu.setOnClickListener {
            backToMainMenu()
        }

        binding.btnCalculate.setOnClickListener {
            onCalculate(it)
        }

        binding.btnShowAlgo.setOnClickListener {
            onShowAlgorithm()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initControls()
    }

    private fun backToMainMenu() {
        val parentActivity: Activity? = activity
        if (parentActivity != null) (parentActivity as MainActivity).goToMainMenu()
    }

    fun onCalculate(button: View) {
        Timber.i("performing Euler's Forward calculation")
        onCalculate((button as Button).text.toString())
    }

    fun onShowAlgorithm() {
        if (activity != null) (activity as MainActivity?)!!.showAlgorithm(
            findNavController(),
            "euler"
        )
    }

    private fun onCalculate(buttonText: String) {
        //only handle empty inputs in this module and display using their corresponding TextInputLayouts.
        //Any other errors are handled in Numericals.java. This may check most of the NumberFormatException which
        //gets thrown as a result of passing empty parameters to Type.ParseType(string param)
        if (!checkForEmptyInput()) {
            return
        }
        val eqn: String
        val x0: Float
        val x1: Float
        val h: Float
        val interval: DoubleArray
        val initY: Int
        try {
            eqn = binding.textEquation.getText().toString()
            x0 = binding.x0.getText().toString().toFloat()
            x1 = binding.x1.getText().toString().toFloat()
            interval = doubleArrayOf(x0.toDouble(), x1.toDouble())
            h = binding.textH.getText().toString().toFloat()
            initY = binding.textInitialY.getText().toString().toInt()
            val answerAtLastIterate: Double
            val eulerResults = Numericals.solveOdeByEulersMethod(eqn, h.toDouble(), interval, initY.toDouble())

            //are we displaying all answers or just the last iteration
            if (buttonText == resources.getString(R.string.solve)) {
                answerAtLastIterate = eulerResults[eulerResults.size - 1].y
                binding.tvAnswer.text = answerAtLastIterate.toString()

                //display the answer
                Utilities.animateAnswer(binding.tvAnswer, binding.root, Utilities.DisplayMode.SHOW)
                Utilities.animateAnswer(binding.tvAnswer, binding.root, Utilities.DisplayMode.SHOW)
            } else if (buttonText == resources.getString(R.string.show_iterations)) {
                findNavController().navigate(
                    FragmentEulerDirections.fragmentEulerResults(
                        eqn, x0, x1, h, initY.toFloat(),
                        eulerResults.toTypedArray()
                    )
                )
            }
            //replace the calculate button with show iterations so that clicking will show the iteration steps rather
            binding.btnCalculate.text = resources.getString(R.string.show_iterations)
        } catch (ex: NumberFormatException) {
            binding.tilUserInput.isErrorEnabled = true
            binding.tilUserInput.error = "One or more of the input expressions are invalid!"
            Timber.i("Error parsing one or more of the expressions")
        } catch (exception: Exception) {
            binding.tilUserInput.isErrorEnabled = true
            binding.tilUserInput.error = exception.message
        }
    }

    private fun checkForEmptyInput(): Boolean {
        var validated = true
        if (binding.textEquation.getText().toString().isEmpty()) {
            binding.tilUserInput.isErrorEnabled = true
            binding.tilUserInput.error = "Cannot be empty"
            validated = false
        }
        if (binding.textInitialY.getText().toString().isEmpty()) {
            binding.tilInitY.isErrorEnabled = true
            binding.tilInitY.error = "error"
            validated = false
        }
        if (binding.x0.getText().toString().isEmpty()) {
            binding.tilX0.isErrorEnabled = true
            binding.tilX0.error = "error"
            validated = false
        }
        if (binding.x1.getText().toString().isEmpty()) {
            binding.tilX1.isErrorEnabled = true
            binding.tilX1.error = "error"
            validated = false
        }
        if (binding.textH.getText().toString().isEmpty()) {
            binding.tilStepSize.isErrorEnabled = true
            binding.tilStepSize.error = "error"
            validated = false
        }
        return validated
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        onEquationChanged()
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        onEquationChanged()
    }

    override fun afterTextChanged(editable: Editable) {
        onEquationChanged()
    }

    private fun onEquationChanged() {
        binding.btnCalculate.text = resources.getString(R.string.solve)
        Utilities.animateAnswer(binding.tvAnswer, binding.root, Utilities.DisplayMode.HIDE)
    }

    companion object {
        private const val TAG = "FragmentEuler"
    }
}
