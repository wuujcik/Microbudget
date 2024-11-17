package com.wuujcik.microbudget.data.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wuujcik.microbudget.data.entities.Currency
import com.wuujcik.microbudget.data.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @androidx.room.Transaction
    @Query("SELECT * FROM ${Transaction.TABLE_NAME} WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): Transaction?

    @androidx.room.Transaction
    @Query("SELECT * FROM ${Transaction.TABLE_NAME} WHERE currency = :currency")
    suspend fun getByCurrency(currency: Currency): List<Transaction>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: Transaction)

    @Query("SELECT * FROM ${Transaction.TABLE_NAME}")
    fun getAll(): Flow<List<Transaction>>

    @Query("DELETE FROM ${Transaction.TABLE_NAME} WHERE id = :id")
    suspend fun deleteOneById(id: Int)

    @Query("DELETE FROM ${Transaction.TABLE_NAME}")
    suspend fun deleteAll()
}
