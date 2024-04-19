package com.foreverrafs.numericals.ui.menus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter
import com.foreverrafs.numericals.databinding.FragmentMainMenuBinding
import com.foreverrafs.numericals.model.OperationMenu
import com.foreverrafs.numericals.utils.Constants

class FragmentMainMenu : Fragment() {
    private var navController: NavController? = null

    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController(view)

        val mainMenuItems = binding.contentMenu.listMainMenu

        mainMenuItems.setLayoutManager(GridLayoutManager(context, 2))
        val operations: MutableList<OperationMenu> = ArrayList()
        operations.add(OperationMenu("Conversion", R.drawable.button_normal_nc, Constants.MENU_NUMBER_CONVERSION))
        operations.add(OperationMenu("Location of Roots", R.drawable.button_normal_locationofroots, Constants.MENU_LOCATION_OF_ROOTS))
        operations.add(OperationMenu("Sys. of Eqns", R.drawable.button_normal_system_of_eqns_gaussian3x3, Constants.MENU_SYSTEM_OF_EQUATIONS))
        operations.add(OperationMenu("Ord. Diff. Eqns", R.drawable.button_normal_ordinary_diff_eqns, Constants.MENU_ODE))
        operations.add(OperationMenu("Interpolation", R.drawable.button_normal_interpolation, Constants.MENU_INTERPOLATION))
        operations.add(OperationMenu("Algorithms", R.drawable.button_normal_algorithms, Constants.MENU_ALGORITHMS))
        operations.add(OperationMenu("About", R.drawable.button_normal_about, Constants.MENU_ABOUT))
        binding.contentMenu.tvHeader.setText(R.string.operations)
        val adapter = OperationsMenuAdapter(operations)
        mainMenuItems.setHasFixedSize(true)
        mainMenuItems.setAdapter(adapter)

        adapter.setOnItemClickListenener { menuItemType: Int ->
            var directions: NavDirections? = null
            when (menuItemType) {
                Constants.MENU_NUMBER_CONVERSION -> directions = FragmentMainMenuDirections.fragmentConversionMenu()
                Constants.MENU_LOCATION_OF_ROOTS -> directions = FragmentMainMenuDirections.fragmentLocationOfRootsMenu()
                Constants.MENU_ALGORITHMS -> directions = FragmentMainMenuDirections.fragmentShowAlgorithm()
                Constants.MENU_ABOUT -> {
                    //toggle bottom sheet here
                    (requireActivity() as MainActivity).toggleBottomSheet()
                    return@setOnItemClickListenener
                }

                Constants.MENU_ODE -> directions = FragmentMainMenuDirections.fragmentODEMenu()
                Constants.MENU_SYSTEM_OF_EQUATIONS -> directions = FragmentMainMenuDirections.fragmentSysOfEquationsMenu()
                Constants.MENU_INTERPOLATION -> directions = FragmentMainMenuDirections.fragmentInterpolationMenu()
            }
            if (directions != null) navController!!.navigate(directions)
        }
    }
}
