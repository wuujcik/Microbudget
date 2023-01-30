package com.wuujcik.infrastructure.interactor

import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.dao.SpendingsDao
import com.wuujcik.domain.interactor.SpendingsInteractor
import kotlinx.coroutines.flow.Flow

class SpendingsInteractorImpl(private val spendingDao: SpendingsDao) : SpendingsInteractor {

    override suspend fun insertOrUpdate(spending: Spending) {
        spendingDao.insertOrUpdate(spending)
    }

    override suspend fun getAll(): Flow<List<Spending>> {
        return spendingDao.getAll()
    }

    override suspend fun deleteOne(spending: Spending) {
        spendingDao.deleteOneById(spending.id)
    }

    override suspend fun deleteAll() {
        spendingDao.deleteAll()
    }
}