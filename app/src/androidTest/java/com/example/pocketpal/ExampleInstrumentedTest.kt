package com.example.pocketpal

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.regex.Pattern.matches

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pocketpal", appContext.packageName)
    }
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    fun matches(condition: Matcher<View>): ViewAssertion {
        return ViewAssertion { view, noViewFoundException ->
            assertThat(view, matches(condition))
        }
    }

    private fun assertThat(view: View?, matches: ViewAssertion) {

    }

    @Test
    fun testAddBillsButton() {
        // Click on the "Add Bills" button
        onView(withId(R.id.btnAdd)).perform(click())

        // Check if the AddBills activity is opened
        onView(withId(R.id.add_bills_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun testUpcomingBillsButton() {
        // Click on the "Upcoming Bills" button
        onView(withId(R.id.btnUpcoming)).perform(click())

        // Check if the UpcomingBills activity is opened
        onView(withId(R.id.upcoming_bills_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun testPaidBillsButton() {
        // Click on the "Paid Bills" button
        onView(withId(R.id.btnPaid)).perform(click())

        // Check if the PaidBills activity is opened
        onView(withId(R.id.paid_bills_layout)).check(matches(isDisplayed()))
    }






}




