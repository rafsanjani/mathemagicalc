package com.foreverrafs.numericals.ui.conversions

import android.util.Log
import butterknife.OnClick
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.utils.Utilities

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentDecToBinInt : ConversionsBase() {
    override fun initControls() {
        super.initControls()
        setHeader(getString(R.string.dec_to_bin_integers))
        setDescription(getString(R.string.dec_to_bin_int_desc))
        setInputHint(getString(R.string.decimal_int_input_hint))
        setMethodName("dectobinint")
    }

    @OnClick(R.id.btnShowAlgo)
    fun showAlgo() {
        parentActivity.showAlgorithm(navController, "dectobinint")
    }

    override fun onCalculate() {
        val decimal = etInput.text.toString()
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false)
            return
        }
        try {
            val decInt = decimal.toInt()
            if (decInt < 1) {
                showErrorMessage("Should be equal to or greater than 1", false)
                return
            }
            val binary = Numericals.decimalIntToBinary(decInt)
            Log.i(TAG, "" + binary.length)
            tvAnswer.text = binary
            Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.SHOW)
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "cannot parse $decimal to a double value")
            showErrorMessage("Input isn't an integer", false)
            displayAnswer()
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    companion object {
        private const val TAG = "FragmentDecToBinInt"
    }
}