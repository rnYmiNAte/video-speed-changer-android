package com.dola.videospeedchanger.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dola.videospeedchanger.R
import com.dola.videospeedchanger.presentation.main.MainActivity
import org.junit.Test

class MainActivityTest {

    @Test
    fun `activity launches and shows title`() {
        // Arrange
        ActivityScenario.launch(MainActivity::class.java)

        // Assert
        onView(withId(R.id.tvAppName)) // Update ID if needed to match your layout
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.app_name)))
        onView(withId(R.id.rvVideos))
            .check(matches(isDisplayed()))
    }
}
