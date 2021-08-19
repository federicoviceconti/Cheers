package open.vice.cheers.network.converter

import open.vice.cheers.network.model.BeerResponseItem
import open.vice.cheers.repositories.model.Beer

fun convertBeerResponseItemToBeerModel(beerResponseItem: BeerResponseItem): Beer = Beer(beerResponseItem)