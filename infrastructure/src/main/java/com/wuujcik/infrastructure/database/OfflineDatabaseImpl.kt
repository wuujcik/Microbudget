package com.wuujcik.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.database.OfflineDatabase
import com.wuujcik.domain.database.ZonedDateTimeTypeConverter
import com.wuujcik.infrastructure.dao.SpendingsDaoImpl

@Database(
    entities = [
        Spending::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ZonedDateTimeTypeConverter::class
)

abstract class OfflineDatabaseImpl : RoomDatabase(), OfflineDatabase {

    /**
     * The holder of definitions of database changes and corresponding migrations.
     */
    companion object : RoomMigrations() {
        override val migrations: List<Migration> = listOf()
    }

    override suspend fun clear() {
        spendingsDao().deleteAll()
    }

    abstract override fun spendingsDao(): SpendingsDaoImpl
}
