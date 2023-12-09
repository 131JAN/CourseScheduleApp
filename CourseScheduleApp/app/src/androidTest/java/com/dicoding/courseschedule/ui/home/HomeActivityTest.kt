package com.dicoding.courseschedule.ui.home

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Before
    fun setUp() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            HomeActivity::class.java
        )
        ActivityScenario.launch<HomeActivity>(intent)
    }

    @Test
    fun testAddCourseMenuClick() {
        // Click on the "Add Course (+)" menu item
        onView(withId(R.id.action_add)).perform(click())

        // Verify that AddCourseActivity is displayed
        onView(withId(R.id.ed_course_name)).check(matches(isDisplayed()))
    }
}
