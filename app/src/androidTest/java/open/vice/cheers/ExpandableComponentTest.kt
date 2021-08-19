package open.vice.cheers

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import open.vice.cheers.core.components.ExpandableComponentView
import open.vice.cheers.ui.theme.CheersTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpandableComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_ExpandableShouldShowBeShowCorrectly() {
        val text = "Text to be shown"

        composeTestRule.setContent {
            CheersTheme {
                ExpandableComponentView(
                    text,
                    content = { Box{} },
                )
            }
        }

        composeTestRule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun test_ExpandableShouldShowByDefaultIfSet() {
        val contentText = "Hello there"

        composeTestRule.setContent {
            CheersTheme {
                ExpandableComponentView(text = "", defaultShowContent = true, content = {
                    Text(text = contentText)
                })
            }
        }

        composeTestRule
            .onNodeWithText(contentText)
            .assertExists()
    }

    @Test
    fun test_ExpandableShouldNotBeShownByDefault() {
        val contentText = "Hello there"

        composeTestRule.setContent {
            CheersTheme {
                ExpandableComponentView(text = "", content = {
                    Text(text = contentText)
                })
            }
        }

        composeTestRule
            .onNodeWithText(contentText)
            .assertDoesNotExist()
    }

    @Test
    fun test_ExpandableContentShouldByShownIfOpened() {
        val parentText = "Text to be shown"
        val contentText = "Hello there"

        composeTestRule.setContent {
            CheersTheme {
                ExpandableComponentView(parentText, content = {
                    Text(text = contentText)
                })
            }
        }

        composeTestRule
            .onNodeWithText(parentText)
            .performClick()

        composeTestRule
            .onNodeWithText(contentText)
            .assertExists()
    }
}