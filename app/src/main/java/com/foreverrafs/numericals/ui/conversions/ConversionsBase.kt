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
import androidx.navigation.Navigation
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.utils.Utilities
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Rafsanjani on 6/16/2019
 */
abstract class ConversionsBase : Fragment() {

    @BindView(R.id.til_user_input)
    lateinit var inputLayout: TextInputLayout


    @BindView(R.id.tvHeader)
    lateinit var txtHeader: TextView


    @BindView(R.id.tvDescription)
    lateinit var txtDescription: TextView

    @BindView(R.id.text_user_input)
    lateinit var etInput: TextInputEditText

    @BindView(R.id.tvAnswer)
    lateinit var tvAnswer: TextView

    private lateinit var methodName: String

    protected lateinit var parentActivity: MainActivity
    protected lateinit var navController: NavController

    protected lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_conversions_layout, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initControls()
        navController = Navigation.findNavController(view)

        parentActivity = activity as MainActivity
    }

    protected fun setHeader(header: String?) {
        txtHeader.text = header
    }

    @OnClick(R.id.btnBackToMainMenu)
    fun backToMainMenuPressed(button: Button?) {
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

    @OnClick(R.id.btnCalculate)
    fun onCalculateClicked() {
        onCalculate()
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
    }

    fun displayAnswer() {
        Utilities.animateAnswer(tvAnswer,
                rootView, Utilities.DisplayMode.SHOW)
    }

    protected abstract fun onCalculate()
}