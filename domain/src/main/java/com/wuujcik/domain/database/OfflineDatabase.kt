package com.wuujcik.domain.database

import com.wuujcik.domain.dao.SpendingsDao

interface OfflineDatabase {

    companion object {
        const val DB_NAME = "offline_database"
    }

    suspend fun clear()

    fun spendingsDao(): SpendingsDao
}
