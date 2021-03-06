package com.example.projemanag.ui.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

fun alert(func: AlertRobot.() -> Unit) = AlertRobot().apply { func() }

class AlertRobot : BaseRobot() {
    private val yesButtonMatcher = withText("YES")
    private val noButtonMatcher = withText("NO")

    fun isAlertMessageDisplayed(message: String) {
        onView(withText(message))
            .check(matches(isDisplayed()))
    }
    fun tapOnYes() = tapOn(yesButtonMatcher)
    fun tapOnNo() = tapOn(noButtonMatcher)
}
