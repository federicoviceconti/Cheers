package open.vice.cheers.functionalities.loading

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import open.vice.cheers.core.BaseViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import open.vice.cheers.functionalities.home.HomeComponentViewModel

@Composable
fun <T> BaseLoadingComponentView(viewModel: HomeComponentViewModel, content: @Composable () -> Unit) where T: BaseViewModel {
    val isLoading by viewModel.isLoading.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if(isLoading) {
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
            ) {
                Box(modifier = Modifier.fillMaxSize())
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(40.dp)
                        .height(40.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
