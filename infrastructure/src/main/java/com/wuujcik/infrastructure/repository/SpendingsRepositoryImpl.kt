package com.wuujcik.infrastructure.repository

import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.dataManager.SpendingsDataManager
import com.wuujcik.domain.repository.SpendingsRepository
import kotlinx.coroutines.flow.Flow

class SpendingsRepositoryImpl(private val spendingsDataManager: SpendingsDataManager) :
    SpendingsRepository {

    override suspend fun insertOrUpdate(spending: Spending) {
        spendingsDataManager.insertOrUpdate(spending)
    }

    override suspend fun getAll(): Flow<List<Spending>> {
        return spendingsDataManager.getAll()
    }

    override suspend fun deleteOne(spending: Spending) {
        spendingsDataManager.deleteOne(spending)
    }

    override suspend fun deleteAll() {
        spendingsDataManager.deleteAll()
    }
}
