package open.vice.cheers.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun <T> InfiniteList(
    list: List<T>,
    contentPadding: PaddingValues,
    itemContent: @Composable() (LazyItemScope.(index: Int) -> Unit),
    tag: String,
    onLoadMore: () -> Unit,
    showLoadingItem: Boolean = false
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth().testTag(tag),
        contentPadding = contentPadding
    ) {
        items(list.size, itemContent = itemContent)

        item {
            if(showLoadingItem) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .height(20.dp)
                        .width(20.dp)) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    InfiniteListHandler(
        listState = listState,
        onLoadMore = onLoadMore
    )
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    onLoadMore: () -> Unit,
    buffer: Int = 2,
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { shouldLoadMore ->
                if(shouldLoadMore) {
                    onLoadMore()
                }
            }
    }
}