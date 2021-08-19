package open.vice.cheers

import androidx.activity.viewModels
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import open.vice.cheers.core.utils.TagConstants
import open.vice.cheers.functionalities.home.HomeComponentView
import open.vice.cheers.functionalities.home.HomeComponentViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeComponentTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.setContent {
            HomeComponentView(
                composeTestRule.activity.viewModels<HomeComponentViewModel>().value
            )
        }
    }

    @Test
    fun test_DateFilterComponentExists() {
        composeTestRule.onNodeWithTag(TagConstants.DATE_FILTER_EXPANDABLE).assertExists()
    }

    @Test
    fun test_ShouldSetExactDate() {
        composeTestRule.onNodeWithTag(TagConstants.DATE_FILTER_EXPANDABLE).performClick()

        composeTestRule.onNodeWithTag(TagConstants.START_DATE_FILTER_TEXT_FIELD).performClick()
        composeTestRule.waitUntil(1000) { true }

        composeTestRule.onNodeWithTag(TagConstants.DROPDOWN_MENU_YEAR).performClick()
        composeTestRule.waitUntil(1000) { true }
        composeTestRule.onNodeWithText("1996").performClick()

        composeTestRule.onNodeWithTag(TagConstants.DROPDOWN_MENU_MONTH).performClick()
        composeTestRule.waitUntil(1000) { true }
        composeTestRule.onNodeWithText("2").performClick()

        composeTestRule.onNodeWithTag(TagConstants.OK_ALERT_DIALOG).performClick()
        composeTestRule.waitUntil(2000) { true }

        composeTestRule
            .onNodeWithTag(TagConstants.START_DATE_FILTER_TEXT_FIELD)
            .assert(hasText("02/1996"))
    }

    @Test
    fun test_ShouldContainsSearchedTextIntoItems() {
        val textToSearch = "Candy Skull Again"

        composeTestRule.onNodeWithTag(TagConstants.SEARCH_FILTER_EXPANDABLE).performClick()

        composeTestRule
            .onNodeWithTag(TagConstants.SEARCH_FILTER_TEXT_FIELD)
            .performTextInput(textToSearch)

        composeTestRule.waitUntil(1000) { true }

        composeTestRule
            .onNodeWithTag(TagConstants.PUNK_BEER_LIST)
            .onChildren()
            .onFirst()
            .onChildren()
            .assertAny(hasText(textToSearch, ignoreCase = true))
    }
}