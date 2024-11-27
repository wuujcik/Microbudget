package com.wuujcik.microbudget.di

import com.wuujcik.microbudget.data.databases.OfflineDatabase
import com.wuujcik.microbudget.data.databases.TransactionDatabase
import com.wuujcik.microbudget.data.datasources.TransactionLocalDataSource
import com.wuujcik.microbudget.data.repositories.TransactionsRepository
import com.wuujcik.microbudget.ui.screens.detail.TransactionDetailViewModel
import com.wuujcik.microbudget.ui.screens.list.ListOfTransactionsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ListOfTransactionsViewModel(get()) }
    viewModel { TransactionDetailViewModel(get()) }
}

val dataModule = module {
    single { TransactionsRepository(get()) }
    single { TransactionLocalDataSource(get(), get()) }
    single { TransactionDatabase.getInstance() }
    single { OfflineDatabase.getInstance() }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}