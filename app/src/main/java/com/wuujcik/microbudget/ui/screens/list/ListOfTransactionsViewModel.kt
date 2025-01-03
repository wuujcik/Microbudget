package com.wuujcik.microbudget.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.microbudget.data.entities.Spending
import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.data.repositories.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ListOfTransactionsViewModel(
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    val transactions = MutableStateFlow(listOf<Transaction>())

    val oldSpendings = MutableStateFlow(listOf<Spending>())

    init {
        viewModelScope.launch {
            transactionsRepository.getTransactions().collect { newTransactions: List<Transaction> ->
                transactions.value = newTransactions.sortedBy { it.date }
            }
        }

        viewModelScope.launch {
            transactionsRepository.getOldSpendings().collect { newTransactions: List<Spending> ->
            oldSpendings.value = newTransactions.sortedBy { it.date }
        } }
    }

    fun deleteItem(item: Transaction) = viewModelScope.launch {
        transactionsRepository.deleteOne(item)
    }

    fun deleteAllTransactions() = viewModelScope.launch {
        transactionsRepository.deleteAll()
    }

}
