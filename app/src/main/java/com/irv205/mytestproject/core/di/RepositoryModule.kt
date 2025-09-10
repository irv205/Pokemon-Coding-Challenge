package com.irv205.mytestproject.core.di

import com.irv205.mytestproject.data.repository.PokeRepositoryImpl
import com.irv205.mytestproject.domain.repository.PokeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repositoryImpl: PokeRepositoryImpl): PokeRepository
}