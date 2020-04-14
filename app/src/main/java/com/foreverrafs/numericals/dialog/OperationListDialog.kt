package com.foreverrafs.numericals.dialog

import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.adapter.MethodListAdapter
import com.foreverrafs.numericals.ui.conversions.*
import com.foreverrafs.numericals.ui.interpolation.FragmentLagrange
import com.foreverrafs.numericals.ui.location_of_roots.FragmentBisection
import com.foreverrafs.numericals.ui.location_of_roots.FragmentFalsePosition
import com.foreverrafs.numericals.ui.location_of_roots.FragmentNewtonRaphson
import com.foreverrafs.numericals.ui.location_of_roots.FragmentSecante
import com.foreverrafs.numericals.ui.menus.FragmentODEMenu
import com.foreverrafs.numericals.ui.sys_of_equations.*

class OperationListDialog : DialogFragment(), OnItemClickListener {
    private var myContext: FragmentActivity? = null
    private var rootView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        rootView = inflater.inflate(R.layout.dialog_goto_operation, null)
        onInit()
        return rootView
    }

    private fun onInit() {
        val operationStringList = activity.resources.getStringArray(R.array.main_menu_legacy)
        val operationList = rootView!!.findViewById<ListView>(R.id.operationList)
        val operationListAdapter: ArrayAdapter<String> = MethodListAdapter(activity, R.layout.list_item,
                operationStringList)
        operationList.adapter = operationListAdapter
        operationList.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        var fragment: Fragment? = null
        when (position + 1) {
            1 -> fragment = FragmentDecToBinFrac()
            2 -> fragment = FragmentDecToBinInt()
            3 -> fragment = FragmentDecToBin()
            4 -> fragment = FragmentBinToDec()
            5 -> fragment = FragmentDecToHexadecimal()
            6 -> fragment = FragmentDecToOctal()
            7 -> fragment = FragmentBisection()
            8 -> fragment = FragmentNewtonRaphson()
            9 -> fragment = FragmentFalsePosition()
            10 -> fragment = FragmentSecante()
            11 -> fragment = FragmentGaussianPartial3x3()
            12 -> fragment = FragmentGaussianComplete3x3()
            13 -> fragment = FragmentGaussianPartial4x4()
            14 -> fragment = FragmentGaussianComplete4x4()
            15 -> fragment = FragmentGaussSeidel()
            16 -> fragment = FragmentGaussSeidelWithSOR()
            17 -> fragment = FragmentJacobi()
            18 -> fragment = FragmentODEMenu()
            19 -> fragment = FragmentLagrange()
        }

        // Utilities.replaceFragment(fragment, myContext.getSupportFragmentManager(), R.id.fragmentContainer);
        dismiss()
    }
}