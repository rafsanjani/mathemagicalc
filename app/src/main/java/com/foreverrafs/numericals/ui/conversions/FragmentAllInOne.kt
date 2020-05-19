package com.foreverrafs.numericals.ui.conversions

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.utils.Utilities
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentAllInOne : Fragment(), TextWatcher {
    @BindView(R.id.layout_answer_area)
    lateinit var layoutAnswerArea: ConstraintLayout

    @BindView(R.id.btnCalculate)
    lateinit var btnCalculate: Button


    @BindView(R.id.btnBackToMainMenu)
    lateinit var btnBack: Button

    @BindView(R.id.til_user_input)
    lateinit var tilUserInput: TextInputLayout

    @BindView(R.id.tvAnswerBinary)
    lateinit var tvBinary: TextView

    @BindView(R.id.tvAnswerOctal)
    lateinit var tvOctal: TextView


    @BindView(R.id.tvAnswerHexadecimal)
    lateinit var tvHexadecimal: TextView

    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_conversions_allinone, container, false)
        ButterKnife.bind(this, rootView)
        initControls()
        return rootView
    }

    private fun initControls() {
        val etInput = tilUserInput.editText
        etInput!!.setOnKeyListener { view: View?, i: Int, keyEvent: KeyEvent ->
            //take the error message away
            tilUserInput.isErrorEnabled = false
            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                onCalculate()
                return@setOnKeyListener true
            }
            false
        }
        etInput.addTextChangedListener(this)
    }

    @OnClick(R.id.btnBackToMainMenu)
    fun onBackToMainMenu(button: Button?) {
        (activity as MainActivity?)!!.goToMainMenu(button)
    }

    @OnClick(R.id.btnCalculate)
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
            Utilities.animateAnswer(layoutAnswerArea,
                    rootView, Utilities.DisplayMode.SHOW)
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "cannot parse $decimal to an integer value")
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {
        if (editable.isEmpty()) {
            Utilities.animateAnswer(layoutAnswerArea,
                    rootView, Utilities.DisplayMode.HIDE)
        }
    }

    companion object {
        private const val TAG = "FragmentAllInOne"
    }
}