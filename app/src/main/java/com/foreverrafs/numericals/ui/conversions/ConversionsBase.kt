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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.databinding.FragmentConversionsLayoutBinding
import com.foreverrafs.numericals.utils.Utilities
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Rafsanjani on 6/16/2019
 */
abstract class ConversionsBase : Fragment() {

    lateinit var inputLayout: TextInputLayout

    lateinit var txtHeader: TextView

    lateinit var txtDescription: TextView

    lateinit var etInput: TextInputEditText

    lateinit var tvAnswer: TextView

    private lateinit var methodName: String

    private lateinit var parentActivity: MainActivity

    protected lateinit var navController: NavController

    lateinit var binding: FragmentConversionsLayoutBinding

    protected lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConversionsLayoutBinding.inflate(inflater, container, false)

        with(binding) {
            rootView = root
            inputLayout = tilUserInput
            txtHeader = tvHeader
            txtDescription = tvDescription
            etInput = textUserInput
            this@ConversionsBase.tvAnswer = tvAnswer
        }

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initControls()
        navController = findNavController()

        parentActivity = activity as MainActivity
    }

    protected fun setHeader(header: String?) {
        txtHeader.text = header
    }

    private fun backToMainMenu(button: Button?) {
        parentActivity.goToMainMenu(button)
    }

    protected fun setDescription(description: String?) {
        txtDescription.text = description
    }

    fun showErrorMessage(errorMessage: String?, clearInput: Boolean) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = errorMessage

        if (clearInput)
            etInput.text?.clear()
    }

    private fun hideErrorMessage() {
        inputLayout.isErrorEnabled = false
    }

    fun setMethodName(value: String) {
        methodName = value
    }

    fun setInputHint(hint: String) {
        inputLayout.hint = hint
    }


    protected open fun initControls() {
        etInput.setOnKeyListener { _: View?, _: Int, keyEvent: KeyEvent ->
            //remove the error message from the input layout if any
            hideErrorMessage()

            if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                onCalculate()
                return@setOnKeyListener true
            }
            false
        }

        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.isEmpty()) {
                    Utilities.animateAnswer(rootView.findViewById(R.id.tvAnswer),
                            rootView.findViewById(R.id.parentContainer), Utilities.DisplayMode.HIDE)
                }
            }
        })

        binding.btnBackToMainMenu.setOnClickListener {
            backToMainMenu(binding.btnBackToMainMenu)
        }

        binding.btnCalculate.setOnClickListener {
            onCalculate()
        }

        binding.btnShowAlgo.setOnClickListener {
            parentActivity.showAlgorithm(navController, methodName)
        }
    }

    fun displayAnswer() {
        Utilities.animateAnswer(tvAnswer, rootView, Utilities.DisplayMode.SHOW)
    }

    protected abstract fun onCalculate()
}