package open.vice.cheers.functionalities.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.glide.GlideImage
import open.vice.cheers.R
import open.vice.cheers.core.components.InfiniteList
import open.vice.cheers.core.extension.toDateFormatted
import open.vice.cheers.core.utils.TagConstants
import open.vice.cheers.repositories.model.Beer

@Composable
fun PunkBeerListView(viewModel: HomeComponentViewModel = viewModel()) {
    val beerList = viewModel.filteredBeerList.observeAsState(initial = listOf())
    val isScrollLoading = viewModel.isScrollLoading.observeAsState(initial = false)

    if (beerList.value.isEmpty()) {
        NoItemView()
    } else {
        InfiniteList(
            tag = TagConstants.PUNK_BEER_LIST,
            list = beerList.value,
            contentPadding = PaddingValues(
                bottom = 8.dp,
                end = 16.dp,
                start = 16.dp
            ),
            itemContent = {
                if(it < beerList.value.size) {
                    BeerListItem(beerList.value[it])
                }
            },
            onLoadMore = {
                viewModel.onScrollEnd()
            },
            showLoadingItem = isScrollLoading.value
        )
    }
}



@OptIn(ExperimentalUnitApi::class)
@Composable
fun BeerListItem(beer: Beer) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = beer.url,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp),
                placeHolder = ImageVector.vectorResource(R.drawable.ic_placeholder_beer),
                error = ImageVector.vectorResource(R.drawable.ic_error_beer),
                contentDescription = beer.name
            )
            Column {
                Text(
                    beer.name,
                    fontSize = TextUnit(18f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    beer.tagLine,
                    fontSize = TextUnit(12f, type = TextUnitType.Sp)
                )
                Text(
                    beer.firstBrewed.toDateFormatted(),
                    fontSize = TextUnit(12f, type = TextUnitType.Sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    beer.description,
                    fontSize = TextUnit(14f, type = TextUnitType.Sp),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun NoItemView() {
    Text(stringResource(R.string.no_items_found))
}