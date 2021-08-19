package open.vice.cheers.repositories.home

import open.vice.cheers.core.network.makeRequest
import open.vice.cheers.network.PunkServiceConstants
import open.vice.cheers.network.services.PunkBeerService
import open.vice.cheers.network.converter.convertBeerResponseItemToBeerModel
import open.vice.cheers.network.model.BeerResponseItem
import open.vice.cheers.network.model.ResponseData
import open.vice.cheers.repositories.model.Beer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val punkBeerService: PunkBeerService
) : HomeRepository {
    override suspend fun getBeers(page: Int?): ResponseData<List<Beer>> {
        return makeBeerRequestAndConvertResponse {
            punkBeerService
                .getBeersByPage(page ?: PunkServiceConstants.MIN_PAGE_VALUE)
        }
    }

    override suspend fun getBeersByDate(page: Int?, start: LocalDate?, end: LocalDate?): ResponseData<List<Beer>> {
        return makeBeerRequestAndConvertResponse {
            punkBeerService.getBeersByDateAndPage(
                page ?: PunkServiceConstants.MIN_PAGE_VALUE,
                start.formatToDateService(),
                end.formatToDateService()
            )
        }
    }

    private suspend fun makeBeerRequestAndConvertResponse(
        requestFunc: suspend () -> List<BeerResponseItem>
    ) : ResponseData<List<Beer>> {
        return makeRequest {
            val beerResponseList = requestFunc()

            return@makeRequest beerResponseList.map {
                convertBeerResponseItemToBeerModel(it)
            }.toList()
        }
    }
}

private fun LocalDate?.formatToDateService(): String? {
    if(this == null) { return null }

    return this.format(DateTimeFormatter.ofPattern("MM-yyyy"))
}
