package com.wuujcik.infrastructure.dataManager

import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.dataManager.SpendingsDataManager
import com.wuujcik.domain.interactor.SpendingsInteractor
import kotlinx.coroutines.flow.Flow

class SpendingsDataManagerImpl(
    private val spendingsInteractor: SpendingsInteractor
) : SpendingsDataManager {

    override suspend fun insertOrUpdate(spending: Spending) {
        spendingsInteractor.insertOrUpdate(spending)
    }

    override suspend fun getAll(): Flow<List<Spending>> {
        return spendingsInteractor.getAll()
    }

    override suspend fun deleteOne(spending: Spending) {
        spendingsInteractor.deleteOne(spending)
    }

    override suspend fun deleteAll() {
        spendingsInteractor.deleteAll()
    }
}