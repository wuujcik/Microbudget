package com.wuujcik.microbudget.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wuujcik.domain.database.CurrencyAdapter
import com.wuujcik.domain.database.OfflineDatabase
import com.wuujcik.domain.database.ZonedDateTimeAdapter
import com.wuujcik.domain.database.ZonedDateTimeTypeConverter
import com.wuujcik.infrastructure.database.OfflineDatabaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Module holding databases.
 */
val databaseModule = module {
    single<OfflineDatabase> {
        Room
            .databaseBuilder(
                androidApplication(),
                OfflineDatabaseImpl::class.java,
                OfflineDatabase.DB_NAME
            )
            .addMigrations(*OfflineDatabaseImpl.migrations.toTypedArray())
            .addTypeConverter(ZonedDateTimeTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(CurrencyAdapter)
            .add(ZonedDateTimeAdapter)
            .build()
    }
}
