package com.wuujcik.microbudget.di

import com.wuujcik.domain.dao.SpendingsDao
import com.wuujcik.domain.database.OfflineDatabase
import org.koin.dsl.module

/**
 * Module holding database access objects.
 */
val daoModule = module {
    single<SpendingsDao> { get<OfflineDatabase>().spendingsDao() }
}
