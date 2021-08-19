package open.vice.cheers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import open.vice.cheers.core.extension.toDateFormatted
import open.vice.cheers.network.PunkServiceConstants
import open.vice.cheers.network.converter.convertBeerResponseItemToBeerModel
import open.vice.cheers.network.model.ResponseData
import open.vice.cheers.network.model.SuccessResult
import open.vice.cheers.network.services.PunkBeerService
import open.vice.cheers.repositories.home.HomeRepository
import open.vice.cheers.repositories.model.Beer
import java.time.LocalDate
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class MockRepositoryModule {
    @Provides
    @Singleton
    fun provideFakeHomeRepository(punkBeerService: PunkBeerService): HomeRepository {
        return object : HomeRepository {
            override suspend fun getBeers(page: Int?): ResponseData<List<Beer>> {
                val data = punkBeerService.getBeersByPage(
                    page ?: PunkServiceConstants.MIN_PAGE_VALUE,
                    PunkServiceConstants.BEER_PER_PAGE
                )

                return SuccessResult(
                    data = data.map { convertBeerResponseItemToBeerModel(it) }
                )
            }

            override suspend fun getBeersByDate(
                page: Int?,
                start: LocalDate?,
                end: LocalDate?
            ): ResponseData<List<Beer>> {
                val data = punkBeerService.getBeersByDateAndPage(
                    page ?: PunkServiceConstants.MIN_PAGE_VALUE,
                    start?.toDateFormatted(),
                    end?.toDateFormatted()
                )

                return SuccessResult(
                    data = data.map { convertBeerResponseItemToBeerModel(it) }
                )
            }
        }
    }
}