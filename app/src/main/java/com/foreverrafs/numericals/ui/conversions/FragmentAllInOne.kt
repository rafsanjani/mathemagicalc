package com.foreverrafs.numericals.ui.conversions

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.databinding.FragmentConversionsAllinoneBinding
import com.foreverrafs.numericals.utils.Utilities
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber

/** Created by Aziz Rafsanjani on 11/4/2017. */
class FragmentAllInOne : Fragment(), TextWatcher {
    private lateinit var layoutAnswerArea: ConstraintLayout

    lateinit var btnCalculate: Button

    private lateinit var btnBack: Button

    private lateinit var tilUserInput: TextInputLayout

    private lateinit var tvBinary: TextView

    private lateinit var tvOctal: TextView

    private lateinit var tvHexadecimal: TextView

    lateinit var binding: FragmentConversionsAllinoneBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConversionsAllinoneBinding.inflate(inflater)
        initControls()
        return binding.root
    }

    private fun initControls() {
        tvHexadecimal = binding.tvAnswerHexadecimal
        tvOctal = binding.tvAnswerOctal
        tvBinary = binding.tvAnswerBinary
        tilUserInput = binding.tilUserInput
        btnBack = binding.btnBackToMainMenu
        layoutAnswerArea = binding.layoutAnswerArea;
        btnCalculate = binding.btnCalculate;


        binding.tilUserInput.setOnKeyListener { _: View?, i: Int, keyEvent: KeyEvent ->
            //take the error message away
            tilUserInput.isErrorEnabled = false
            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                onCalculate()
                return@setOnKeyListener true
            }
            false
        }
        binding.tilUserInput.editText?.addTextChangedListener(this)

        btnBack.setOnClickListener { onBackToMainMenu() }

        btnCalculate.setOnClickListener { onCalculate() }
    }

    private fun onBackToMainMenu() {
        (activity as MainActivity?)!!.goToMainMenu()
    }

    fun onCalculate() {
        val binary: String
        val octal: String
        val hexadecimal: String
        val decimal = tilUserInput.editText?.text.toString()

        if (decimal.isEmpty()) {
            tilUserInput.error = "Input field is empty"
            return
        }
        try {
            val decLong = decimal.toDouble()
            if (decLong <= 0) {
                tilUserInput.error = "Number should be greater than 0"
                return
            }
            binary = Numericals.decimalToBinary(decLong)
            octal = Numericals.decimalToOctal(decimal)
            hexadecimal = Numericals.decimalToHexadecimal(decimal)
            tvBinary.text = binary
            tvOctal.text = octal
            tvHexadecimal.text = hexadecimal
            Utilities.animateAnswer(
                layoutAnswerArea,
                binding.root, Utilities.DisplayMode.SHOW
            )
        } catch (ex: NumberFormatException) {
            Timber.e("cannot parse $decimal to an integer value : $ex")
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {
        if (editable.isEmpty()) {
            Utilities.animateAnswer(
                layoutAnswerArea,
                binding.root, Utilities.DisplayMode.HIDE
            )
        }
    }
}