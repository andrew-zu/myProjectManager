package com.example.projemanag.ui.utils

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.Matcher

fun getTextWithMatcher(matcher: Matcher<View>): String? {
    var text: String? = null
    onView(matcher).perform(object : ViewAction {
        override fun getConstraints() = isA(View::class.java)

        override fun getDescription() = "Get text from View: $text"

        override fun perform(uiController: UiController, view: View) {
            val actualText = view as TextView
            text = actualText.text.toString()
        }
    })
    return text
}

fun getColorWithMatcher(matcher: Matcher<View>): Int? {
    var color: Int? = null
    onView(matcher).perform(object : ViewAction {
        override fun getConstraints() = isA(View::class.java)

        override fun getDescription() = "Get color from View: $color"

        override fun perform(uiController: UiController, view: View) {
            val cd: ColorDrawable = view.background as ColorDrawable
            color = cd.color
        }
    })
    return color
}
