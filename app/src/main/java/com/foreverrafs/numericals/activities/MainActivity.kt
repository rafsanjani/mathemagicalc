package com.foreverrafs.numericals.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import butterknife.ButterKnife
import butterknife.OnClick
import com.foreverrafs.numericals.BuildConfig
import com.foreverrafs.numericals.R
import com.foreverrafs.numericals.fragments.FragmentShowAlgorithm
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.about_dialog.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar as Toolbar, navController)
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet!!)
        tvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
        enableStrictMode()
    }

    fun toggleBottomSheet() {
        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        else sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    fun goToMainMenu(button: Button?) {
        val controller = Navigation.findNavController(button!!)
        val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_left)
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.nav_graph, true)
                .setExitAnim(R.anim.slide_out_right)
                .build()
        controller.navigate(R.id.fragment_main_menu, null, navOptions, null)
    }

    fun showAlgorithm(navController: NavController, methodName: String?) {
        val navOptions = NavOptions.Builder()
                .build()
        val bundle = Bundle()
        bundle.putString(FragmentShowAlgorithm.EXTRA_METHOD_NAME, methodName)
        navController.navigate(R.id.show_algorithm, bundle, navOptions, null)
    }

    override fun onBackPressed() {
        if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) toggleBottomSheet() else super.onBackPressed()
    }

    //tvWebsite is a textview on the bottomsheet
    @OnClick(R.id.tvWebsite)
    fun onWebsiteClicked(url: TextView) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url.text.toString())
        startActivity(intent)
    }

    @OnClick(R.id.btnAboutClose)
    fun onAboutClose() {
        toggleBottomSheet()
    }

    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
            val policy = StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            StrictMode.setThreadPolicy(policy)
            val vmPolicy = VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            StrictMode.setVmPolicy(vmPolicy)
        }
    }
}