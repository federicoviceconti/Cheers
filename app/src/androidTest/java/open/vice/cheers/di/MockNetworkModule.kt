package open.vice.cheers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import open.vice.cheers.network.annotation.LoggingInterceptorOkHttpClient
import open.vice.cheers.network.model.BeerResponseItem
import open.vice.cheers.network.services.PunkBeerService

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule {
    @Provides
    fun provideFakePunkBeerService(): PunkBeerService {
        return object : PunkBeerService {
            override suspend fun getBeersByPage(page: Int, perPage: Int): List<BeerResponseItem> {
                return listOf(
                    BeerResponseItem(
                        1,
                        "Skull Candy",
                        "Pacific Hopped Amber Bitter.",
                        "01/2020",
                        "Lorem ipsum dolor sit amet.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        2,
                        "Candy Skull Again",
                        "Bitter better.",
                        "03/2021",
                        "Lorem amet.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        3,
                        "Skull Skull Candy",
                        "Paciic.",
                        "11/2010",
                        "Ipsum.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        4,
                        "S",
                        "Pacfic.",
                        "11/2010",
                        "psum.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        5,
                        "Sk",
                        "Paific.",
                        "11/2010",
                        "Isum.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        6,
                        "Sku",
                        "Pcific.",
                        "11/2010",
                        "Ipsum.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        7,
                        "Skul",
                        "Pacific.",
                        "11/2010",
                        "Ipsm.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        8,
                        "Skullx",
                        "acific.",
                        "11/2010",
                        "Ipsu.",
                        "https://images.punkapi.com/v2/keg.png"
                    )
                )
            }

            override suspend fun getBeersByDateAndPage(
                page: Int,
                start: String?,
                end: String?,
                perPage: Int
            ): List<BeerResponseItem> {
                return listOf(
                    BeerResponseItem(
                        1,
                        "Skull Candy",
                        "Pacific Hopped Amber Bitter.",
                        "01/2020",
                        "Lorem ipsum dolor sit amet.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        2,
                        "Candy Skull Again",
                        "Bitter better bet.",
                        "02/2020",
                        "Lorem amet.",
                        "https://images.punkapi.com/v2/keg.png"
                    ),
                    BeerResponseItem(
                        1,
                        "Skull Skull Candy",
                        "Pacific.",
                        "03/2020",
                        "Ipsum.",
                        "https://images.punkapi.com/v2/keg.png"
                    )
                )
            }

        }
    }
}