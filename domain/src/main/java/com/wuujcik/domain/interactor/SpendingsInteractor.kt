package com.wuujcik.domain.interactor

import com.wuujcik.data.entities.Spending
import kotlinx.coroutines.flow.Flow

interface SpendingsInteractor {
    suspend fun insertOrUpdate(spending: Spending)
    suspend fun getAll(): Flow<List<Spending>>
    suspend fun deleteOne(spending: Spending)
    suspend fun deleteAll()
}