package com.foreverrafs.numericals.ui.conversions

import com.foreverrafs.core.Numericals
import com.foreverrafs.numericals.R
import timber.log.Timber

/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */
class FragmentDecToBin : ConversionsBase() {
    override fun initControls() {
        super.initControls()
        setHeader(getString(R.string.dec_to_bin_any_number))
        setDescription(getString(R.string.decimal_to_bin_desc))
        setInputHint(getString(R.string.enter_decimal))
        setMethodName("dectobin")
    }

    override fun onCalculate() {
        val decimal = etInput.text.toString()
        if (decimal.isEmpty()) {
            showErrorMessage(getString(R.string.errormsg_empty_input), false)
            return
        }
        try {
            val decLong = decimal.toDouble()
            if (decLong <= 0) {
                showErrorMessage(getString(R.string.errormsg_greater_than_zero), false)
                return
            }
            val rawBinary = Numericals.decimalToBinary(decLong)
            tvAnswer.text = rawBinary
            displayAnswer()
        } catch (ex: NumberFormatException) {
            Timber.e("cannot parse $decimal to an integer value")
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}