package com.wuujcik.microbudget.di

import com.wuujcik.domain.repository.SpendingsRepository
import com.wuujcik.infrastructure.repository.SpendingsRepositoryImpl
import org.koin.dsl.module

/**
 * Module holding repositories.
 */
val repositoryModule = module {
    single<SpendingsRepository> { SpendingsRepositoryImpl(spendingsDataManager = get()) }
}
