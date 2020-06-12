package com.example.projemanag.ui.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.projemanag.activities.IntroActivity
import com.example.projemanag.ui.robots.alert
import com.example.projemanag.ui.robots.board
import com.example.projemanag.ui.robots.createBoard
import com.example.projemanag.ui.robots.main
import com.example.projemanag.ui.utils.DataPrep.createBoardToBeDeleted
import com.example.projemanag.ui.utils.dateInMills
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BoardTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityTestRule(IntroActivity::class.java)
    private val emptyBoardName = ""
    private val newBoardName = "${dateInMills()}_Board"
    private val boardToBeDeleted = "${dateInMills()}_DeleteBoard"
    private val pleaseEnterABoardNameMessage = "Please enter a Board name"
    private val deleteBoardAlertMessage =
        "Are you sure you want to delete board '$boardToBeDeleted'?"

    @Test
    fun createBoardWithEmptyName() {
        signInWithTestCreds()

        main {
            tapOnfabCreateBoard()
        }

        createBoard {
            typeInBoardNameField(emptyBoardName)
            tapOnCreateButton()
            snackbarIsDisplayed()
            Assert.assertEquals(pleaseEnterABoardNameMessage, getSnackbarText())
        }
    }

    @Test
    fun createBoard() {
        signInWithTestCreds()

        main {
            tapOnfabCreateBoard()
        }

        createBoard {
            typeInBoardNameField(newBoardName)
            tapOnCreateButton()
        }

        main {
            isBoardDisplayed(newBoardName)
        }
    }

    @Test
    fun deleteBoard() {
        var numberOfBoards: Int? = null
        createBoardToBeDeleted(boardToBeDeleted)
        signInWithTestCreds()

        main {
            numberOfBoards = getNumberOfBoards()
            tapOnBoard(boardToBeDeleted)
        }

        board {
            tapOnDeleteBoard()
        }

        alert {
            isAlertMessageDisplayed(deleteBoardAlertMessage)
            tapOnYes()
        }

        main {
            val newNumberOfBoards = getNumberOfBoards()
            Assert.assertEquals(numberOfBoards?.minus(1), newNumberOfBoards)
        }
    }
}
