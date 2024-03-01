package com.fm.easypay_sample.di

import com.fm.easypay.repositories.ConsentAnnual
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConsentAnnual() = ConsentAnnual()
}