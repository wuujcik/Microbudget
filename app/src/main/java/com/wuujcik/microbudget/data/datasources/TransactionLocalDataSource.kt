package com.wuujcik.microbudget.data.datasources

import com.wuujcik.microbudget.data.databases.OfflineDatabase
import com.wuujcik.microbudget.data.databases.TransactionDatabase
import com.wuujcik.microbudget.data.entities.Spending
import com.wuujcik.microbudget.data.entities.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(
    private val transactionDatabase: TransactionDatabase,
    private val spendingDatabase: OfflineDatabase
) {
    suspend fun insertOrUpdate(transaction: Transaction) =
        transactionDatabase.transactionsDao().insertOrUpdate(transaction)

    suspend fun getAll(): Flow<List<Transaction>> = transactionDatabase.transactionsDao().getAll()

    suspend fun deleteOne(transaction: Transaction) =
        transactionDatabase.transactionsDao().deleteOneById(transaction.id)

    suspend fun deleteAll() = transactionDatabase.transactionsDao().deleteAll()

    fun getOldSpendings(): Flow<List<Spending>> = spendingDatabase.spendingsDao().getAll()
}
