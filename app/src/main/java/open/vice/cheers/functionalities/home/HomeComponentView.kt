package open.vice.cheers.functionalities.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import open.vice.cheers.R
import open.vice.cheers.core.components.DatePickerDialogComponent
import open.vice.cheers.core.components.ExpandableComponentView
import open.vice.cheers.core.components.SearchBarView
import open.vice.cheers.core.extension.toDate
import open.vice.cheers.core.utils.TagConstants
import open.vice.cheers.functionalities.loading.BaseLoadingComponentView
import java.time.LocalDate

@Composable
fun HomeComponentView(viewModel: HomeComponentViewModel = viewModel()) {
    val stateHintText = remember {
        mutableStateOf(TextFieldValue())
    }

    BaseLoadingComponentView<HomeComponentViewModel>(viewModel) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                Image(
                    ImageBitmap.imageResource(id = R.drawable.ic_logo_top),
                    contentDescription = stringResource(R.string.logo_top),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                SearchFilterView(
                    onSearchEnd = {
                        viewModel.onSearchEnd(it)
                    },
                    stateHintText = stateHintText
                )
                DateFilterView(
                    expandableTag = TagConstants.DATE_FILTER_EXPANDABLE,
                    onStartDateSelected = {
                        viewModel.onStartDateChanged(it)
                    },
                    onEndDateSelected = {
                        viewModel.onEndDateChanged(it)
                    },
                    onFilterByDate = {
                        viewModel.onFilterByDateApplied()
                        stateHintText.value = TextFieldValue()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                PunkBeerListView()
            }
        }
    }
}

@Composable
fun SearchFilterView(
    stateHintText: MutableState<TextFieldValue>,
    onSearchEnd: (String) -> Unit,
) {
    ExpandableComponentView(
        text = stringResource(R.string.filter_result_by_name),
        clickableTag = TagConstants.SEARCH_FILTER_EXPANDABLE
    ) {
        SearchBarView(
            stateHintText = stateHintText,
            hint = stringResource(id = R.string.hint_search_bar),
        ) {
            onSearchEnd(it.text)
        }
    }
}

@Composable
fun DateFilterView(
    onStartDateSelected: ((LocalDate) -> Unit)? = null,
    onEndDateSelected: ((LocalDate) -> Unit)? = null,
    expandableTag: String = "",
    onFilterByDate: (() -> Unit)? = null
) {
    ExpandableComponentView(
        text = stringResource(R.string.or_search_by_brewed_date),
        clickableTag = expandableTag
    ) {
        Row(
            modifier = Modifier.padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateTextField(
                tagTextField = TagConstants.START_DATE_FILTER_TEXT_FIELD,
                label = stringResource(R.string.start_date), onValueChanged = {
                    onStartDateSelected?.invoke(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            DateTextField(
                label = stringResource(R.string.end_date), onValueChanged = {
                    onEndDateSelected?.invoke(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                tagTextField = TagConstants.END_DATE_FILTER_TEXT_FIELD
            )
            Button(
                onClick = { onFilterByDate?.invoke() },
                modifier = Modifier.testTag(TagConstants.BUTTON_DATE_FILTER)
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        }
    }
}

@Composable
fun DateTextField(
    label: String,
    onValueChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    tagTextField: String = ""
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }

    val placeholderText = stringResource(R.string.date_format)

    val showDialog = remember { mutableStateOf(false) }

    TextField(
        text.value,
        onValueChange = { text.value = it },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(label) },
        enabled = false,
        modifier = modifier.testTag(tagTextField).clickable {
            showDialog.value = true
        },
        placeholder = { Text(text = placeholderText) }
    )

    DatePickerDialogComponent(
        showDialog = showDialog.value,
        setDialogVisible = {
            showDialog.value = it
        },
        onDateSelected = { month, year ->
            val newDate = TextFieldValue("$month/$year")
            text.value = newDate
            onValueChanged(newDate.text.toDate())
        }
    )
}
