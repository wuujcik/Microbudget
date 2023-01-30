package com.wuujcik.infrastructure.dao

import androidx.room.*
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.dao.SpendingsDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SpendingsDaoImpl : SpendingsDao {

    @Transaction
    @Query("SELECT * FROM ${Spending.TABLE_NAME} WHERE id = :id LIMIT 1")
    abstract override suspend fun getById(id: String): Spending?

    @Transaction
    @Query("SELECT * FROM ${Spending.TABLE_NAME} WHERE currency = :currency")
    abstract override suspend fun getByCurrency(currency: Currency): List<Spending>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insertOrUpdate(item: Spending)

    @Query("SELECT * FROM ${Spending.TABLE_NAME}")
    abstract override fun getAll(): Flow<List<Spending>>

    @Query("DELETE FROM ${Spending.TABLE_NAME} WHERE id = :id")
    abstract override suspend fun deleteOneById(id: Int)

    @Query("DELETE FROM ${Spending.TABLE_NAME}")
    abstract override suspend fun deleteAll()
}
