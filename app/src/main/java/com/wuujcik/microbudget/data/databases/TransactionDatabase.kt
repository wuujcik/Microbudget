package com.wuujcik.microbudget.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wuujcik.microbudget.MainApplication.Companion.app
import com.wuujcik.microbudget.data.entities.Transaction

@Database(
    entities = [
        Transaction::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(BaseConverters::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        private var instance: TransactionDatabase? = null

        @Synchronized
        fun getInstance(context: Context = app()): TransactionDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(
                        context,
                        TransactionDatabase::class.java,
                        DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

        const val DB_NAME = "transactions_database"
    }
}