package com.terranullius.task.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.terranullius.task.business.data.network.abstraction.ImageNetworkDataSource
import com.terranullius.task.business.data.network.implementation.ImageNetworkDataSourceImpl
import com.terranullius.task.business.interactors.imagelist.GetAllImages
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import com.terranullius.task.framework.datasource.network.abstraction.ImageNetworkService
import com.terranullius.task.framework.datasource.network.implementation.ApiService
import com.terranullius.task.framework.datasource.network.implementation.ImageNetworkServiceImpl
import com.terranullius.task.framework.datasource.network.mappers.NetworkMapper
import com.terranullius.task.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        moshi: Moshi
    ): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesNetworkMapper(): NetworkMapper {
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun providesImageNetworkService(
        apiService: ApiService,
        networkMapper: NetworkMapper
    ): ImageNetworkService {
        return ImageNetworkServiceImpl(
            networkMapper,
            apiService
        )
    }

    @Singleton
    @Provides
    fun providesImageNetworkDataSource(imageNetworkService: ImageNetworkService): ImageNetworkDataSource {
        return ImageNetworkDataSourceImpl(imageNetworkService)
    }

    @Singleton
    @Provides
    fun proviedsGetAlLImagesUseCase(imageNetworkDataSource: ImageNetworkDataSource): GetAllImages {
        return GetAllImages(
            imageNetworkDataSource
        )
    }

    @Singleton
    @Provides
    fun providesImageListInteractors(getAllImages: GetAllImages): ImageListInteractors {
        return ImageListInteractors(getAllImages)
    }

}