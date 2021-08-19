package open.vice.cheers.repositories.home

import open.vice.cheers.network.model.ResponseData
import open.vice.cheers.repositories.model.Beer
import java.time.LocalDate

interface HomeRepository {
    suspend fun getBeers(page: Int?) : ResponseData<List<Beer>>
    suspend fun getBeersByDate(page: Int?, start: LocalDate?, end: LocalDate?): ResponseData<List<Beer>>
}