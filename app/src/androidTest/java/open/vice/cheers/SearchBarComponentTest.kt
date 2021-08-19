package open.vice.cheers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import open.vice.cheers.core.components.SearchBarView
import open.vice.cheers.ui.theme.CheersTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBarComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_ShouldTextChange() {
        val oldValue = "old value"
        val newValue = "new Value"
        val stateHintText = mutableStateOf(TextFieldValue(oldValue))

        composeTestRule.setContent {
            CheersTheme {
                SearchBarView(
                    stateHintText = stateHintText
                )
            }
        }

        composeTestRule.onNodeWithText(oldValue).assertExists()
        composeTestRule.onNodeWithText(newValue).assertDoesNotExist()

        stateHintText.value = TextFieldValue(newValue)

        composeTestRule.onNodeWithText(newValue).assertExists()
        composeTestRule.onNodeWithText(oldValue).assertDoesNotExist()
    }

    @Test
    fun test_ShouldPlaceholderExists() {
        val placeholder = "Lorem ipsum"

        composeTestRule.setContent {
            CheersTheme {
                SearchBarView(
                    hint = placeholder
                )
            }
        }

        composeTestRule
            .onNodeWithText(placeholder)
            .assertExists()
    }
}