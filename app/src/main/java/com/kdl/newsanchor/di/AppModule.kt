package com.kdl.newsanchor.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kdl.newsanchor.R
import com.kdl.newsanchor.common.Constants.BASE_URL
import com.kdl.newsanchor.data.remote.NewsAnchorApi
import com.kdl.newsanchor.data.repositories.AccessRepository
import com.kdl.newsanchor.domain.repositories.AccessRepositoryImpl
import com.kdl.newsanchor.ui.adapters.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNewsAnchorClient(): NewsAnchorApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAnchorApi::class.java)
    }


    @Provides
    @Singleton
    fun providesRepository(clientApi: NewsAnchorApi):AccessRepository {
        return AccessRepositoryImpl(clientApi)
    }

    @Provides
    @Singleton
    fun providesGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Provides
    @Singleton
    fun providesAdapter(
        glide: RequestManager
    ) = NewsAdapter(glide)
}