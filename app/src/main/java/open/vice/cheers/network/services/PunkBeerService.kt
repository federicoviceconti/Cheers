package open.vice.cheers.network.services

import open.vice.cheers.network.PunkServiceConstants
import open.vice.cheers.network.model.BeerResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkBeerService {
    @GET("beers")
    suspend fun getBeersByPage(
        @Query(value = "page") page: Int,
        @Query(value = "per_page") perPage: Int = PunkServiceConstants.BEER_PER_PAGE
    ) : List<BeerResponseItem>

    @GET("beers")
    suspend fun getBeersByDateAndPage(
        @Query(value = "page") page: Int,
        @Query(value = "brewed_after") start: String?,
        @Query(value = "brewed_before") end: String?,
        @Query(value = "per_page") perPage: Int = PunkServiceConstants.BEER_PER_PAGE
    ) : List<BeerResponseItem>
}