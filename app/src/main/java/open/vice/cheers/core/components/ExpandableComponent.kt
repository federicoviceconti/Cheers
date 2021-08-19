package open.vice.cheers.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import open.vice.cheers.R

@Composable
fun ExpandableComponentView(
    text: String,
    defaultShowContent: Boolean = false,
    clickableTag: String = "",
    content: @Composable () -> Unit,
) {
    val showContent = remember {
        mutableStateOf(defaultShowContent)
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(clickableTag)
                .clickable {
                    showContent.value = !showContent.value
                }
        ) {
            Text(text, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
            Image(
                bitmap = ImageBitmap
                    .imageResource(
                        id = android.R.drawable.arrow_down_float),
                contentDescription = stringResource(
                    R.string.arrow
                ),
                colorFilter = ColorFilter.tint(
                    if(isSystemInDarkTheme()) Color.White else Color.Black
                ),
                modifier = Modifier.rotate(
                    if(showContent.value) 0f else -90f
                )
            )
        }

        if(showContent.value) {
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
    }
}