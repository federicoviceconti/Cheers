package open.vice.cheers.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import open.vice.cheers.R
import open.vice.cheers.core.utils.TagConstants
import java.time.LocalDate

fun getMonthList() = (1..12).toList()

fun getYearList() = (1990..LocalDate.now().year).toList()

@Composable
fun DatePickerDialogComponent(
    showDialog: Boolean,
    setDialogVisible: (Boolean) -> Unit,
    onDateSelected: (month: String, year: String) -> Unit
) {
    if (showDialog) {
        val yearList = getYearList()
        val monthList = getMonthList()

        val yearSelected = remember {
            mutableStateOf(yearList.indexOf(LocalDate.now().year))
        }

        val monthSelected = remember {
            mutableStateOf(monthList.indexOf(LocalDate.now().month.value))
        }

        AlertDialog(
            onDismissRequest = {
                setDialogVisible(false)
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.select_a_date)
                )
            },
            text = {
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        Column {
                            Text(stringResource(R.string.year), fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            DatePickerItemDropdownMenu(
                                initialIndex = yearSelected.value,
                                itemList = yearList,
                                onItemSelected = {
                                    yearSelected.value = it
                                },
                                tag = TagConstants.DROPDOWN_MENU_YEAR
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Column {
                            Text(stringResource(R.string.month), fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            DatePickerItemDropdownMenu(
                                initialIndex = monthSelected.value,
                                itemList = monthList,
                                onItemSelected = {
                                    monthSelected.value = it
                                },
                                tag = TagConstants.DROPDOWN_MENU_MONTH
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    setDialogVisible(false)

                    val yearToSet = yearList[yearSelected.value]
                    val monthToSet = monthList[monthSelected.value]
                    onDateSelected(
                        monthToSet.toString().padStart(2, '0'),
                        yearToSet.toString()
                    )
                }, modifier = Modifier.testTag(TagConstants.OK_ALERT_DIALOG)) {
                    Text(stringResource(id = android.R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = {
                    setDialogVisible(false)
                }) {
                    Text(stringResource(id = android.R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun DatePickerItemDropdownMenu(
    initialIndex: Int,
    itemList: List<Int>,
    onItemSelected: (Int) -> Unit,
    tag: String
) {
    val selectedIndex = remember { mutableStateOf(initialIndex) }
    val isMenuExpanded = remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier.testTag(tag).clickable(
                onClick = { isMenuExpanded.value = true }
            )
        ) {
            Text(
                itemList[selectedIndex.value].toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )
        }
        DropdownMenu(
            expanded = isMenuExpanded.value,
            onDismissRequest = { isMenuExpanded.value = false },
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            itemList.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    isMenuExpanded.value = false

                    onItemSelected(selectedIndex.value)
                }) {
                    Text(
                        text = text.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}