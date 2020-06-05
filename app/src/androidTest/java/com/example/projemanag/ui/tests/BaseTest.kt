package com.example.projemanag.ui.tests

import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import com.example.projemanag.ui.robots.intro
import com.example.projemanag.ui.robots.signIn
import com.example.projemanag.ui.utils.getJsonValue
import com.example.projemanag.utils.Utils
import org.junit.After
import org.junit.Before
import java.util.concurrent.TimeUnit

open class BaseTest {

    private val testEmail = getJsonValue("email")
    private val testPassword = getJsonValue("password")
    val testBoard = "testBoard"
    val testList = "testList"
    val testCard = "testCard"

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(Utils.countingIdlingResource)
        IdlingPolicies.setIdlingResourceTimeout(60, TimeUnit.SECONDS)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(Utils.countingIdlingResource)
    }

    fun signInWithTestCreds() {
        intro {
            tapOnSignInButton()
        }

        signIn {
            typeInEmail(testEmail)
            typeInPassword(testPassword)
            tapOnSignInButton()
        }
    }
}
