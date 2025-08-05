package com.hfad.main.di

import com.hfad.main.data.api.BlogApi
import com.hfad.main.domain.repository.BlogRepository
import com.hfad.main.data.repository.BlogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlogModule {



    @Provides
    @Singleton
    fun provideBlogApi(retrofit: Retrofit): BlogApi {
        return retrofit.create(BlogApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBlogRepository(
        blogApi: BlogApi
    ): BlogRepository = BlogRepositoryImpl(blogApi)
}
