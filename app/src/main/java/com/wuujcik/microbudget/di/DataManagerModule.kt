package com.wuujcik.microbudget.di

import com.wuujcik.domain.dataManager.SpendingsDataManager
import com.wuujcik.infrastructure.dataManager.SpendingsDataManagerImpl
import org.koin.dsl.module

/**
 * Module containing data managers and data sources.
 */
val dataManagerModule = module {
    single<SpendingsDataManager> { SpendingsDataManagerImpl(spendingsInteractor = get()) }
}