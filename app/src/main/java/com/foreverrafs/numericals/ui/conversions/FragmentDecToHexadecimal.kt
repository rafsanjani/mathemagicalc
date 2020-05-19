package com.foreverrafs.numericals.ui.conversions

import android.util.Log
import butterknife.OnClick
import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R

//import com.ms.square.android.expandabletextview.ExpandableTextView;
/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentDecToHexadecimal : ConversionsBase() {
    override fun initControls() {
        super.initControls()
        setHeader(getString(R.string.dec_to_hexadecimal))
        setDescription(getString(R.string.decimal_to_hexadecimal_desc))
        setInputHint(getString(R.string.decimal_int_input_hint))
        setMethodName("dectohexa")
    }

    @OnClick(R.id.btnShowAlgo)
    fun showAlgo() {
        parentActivity.showAlgorithm(navController, "dectohexa")
    }

    override fun onCalculate() {
        val decimal = etInput.text.toString()
        if (decimal.isEmpty()) {
            showErrorMessage("Input cannot be empty", false)
            return
        }
        try {
            val decLong = decimal.toDouble()
            if (decLong <= 0) {
                showErrorMessage("Input must be greater than 0!", false)
                return
            }
            val hexadecimal = Numericals.decimalToHexadecimal(decimal)
            tvAnswer.text = hexadecimal
            displayAnswer()
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "cannot parse $decimal to an integer value")
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    companion object {
        private const val TAG = "FragmentDecToHexadecima"
    }
}