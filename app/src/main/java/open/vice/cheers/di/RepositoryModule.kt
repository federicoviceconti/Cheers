package open.vice.cheers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import open.vice.cheers.network.services.PunkBeerService
import open.vice.cheers.repositories.home.HomeRepository
import open.vice.cheers.repositories.home.HomeRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providesHomeRepository(punkBeerService: PunkBeerService) : HomeRepository {
        return HomeRepositoryImpl(punkBeerService)
    }
}