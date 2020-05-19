package com.foreverrafs.numericals.ui.conversions

import android.util.Log
import butterknife.OnClick
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentDecToBinFrac : ConversionsBase() {
    private var rawBinary: String? = null
    override fun initControls() {
        super.initControls()
        setHeader(getString(R.string.dec_to_bin_fractions))
        setDescription(getString(R.string.dec_to_bin_frac_desc))
        setInputHint(getString(R.string.decimal_frac_input_hint))
        setMethodName("dectobinfrac")
    }

    @OnClick(R.id.btnShowAlgo)
    fun showAlgo() {
        parentActivity.showAlgorithm(navController, "dectobinfrac")
    }

    override fun onCalculate() {
        val decimal = etInput.text.toString()
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false)
            return
        }
        try {
            val decDouble = decimal.toDouble()
            if (decDouble >= 1) {
                showErrorMessage("Must be less than 1", false)
                return
            }
            if (decDouble == 0.0) {
                showErrorMessage("Cannot be zero", false)
                return
            }

            //keep a reference in case user wants to display all
            rawBinary = Numericals.decimalFractionToBinary(decDouble)
            tvAnswer.text = rawBinary
            displayAnswer()
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "cannot parse $decimal to a double value")
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    companion object {
        private const val TAG = "FragmentDecToBinFrac"
    }
}