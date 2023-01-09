package com.kdl.newsanchor.di

import com.kdl.newsanchor.ui.fragments.TestNewsFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun providesFragmentFactory() = TestNewsFragmentFactory()
}