package io.ilyasin.logonex.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ilyasin.logonex.data.IProductsRepository
import io.ilyasin.logonex.data.ProductsRepository
import io.ilyasin.logonex.data.db.ProductDatabase
import io.ilyasin.logonex.data.network.ApiService
import io.ilyasin.logonex.data.network.NetworkDataSource

/**
 * Hilt module provides dependencies for injections.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideApi(): ApiService = ApiService.create()

    @Provides
    fun provideProductRepository(networkDataSource: NetworkDataSource, database: ProductDatabase):
            IProductsRepository = ProductsRepository(networkDataSource, database)

    @Provides
    fun provideNetworkDataSource(apiService: ApiService): NetworkDataSource = NetworkDataSource(apiService)

    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): ProductDatabase =
        ProductDatabase.createDB(applicationContext)
}