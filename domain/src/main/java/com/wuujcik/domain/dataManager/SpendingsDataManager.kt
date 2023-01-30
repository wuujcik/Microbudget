package com.wuujcik.domain.dataManager

import com.wuujcik.data.entities.Spending
import kotlinx.coroutines.flow.Flow

interface SpendingsDataManager {
    suspend fun insertOrUpdate(spending: Spending)
    suspend fun getAll(): Flow<List<Spending>>
    suspend fun deleteOne(spending: Spending)
    suspend fun deleteAll()
}