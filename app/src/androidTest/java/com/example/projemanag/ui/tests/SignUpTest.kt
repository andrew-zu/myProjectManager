package com.example.projemanag.ui.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.projemanag.activities.SplashActivity
import com.example.projemanag.ui.robots.intro
import com.example.projemanag.ui.robots.signUp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUpTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityTestRule(SplashActivity::class.java)
    private val invalidName = "invalidName"
    private val validName = "validName"
    private val invalidEmail = "invalidEmail"
    private val validEmail = "validEmail@gmail.com"
    private val invalidPassword = "invalidPassword"
    private val validPassword = "validPassword"
    private val toastRegFailed = "Registration failed"
    private val toastRegSuccess = "you have successfully registered"

    @Test
    fun signUpWithInvalidCreds() {
        intro {
            tapOnSignUpButton()
        }

        signUp {
            typeInName(invalidName)
            typeInEmail(invalidEmail)
            typeInPassword(invalidPassword)
            clickSignUpButton()
            toastWithTextIsDiplayed(toastRegFailed)
        }
    }

    @Test
    fun signUpWithValidCreds() {
        intro {
            tapOnSignUpButton()
        }

        signUp {
            typeInName(validName)
            typeInEmail(validEmail)
            typeInPassword(validPassword)
            clickSignUpButton()
            toastWithTextIsDiplayed(toastRegSuccess)
        }
    }
}