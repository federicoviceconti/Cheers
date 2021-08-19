package open.vice.cheers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import open.vice.cheers.network.annotation.LoggingInterceptorOkHttpClient
import open.vice.cheers.network.services.PunkBeerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.punkapi.com/v2/"

    @LoggingInterceptorOkHttpClient
    @Provides
    fun providesLoggingInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun providePunkBeerService(
        @LoggingInterceptorOkHttpClient loggingClient: OkHttpClient
    ): PunkBeerService = createRetrofitBuilder(PunkBeerService::class.java, loggingClient)

    private fun <T> createRetrofitBuilder(javaClass: Class<T>, client: OkHttpClient): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(javaClass)
    }
}