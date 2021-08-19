package open.vice.cheers.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import open.vice.cheers.core.utils.TagConstants

@Composable
@Preview
fun SearchBarView(
    stateHintText: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    hint: String = "",
    onEditChange: ((TextFieldValue) -> Unit)? = null,
) {
    TextField(
        value = stateHintText.value,
        onValueChange = {
            onEditChange?.invoke(it)
            stateHintText.value = it
        },
        modifier = Modifier.fillMaxWidth().testTag(TagConstants.SEARCH_FILTER_TEXT_FIELD),
        placeholder = { Text(hint) },
    )
}