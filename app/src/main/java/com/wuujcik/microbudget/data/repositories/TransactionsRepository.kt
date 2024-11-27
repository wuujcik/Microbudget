package com.wuujcik.microbudget.data.repositories

import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.data.datasources.TransactionLocalDataSource
import com.wuujcik.microbudget.data.entities.Spending
import kotlinx.coroutines.flow.Flow

class TransactionsRepository(
    private val dataManager: TransactionLocalDataSource
) {
    suspend fun insertOrUpdate(transaction: Transaction) {
        dataManager.insertOrUpdate(transaction)
    }

    suspend fun getTransactions(): Flow<List<Transaction>> {
        return dataManager.getAll()
    }

    suspend fun deleteOne(transaction: Transaction) {
        dataManager.deleteOne(transaction)
    }

    suspend fun deleteAll() {
        dataManager.deleteAll()
    }

    fun getOldSpendings(): Flow<List<Spending>> {
        return dataManager.getOldSpendings()
    }
}
