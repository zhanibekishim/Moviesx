package com.jax.movies.di

import android.content.Context
import com.jax.movies.data.store.OnBoardingSettingStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    fun provideOnBoardingSettingStore(
        @ApplicationContext context: Context
    ):OnBoardingSettingStore{
        return OnBoardingSettingStore(context)
    }
}