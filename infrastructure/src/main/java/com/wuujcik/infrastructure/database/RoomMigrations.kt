package com.wuujcik.infrastructure.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

abstract class RoomMigrations {

    abstract val migrations: List<Migration>

    protected fun migration(
        startVersion: Int,
        endVersion: Int,
        migrateProcedure: SupportSQLiteDatabase.() -> Unit
    ): Migration {
        return object : Migration(startVersion, endVersion) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.migrateProcedure()
            }
        }
    }
}
