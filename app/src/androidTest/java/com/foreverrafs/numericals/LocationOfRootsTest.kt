package com.foreverrafs.numericals

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/* Created by Rafsanjani on 26/05/2020. */

@RunWith(AndroidJUnit4ClassRunner::class)
class LocationOfRootsTest {
    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun openLocationOfRootsMenu() {
        //navigate from main screen to sub-section
        navigateTo(1)
    }

    @Test
    fun testBisection() {
        //move to bisection screen
        navigateTo(0)

        typeText(R.id.text_equation, "x^5 + x^3 + 3x")
        typeText(R.id.x0, "-2")
        typeText(R.id.x1, "-1")
        typeText(R.id.etIterations, "1")

        closeSoftKeyboard()
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "-1.5")
    }

    @Test
    fun testNewtonRaphson() {
        //move to newtons screen
        navigateTo(1)

        typeText(R.id.text_equation, "x^5 + x^3+3")
        typeText(R.id.x0, "-1")
        typeText(R.id.etIterations, "20")

        closeSoftKeyboard()
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "-1.1052985460061695")
    }

    @Test
    fun testFalsePosition() {
        navigateTo(2)
        typeText(R.id.text_equation, "x^5 + x^3 +3")
        typeText(R.id.x0, "-2")
        typeText(R.id.x1, "-1")
        typeText(R.id.etIterations, "100")
        typeText(R.id.etTolerance, "0")

        closeSoftKeyboard()
        clickButton(R.id.btnCalculate)

        assertContainsText(R.id.tvAnswer, "-1.10529")
    }

    @Test
    fun testSecant() {
        navigateTo(3)
        typeText(R.id.text_equation, "x^3 + x^3 + 3")
        typeText(R.id.x0, "1.0")
        typeText(R.id.x1, "-1.0")
        typeText(R.id.etIterations, "800")
        closeSoftKeyboard()

        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "-1.144714")
    }


    private fun navigateTo(position: Int) {
        onView(ViewMatchers.withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(position, ViewActions.click())
                )
    }
}