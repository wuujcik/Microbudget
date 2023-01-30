package com.wuujcik.microbudget.di

import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.ui.presentation.itemDetail.ItemDetailViewModel
import com.wuujcik.microbudget.ui.presentation.spendingList.SpendingListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module containing view models.
 */
val viewModelModule = module {
    viewModel {
        SpendingListViewModel(
            spendingsRepository = get()
        )
    }

    viewModel { (item: Spending?) ->
        ItemDetailViewModel(
            originalItem = item,
            spendingsRepository = get()
        )
    }
}
