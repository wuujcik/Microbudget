package com.wuujcik.microbudget.ui.presentation.spendingList

import com.wuujcik.data.entities.Spending

data class SpendingListState(
    val listOfSpending: List<Spending> = emptyList(),
)
