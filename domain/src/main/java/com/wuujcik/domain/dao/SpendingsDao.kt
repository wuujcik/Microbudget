package com.wuujcik.domain.dao

import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import kotlinx.coroutines.flow.Flow


interface SpendingsDao {

    suspend fun getById(id: String): Spending?

    suspend fun getByCurrency(currency: Currency): List<Spending>?

    suspend fun insertOrUpdate(item: Spending)

    fun getAll(): Flow<List<Spending>>

    suspend fun deleteOneById(id: Int)

    suspend fun deleteAll()
}
