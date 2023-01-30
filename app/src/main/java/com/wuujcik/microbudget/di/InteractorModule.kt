package com.wuujcik.microbudget.di

import com.wuujcik.domain.interactor.SpendingsInteractor
import com.wuujcik.infrastructure.interactor.SpendingsInteractorImpl
import org.koin.dsl.module

/**
 * Module containing interactors.
 */
val interactorModule = module {
    single<SpendingsInteractor> { SpendingsInteractorImpl(spendingDao = get()) }
}
