package com.foreverrafs.numericals

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.foreverrafs.numericals.activities.MainActivity
import com.foreverrafs.numericals.adapter.OperationsMenuAdapter
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/* Created by Rafsanjani on 19/05/2020. */

@RunWith(AndroidJUnit4ClassRunner::class)
class ConversionTest {
    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun openConversionMenu() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(0, click())
                )
    }

    @Test
    fun test_Conversion_FractionDecimalToBinary() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(0, click())
                )

        typeText(R.id.text_user_input, "0.890")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, ".11100011110101110000")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "0.11")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, ".00011100001")
    }

    @Test
    fun test_Conversion_IntegerDecimalToBinary() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(1, click())
                )

        typeText(R.id.text_user_input, "10")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "1010")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "7")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "111")
    }

    @Test
    fun test_Conversion_MixedDecimalToBinary() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(2, click())
                )

        typeText(R.id.text_user_input, "25.3")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "11001.010011001100110011001100")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "19")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "10011.0")
    }

    @Test
    fun test_Conversion_BinaryToDecimal() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(3, click())
                )

        typeText(R.id.text_user_input, "1010")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "10.0")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "0111")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "7")
    }

    @Test
    fun test_Conversion_DecimalToHexadecimal() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(4, click())
                )

        typeText(R.id.text_user_input, "20")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "14")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "40961")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "A001")
    }

    @Test
    fun test_Conversion_DecimalToOctal() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(5, click())
                )

        typeText(R.id.text_user_input, "25")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "31")

        clearText(R.id.text_user_input)

        typeText(R.id.text_user_input, "2563")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswer, "5003")
    }

    @Test
    fun test_Conversion_AllInOneConverter() {
        onView(withId(R.id.list_main_menu))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<OperationsMenuAdapter.MenuViewHolder>(6, click())
                )

        typeText(R.id.text_user_input, "10")
        clickButton(R.id.btnCalculate)
        assertContainsText(R.id.tvAnswerBinary, "1010")
        assertContainsText(R.id.tvAnswerHexadecimal, "A")
        assertContainsText(R.id.tvAnswerOctal, "12")
    }
}