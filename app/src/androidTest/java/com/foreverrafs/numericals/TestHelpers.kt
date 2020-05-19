package com.foreverrafs.numericals

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers


/* Created by Rafsanjani on 19/05/2020. */

fun clearText(viewId: Int) {
    Espresso.onView(ViewMatchers.withId(viewId)).perform(ViewActions.clearText())
}

fun typeText(viewId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(viewId)).perform(ViewActions.typeText(text))
}

fun clickButton(viewId: Int) {
    Espresso.onView(ViewMatchers.withId(viewId)).perform(ViewActions.click())
}

fun assertContainsText(viewId: Int, expected: String) {
    Espresso.onView(ViewMatchers.withId(viewId)).check(ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.containsString(expected))))
}